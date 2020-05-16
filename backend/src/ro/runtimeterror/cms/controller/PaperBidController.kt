package ro.runtimeterror.cms.controller

import ro.runtimeterror.cms.model.Paper
import ro.runtimeterror.cms.model.PaperBidResult

data class PaperBid(val paper: Paper, val bidResult: PaperBidResult)

class PaperBidController
{
    /**
     * Get the bid result for all the papers of the specified user.
     * If the user has not yet bid the paper, it is given as INDECISIVE
     */
    fun getPapers(userId: Int): List<PaperBid>
    {
        TODO("Not yet implemented")
    }

    /**
     * User bids on the specified paper
     */
    fun bid(userId: Int, paperId: Int, bidResult: Int)
    {
        TODO("Not yet implemented")
    }

}
