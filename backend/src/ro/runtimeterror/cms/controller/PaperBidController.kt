package ro.runtimeterror.cms.controller

import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import ro.runtimeterror.cms.database.DatabaseSettings
import ro.runtimeterror.cms.database.daos.PaperDAO
import ro.runtimeterror.cms.database.tables.BidPaperTable
import ro.runtimeterror.cms.model.Paper
import ro.runtimeterror.cms.model.PaperBidResult
import ro.runtimeterror.cms.model.validators.PaperValidator
import ro.runtimeterror.cms.model.validators.UserValidator

data class PaperBid(val paper: Paper, var bidResult: PaperBidResult)

class PaperBidController
{
    /**
     * Get the bid result for all the papers of the specified user.
     * If the user has not yet bid the paper, it is given as INDECISIVE
     */
    fun getPapers(userId: Int): List<PaperBid> {
        val bidResults: MutableList<PaperBid> = ArrayList()
        val results: MutableList<Pair<Int, PaperBidResult>> = ArrayList()
        transaction(DatabaseSettings.connection) {
            PaperDAO
                    .all()
                    .forEach() {paper ->bidResults += PaperBid(paper, PaperBidResult.INDECISIVE) }
//            Results from the bidPaperTable, only paperID, bidResult tuples
                    BidPaperTable
                    .select{BidPaperTable.userID eq userId}
                    .forEach() {
                            bid ->results += Pair<Int, PaperBidResult>(
                                bid[BidPaperTable.userID],
                                PaperBidResult.from(bid[BidPaperTable.paperID])
                        )
                    }

            for(pairs in results){
                bidResults.map {
                    bid ->
                        if(bid.paper.paperId == pairs.first){
                            bid.bidResult = pairs.second
                        }
                        bid
                }
            }
        }
        return bidResults
    }
    /**
     * User bids on the specified paper
     */
    fun bid(userID: Int, paperID: Int, bidResult: Int)
    {
        val bid: PaperBidResult = PaperBidResult.from(bidResult)
        PaperValidator.exists(paperID)
        UserValidator.exists(userID)
            transaction(DatabaseSettings.connection) {
                BidPaperTable.insert {
                    it[BidPaperTable.paperID] = paperID
                    it[BidPaperTable.userID] = userID
                    it[paperBidResult] = bid.value
                }
            }

    }

}
