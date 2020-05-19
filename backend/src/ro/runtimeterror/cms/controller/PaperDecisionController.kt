package ro.runtimeterror.cms.controller

import ro.runtimeterror.cms.model.PaperSuggestion

class PaperDecisionController
{
    /**
     * Decide the status of a paper (accepted, rejected, conflicting)
     */
    fun decide(paperId: Int, status: Int)
    {
        TODO("Not yet implemented")
    }

    /**
     * Get all the papers and associate a suggestion based on the reviews
     */
    fun getPapers(): List<PaperSuggestion>
    {
        TODO("Not yet implemented")
    }

}
