package ro.runtimeterror.cms.controller

import ro.runtimeterror.cms.model.Section

class SectionController
{
    /**
     * Get the section associated with the author or null if the user does not present in any section
     */
    fun getSectionDetails(userId: Int): Section?
    {
        TODO("Not yet implemented")
    }

    /**
     * Upload the presentation for the section in which the speaker is going to present in
     */
    fun uploadPresentation(userId: Int, path: String)
    {
        TODO("Not yet implemented")
    }
}
