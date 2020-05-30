package ro.runtimeterror.cms.controller

import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.dao.with
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.LocalDateTime
import ro.runtimeterror.cms.database.DatabaseSettings.connection
import ro.runtimeterror.cms.database.daos.SectionDAO
import ro.runtimeterror.cms.database.daos.UserDAO
import ro.runtimeterror.cms.database.tables.ReviewTable
import ro.runtimeterror.cms.database.tables.SectionTable
import ro.runtimeterror.cms.database.tables.UserSectionChoiceTable
import ro.runtimeterror.cms.database.tables.UserTable
import ro.runtimeterror.cms.exceptions.NoSectionException
import ro.runtimeterror.cms.model.Qualifier
import ro.runtimeterror.cms.model.Section
import ro.runtimeterror.cms.model.UserReview
import ro.runtimeterror.cms.model.UserType

class SectionController {
    /**
     * Get the section associated with the author or null if the user does not present in any section
     */
    fun getSectionDetails(userId: Int): Section? = transaction(connection) {
        return@transaction SectionTable
            .select { SectionTable.userId eq userId }
            .map { SectionDAO.findById(it[SectionTable.id]) }
            .firstOrNull()
    }

    /**
     * Upload the presentation for the section in which the speaker is going to present in
     */
    fun uploadPresentation(userId: Int, path: String) = transaction(connection) {
        SectionTable
            .update({ SectionTable.userId eq userId }) {
                it[presentationDocumentPath] = path
            }
    }

    /**
     * Get all sections of the conference
     */
    fun getAllSections(): List<Section> = transaction(connection) {
        return@transaction SectionDAO
            .all()
            .toList()
    }


    /**
     * User chose to participate in this section
     */
    fun userSectionChoice(userId: Int, sectionId: Int) = transaction(connection) {
        //TODO check if this already exists
        UserSectionChoiceTable.insert {
            it[userID] = userId
            it[sectionID] = sectionId
        }
    }

    /**
     * Create a section
     */
    fun createSection(name: String, startTime: LocalDateTime, endTime: LocalDateTime) = transaction(connection) {
        SectionDAO.new {
            this@new.roomName = ""
            this@new.user = null
            this@new.paperId = null
            this@new.name = name
            this@new.startTime = startTime.toDateTime()
            this@new.endTime = endTime.toDateTime()
            this@new.presentationDocumentPath = ""
            this@new.sessionChair = null
        }
    }

    /**
     * Choose section chair
     */
    fun chooseSectionChair(sectionId: Int, userId: Int) = transaction(connection) {
        SectionTable.update({ SectionTable.id eq sectionId }) {
            it[SectionTable.sessionChair] = userId
        }
    }

    /**
     * Choose the specker that presents in this section as well as its paper
     */
    fun chooseSectionPresenter(userId: Int, paperId: Int, sectionId: Int) = transaction(connection) {
        SectionTable
            .update({ SectionTable.id eq sectionId }) {
                it[SectionTable.paperId] = paperId
                it[SectionTable.userId] = userId
            }
    }

    /**
     * Change the room name of the section
     */
    fun changeSectionRoom(sectionId: Int, roomName: String) = transaction(connection) {
        SectionTable.update({ SectionTable.id eq sectionId }) {
            it[SectionTable.roomName] = roomName
        }
    }

    /**
     * Get all the reviews for the paper that the author is going to present
     * If the author is a PC member, he is not allowed to see the reviews (return empty list)
     *  Throw an exception if the author is not assigned to a section
     */
    fun getReviews(userId: Int): List<UserReview> = transaction(connection) {
        if (isPcMember(userId)) {
            return@transaction emptyList()
        }
        return@transaction SectionTable
            .select { SectionTable.userId eq userId }
            //Gets the paperID of the papers that the user is presenting
            .mapNotNull { it[SectionTable.paperId] }
            //Gets the reviews of the paper that the user is presenting (In our program every user only has one paper to review)
            .map { paperID ->
                ReviewTable
                    .select { ReviewTable.paperID eq paperID }
                    .map {
                        UserReview(UserDAO
                            .wrapRow(
                                UserTable
                                    .selectAll()
                                    .first { user -> user[UserTable.id].value == it[ReviewTable.userID] }
                            )
                            , it[ReviewTable.recommandation]
                            , Qualifier.from(it[ReviewTable.qualifier]))
                    }
            }
            .toList()
            .firstOrNull() ?: throw NoSectionException("The user is not assigned to any section")
    }

    private fun isPcMember(userId: Int): Boolean = transaction(connection) {
        return@transaction UserTable
            .select { UserTable.id eq userId }
            .filter { it[UserTable.type] == UserType.PC_MEMBER.value }
            .isNotEmpty()
    }
}
