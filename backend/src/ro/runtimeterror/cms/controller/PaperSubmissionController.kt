package ro.runtimeterror.cms.controller

import ro.runtimeterror.cms.model.Paper
import ro.runtimeterror.cms.repository.Repository

class PaperSubmissionController(private val repository: Repository)
{
    /**
     * Author submitted a paper
     */
    fun submitProposal(
        userId: Int,
        name: String,
        field: String,
        keywords: String,
        topics: String,
        authors: String
    )
    {
        TODO("Not yet implemented")
    }

    fun fullPaperUploaded(path: String, userId: Int)
    {
        TODO("Not yet implemented")
    }

    /**
     * Get the user's paper
     */
    fun getPaper(userId: Int): Paper
    {
        TODO("Not yet implemented")
    }

}
