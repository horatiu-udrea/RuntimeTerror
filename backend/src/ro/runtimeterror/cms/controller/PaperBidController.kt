package ro.runtimeterror.cms.controller

import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import ro.runtimeterror.cms.database.DatabaseSettings.connection
import ro.runtimeterror.cms.database.daos.PaperDAO
import ro.runtimeterror.cms.database.daos.withAuthors
import ro.runtimeterror.cms.database.tables.BidPaperTable
import ro.runtimeterror.cms.model.Paper
import ro.runtimeterror.cms.model.PaperBidResult
import ro.runtimeterror.cms.model.validators.PaperValidator
import ro.runtimeterror.cms.model.validators.UniquenessValidator
import ro.runtimeterror.cms.model.validators.UserValidator

data class PaperBid(val paper: Paper, var bidResult: PaperBidResult)

class PaperBidController
{
    /**
     * Get the bid result for all the papers of the specified user.
     * If the user has not yet bid the paper, it is given as INDECISIVE
     */
    fun getPapers(userId: Int): List<PaperBid> = transaction(connection) {
        UserValidator.exists(userId)
        return@transaction PaperDAO
            .all()
            .map { withAuthors(it) }
            .map {
                PaperBid(
                    it,
                    getBidResult(it.id.value, userId)
                )
            }
    }

    private fun getBidResult(paperID: Int, userID: Int): PaperBidResult = transaction(connection) {
        return@transaction PaperBidResult.from(
            BidPaperTable
                .select { (BidPaperTable.paperID eq paperID) and (BidPaperTable.userID eq userID) }
                .map { it[BidPaperTable.paperBidResult] }
                .firstOrNull() ?: PaperBidResult.INDECISIVE.value
        )
    }

    /**
     * User bids on the specified paper
     */
    fun bid(userID: Int, paperID: Int, bidResult: Int) = transaction(connection) {
        PaperValidator.exists(paperID)
        UserValidator.exists(userID)
        if (UniquenessValidator.bidExists(userID, paperID))
        {
            BidPaperTable.insert {
                it[BidPaperTable.paperID] = paperID
                it[BidPaperTable.userID] = userID
                it[paperBidResult] = PaperBidResult.from(bidResult).value
            }
        }
        else
        {
            BidPaperTable.update({ (BidPaperTable.paperID eq paperID) and (BidPaperTable.userID eq userID) }) {
                it[paperBidResult] = PaperBidResult.from(bidResult).value
            }
        }

    }

}
