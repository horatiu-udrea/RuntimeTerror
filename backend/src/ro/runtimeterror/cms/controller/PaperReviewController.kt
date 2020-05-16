package ro.runtimeterror.cms.controller

import ro.runtimeterror.cms.model.Review

class PaperReviewController
{
    /**
     * Get all the review made by the user or assigned to him
     */
    fun getReviews(userId: Int): List<Review>
    {
        TODO("Not yet implemented")
    }

    /**
     * Change a review
     */
    fun review(userid: Int, paperId: Int, recommendation: String, qualifier: Int)
    {
        TODO("Not yet implemented")
    }

}
