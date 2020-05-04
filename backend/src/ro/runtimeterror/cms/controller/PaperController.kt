package ro.runtimeterror.cms.controller

import ro.runtimeterror.cms.model.Paper
import ro.runtimeterror.cms.repository.Repository

class PaperController(private val repository: Repository)
{
    /**
     * Get all papers
     */
    fun getPapers(): List<Paper>
    {
        TODO("Not yet implemented")
    }

    /**
     * Author submitted a paper
     */
    fun addPaper(paper: Paper, userId: Int)
    {
        TODO("Not yet implemented")
    }

}
