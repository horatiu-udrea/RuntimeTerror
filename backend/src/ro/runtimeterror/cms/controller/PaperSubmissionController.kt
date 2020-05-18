package ro.runtimeterror.cms.controller

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
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
        val paperIDs: MutableList<Int> = getPaperIdsFromUser(userId)
        val listOfPapers: MutableList<Paper> = ArrayList<Paper>()
        transaction (DatabaseSettings.connection){
            for(paperID in paperIDs){
                listOfPapers += PaperDAO.findById(paperID)!!
            }
        }
        return listOfPapers
    }

    private fun getPaperIdsFromUser(userId: Int): MutableList<Int> {
        val papers: MutableList<Int> = ArrayList()
        transaction(DatabaseSettings.connection) {
            PaperSubmissionTable
                    .select{PaperSubmissionTable.userID eq userId}
                    .forEach(){papers += it[PaperSubmissionTable.paperID]}

        }
        return papers
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
        status: Int,
        userID: Int,
        authors: String
    ) {

        UserValidator.exists(userID)
        val paperID:Int = addPaperAndGetID(name, abstract, field, keywords, topics, status)
        transaction(DatabaseSettings.connection) {
            PaperSubmissionTable.insert{
                it[PaperSubmissionTable.paperID] = paperID
                it[PaperSubmissionTable.userID] = userID
            }
        }
    }

    private fun addPaperAndGetID(name: String, abstract: String, field: String, keywords: String, topics: String, status: Int): Int {
        var paperID: Int? = null
        transaction(DatabaseSettings.connection) {
            //adds paper to the paper table
            paperID = PaperTable.insertAndGetId { newPaper ->
                newPaper[PaperTable.name] = name
                newPaper[PaperTable.abstract] = abstract
                newPaper[PaperTable.field] = field
                newPaper[PaperTable.keywords] = keywords
                newPaper[PaperTable.topics] = topics
                newPaper[PaperTable.status] = status
            }.value
        }
        return paperID?: throw RuntimeException("Something went wrong when adding the paper")
    }

    fun uploadFullPaper(documentPath: String, paperID: Int)
    {
        transaction(DatabaseSettings.connection) {
            PaperTable.update({PaperTable.id eq paperID}) {
                it[PaperTable.documentPath] = documentPath
            }
        }
    }

    fun changeAbstract(paperID: Int, abstract: String)
    {
        transaction (DatabaseSettings.connection){
            PaperTable.update({PaperTable.id eq paperID}) {
                it[PaperTable.abstract] = abstract
            }
        }
    }
}
