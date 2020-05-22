package ro.runtimeterror.cms.controller

import ro.runtimeterror.cms.model.Paper
import ro.runtimeterror.cms.model.User

class PaperPresentationController
{

    /**
     * Get all accepted papers
     */
    fun getAcceptedPapers(): List<Paper>
    {
        TODO("Not yet implemented")
    }

    /**
     * Get all accepted papers that are not assigned to a section
     */
    fun getRemainingPapers() : List<Paper>
    {
        TODO("Not yet implemented")
    }

    /**
     * Get the authors of the paper that are not assigned to speak in a section yet
     */
    fun getRemainingAuthors(paperId: Int): List<User>
    {
        TODO("Not yet implemented")
    }
}