package ro.runtimeterror.cms.controller

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.omg.SendingContext.RunTime
import ro.runtimeterror.cms.database.DatabaseSettings
import ro.runtimeterror.cms.database.daos.PaperDAO
import ro.runtimeterror.cms.database.tables.PaperSubmissionTable
import ro.runtimeterror.cms.database.tables.PaperTable
import ro.runtimeterror.cms.model.Paper
import ro.runtimeterror.cms.model.PaperStatus
import ro.runtimeterror.cms.model.validators.UserValidator
import java.lang.RuntimeException

class PaperSubmissionController
{
    /**
     * Get all papers of the user
     */
    fun getPapers(userId: Int): List<Paper>
    {
        UserValidator.exists(userId)
        val paperIDs: List<Int> = getPaperIdsFromUser(userId)
        val listOfPapers: ArrayList<Paper> = ArrayList<Paper>()
        transaction (DatabaseSettings.connection){
            for(paperID in paperIDs){
                listOfPapers.add(PaperDAO.findById(paperID)!!)
            }
        }
        return listOfPapers
    }

    private fun getPaperIdsFromUser(userId: Int): List<Int> {
        var papers: List<Int>? = ArrayList()
        transaction(DatabaseSettings.connection) {
            papers = PaperSubmissionTable
                    .select{PaperSubmissionTable.userID eq userId}
                    .map {it[PaperSubmissionTable.paperID]}.
                    toList()
        }
        return papers?: throw RuntimeException("The user doesn't have any papers!")
    }

    /**
     * Author submits a paper
     */
    fun submitProposal(
        name: String,
        abstract: String,
        field: String,
        keywords: String,
        topics: String,
        status: PaperStatus,
        userID: Int
    ) {

        UserValidator.exists(userID)
//        checks if the user exists
        val paperID:Int = addPaperAndGetID(name, abstract, field, keywords, topics, status)
        transaction(DatabaseSettings.connection) {
            PaperSubmissionTable.insert{
                it[PaperSubmissionTable.paperID] = paperID
                it[PaperSubmissionTable.userID] = userID
            }
        }
    }

    private fun addPaperAndGetID(name: String, abstract: String, field: String, keywords: String, topics: String, status: PaperStatus): Int {
        var paperID: Int? = null
        transaction(DatabaseSettings.connection) {
            //adds paper to the paper table
            paperID = PaperTable.insertAndGetId { newPaper ->
                newPaper[PaperTable.name] = name
                newPaper[PaperTable.abstract] = abstract
                newPaper[PaperTable.field] = field
                newPaper[PaperTable.keywords] = keywords
                newPaper[PaperTable.topics] = topics
                newPaper[PaperTable.status] = status.value
            }.value
        }
        return paperID?: throw RuntimeException("Something went wrong when adding the paper")
    }

    fun uploadFullPaper(documentPath: String, paperId: Int)
    {
        transaction(DatabaseSettings.connection) {
            PaperTable.update({PaperTable.id eq paperId}) {
                it[PaperTable.documentPath] = documentPath
            }
        }
    }

    fun changeAbstract(paperId: Int, abstract: String)
    {
        transaction (DatabaseSettings.connection){
            PaperTable.update({PaperTable.id eq paperId}) {
                it[PaperTable.abstract] = abstract
            }
        }
    }
}
