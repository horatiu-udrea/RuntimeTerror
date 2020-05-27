package ro.runtimeterror.cms.controller

import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.dao.with
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import ro.runtimeterror.cms.database.DatabaseSettings.connection
import ro.runtimeterror.cms.database.daos.PaperDAO
import ro.runtimeterror.cms.database.daos.UserDAO
import ro.runtimeterror.cms.database.daos.withAuthors
import ro.runtimeterror.cms.database.tables.PaperSubmissionTable
import ro.runtimeterror.cms.database.tables.PaperTable
import ro.runtimeterror.cms.database.tables.SectionTable
import ro.runtimeterror.cms.model.Paper
import ro.runtimeterror.cms.model.PaperStatus
import ro.runtimeterror.cms.model.User

class PaperPresentationController
{

    /**
     * Get all accepted papers
     */
    fun getAcceptedPapers(): List<Paper> = transaction(connection) {
        return@transaction PaperDAO
            .all()
            .map { withAuthors(it) }
            .filter {
                it.paperStatus == PaperStatus.ACCEPTED}
    }

    private fun getAllPapersThatAreAssignedASection(): List<Paper> = transaction(connection) {
        /*return@transaction SectionTable
            .selectAll()
            .map { PaperDAO.findById(it[SectionTable.paperId]!!)!!.load(PaperDAO::authorIterable)}
            .toList()*/
        return@transaction PaperDAO
            .wrapRows(
                SectionTable.innerJoin(PaperTable)
                .select {
                    PaperTable.id eq SectionTable.paperId
                }
            )
            .toList()
    }
    /**
     * Get all accepted papers that are not assigned to a section
     */
    fun getRemainingPapers() : List<Paper> = transaction(connection) {
//        val papersAssigned: List<Int> = getAllPapersThatAreAssignedASection()
//            .map { it.paperId }
//            .toList()

        /*return@transaction SectionTable
            .selectAll()
            .filter{ it[SectionTable.paperId]  in papersAssigned }
            .map{PaperDAO.findById(it[SectionTable.paperId]!!)!!.load(PaperDAO::authorIterable)}
            .toList()*/
        return@transaction PaperDAO
            .wrapRows(
                SectionTable.innerJoin(PaperTable)
                    .slice(PaperTable.columns)
                    .select{PaperTable.id neq SectionTable.paperId}
            )
            .toList()
    }

    /**
     * Get the authors of the paper that are not assigned to speak in a section yet
     */
    fun getRemainingAuthors(paperId: Int): List<User> = transaction(connection){
        val assignedAuthors:List<Int> = authorsThatWereAlreadyAssignedASection(paperId)
            .map { it.userId }
            .toList()
        return@transaction PaperSubmissionTable
            .select { PaperSubmissionTable.paperID eq paperId }
            .filter{it[PaperSubmissionTable.userID] !in assignedAuthors}
            .map {UserDAO.findById(it[PaperSubmissionTable.userID])!!}
            .toList()
    }

    private fun authorsThatWereAlreadyAssignedASection(paperID: Int): List<User> = transaction (connection){
        return@transaction SectionTable
            .select{SectionTable.paperId eq paperID}
            .map { UserDAO.findById(it[SectionTable.userId]!!)!! }
            .toList()
    }

}