package ro.runtimeterror.cms.controller

import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import ro.runtimeterror.cms.database.DatabaseSettings.connection
import ro.runtimeterror.cms.database.daos.PaperDAO
import ro.runtimeterror.cms.database.daos.UserDAO
import ro.runtimeterror.cms.database.daos.withAuthors
import ro.runtimeterror.cms.database.tables.BidPaperTable
import ro.runtimeterror.cms.database.tables.ReviewTable
import ro.runtimeterror.cms.database.tables.UserTable
import ro.runtimeterror.cms.model.*
import ro.runtimeterror.cms.model.validators.PaperValidator
import ro.runtimeterror.cms.model.validators.UserValidator

class PaperAssignController
{
    /**
     * Retrieve all papers
     */
    fun getPapers(): List<Paper> =
        transaction(connection) {
            return@transaction PaperDAO
                .all()
                .map { withAuthors(it) }
        }

    /**
     * Get the bidding result of the pc m,ember on the paper
     */
    fun getBidResult(paperId: Int, userId: Int): PaperBidResult =
        transaction(connection) {
            return@transaction PaperBidResult.from(
                BidPaperTable
                    .select { ((BidPaperTable.paperID eq paperId) and (BidPaperTable.userID eq userId)) }
                    .map { it[BidPaperTable.paperBidResult] }
                    .firstOrNull() ?: return@transaction PaperBidResult.INDECISIVE
            )
        }

    /**
     * Get all PC Members
     */
    fun getPCMembers(): List<User> = transaction(connection) {
        return@transaction UserTable
            .select { UserTable.type eq UserType.PC_MEMBER.value }
            .map { user -> UserDAO[user[UserTable.id]] }
            .toList()
    }

    /**
     * Assign the paper to the pc member
     */
    fun assign(paperID: Int, userID: Int) = transaction(connection) {
        PaperValidator.exists(paperID)
        UserValidator.exists(userID)
        ReviewTable.insert {
            it[ReviewTable.userID] = userID
            it[ReviewTable.paperID] = paperID
            it[ReviewTable.recommandation] = ""
            it[ReviewTable.qualifier] = Qualifier.NOT_YET_REVIEWED.value
        }
    }
}
