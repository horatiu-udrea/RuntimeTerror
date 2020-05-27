package ro.runtimeterror.cms.controller

import org.joda.time.LocalDateTime
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

    /**
     * Get all sections of the conference
     */
    fun getAllSections() : List<Section>
    {
        TODO("Not yet implemented")
    }

    /**
     * User chose to participate in this section
     */
    fun userSectionChoice(userId: Int, sectionId: Int)
    {
        TODO("Not yet implemented")
    }

    /**
     * Create a section
     */
    fun createSection(name: String, startTime: LocalDateTime, endTime: LocalDateTime)
    {
        TODO("Not yet implemented")
    }

    /**
     * Choose section chair
     */
    fun chooseSectionChair(sectionId: Int, userId: Int)
    {
        TODO("Not yet implemented")
    }

    /**
     * Choose the specker that presents in this section as well as its paper
     */
    fun chooseSectionPresenter(userId: Int, paperId: Int, sectionId: Int)
    {
        TODO("Not yet implemented")
    }

    /**
     * Change the room name of the section
     */
    fun changeSectionRoom(sectionId: Int, roomName: String)
    {
        TODO("Not yet implemented")
    }
}
