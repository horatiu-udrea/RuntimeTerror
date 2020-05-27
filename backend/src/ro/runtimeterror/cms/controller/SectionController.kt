package ro.runtimeterror.cms.controller

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.LocalDateTime
import ro.runtimeterror.cms.database.DatabaseSettings.connection
import ro.runtimeterror.cms.database.daos.SectionDAO
import ro.runtimeterror.cms.database.tables.SectionTable
import ro.runtimeterror.cms.database.tables.UserSectionChoiceTable
import ro.runtimeterror.cms.model.Section
import ro.runtimeterror.cms.model.UserReview

class SectionController
{
    /**
     * Get the section associated with the author or null if the user does not present in any section
     */
    fun getSectionDetails(userId: Int): Section? = transaction {
        return@transaction SectionTable
            .select{SectionTable.userId eq userId}
            .map { SectionDAO.findById(it[SectionTable.id]) }
            .firstOrNull()
    }

    /**
     * Upload the presentation for the section in which the speaker is going to present in
     */
    fun uploadPresentation(userId: Int, path: String) = transaction(connection) {
        SectionTable
            .update({SectionTable.userId eq userId}){
                it[presentationDocumentPath] = path
            }
    }

    /**
     * Get all sections of the conference
     */
    fun getAllSections() : List<Section> = SectionDAO
                                            .all()
                                            .toList()

    /**
     * User chose to participate in this section
     */
    fun userSectionChoice(userId: Int, sectionId: Int) = transaction(connection){
        UserSectionChoiceTable.insert {
            it[userID] = userId
            it[sectionID] = sectionId
        }
    }

    /**
     * Create a section
     */
    fun createSection(name: String, startTime: LocalDateTime, endTime: LocalDateTime) =
        SectionTable.insert {
            it[roomName] = name
            it[SectionTable.startTime] = startTime.toDateTime()
            it[SectionTable.endTime] = endTime.toDateTime()
            }

    /**
     * Choose section chair
     */
    fun chooseSectionChair(sectionId: Int, userId: Int) =
        SectionTable.update({SectionTable.id eq sectionId}) {
            it[SectionTable.userId] = userId
        }

    /**
     * Choose the specker that presents in this section as well as its paper
     */
    fun chooseSectionPresenter(userId: Int, paperId: Int, sectionId: Int) =
        SectionTable
            .update({SectionTable.id eq sectionId}){
                it[SectionTable.paperId] = paperId
                it[SectionTable.userId] = userId
            }

    /**
     * Change the room name of the section
     */
    fun changeSectionRoom(sectionId: Int, roomName: String) =
        SectionTable.update ({ SectionTable.id eq sectionId }){
            it[SectionTable.roomName] = roomName
        }

    /**
     * Get all the reviews for the paper that the author is going to present
     * If the author is a PC member, he is not allowed to see the reviews (return empty list)
     *  Throw an exception if the author is not assigned to a section
     */
    fun getReviews(userId: Int): List<UserReview>
    {
        TODO("Not yet implemented")
    }
}
