package ro.runtimeterror.cms.controller

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import ro.runtimeterror.cms.database.DatabaseSettings
import ro.runtimeterror.cms.database.daos.PaperDAO
import ro.runtimeterror.cms.database.tables.PaperSubmissionTable
import ro.runtimeterror.cms.database.tables.PaperTable
import ro.runtimeterror.cms.database.tables.UserTable
import ro.runtimeterror.cms.model.Author
import ro.runtimeterror.cms.model.Paper
import ro.runtimeterror.cms.model.PaperStatus
import ro.runtimeterror.cms.model.User
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
        userID: Int,
        name: String,
        abstract: String,
        field: String,
        keywords: String,
        topics: String,
        authors: List<Author>
    ) {

        UserValidator.exists(userID)
        val paperID:Int = addPaperAndGetID(name, abstract, field, keywords, topics)
        transaction(DatabaseSettings.connection) {
            PaperSubmissionTable.insert{
                it[PaperSubmissionTable.paperID] = paperID
                it[PaperSubmissionTable.userID] = userID
            }

            for(author in authors){
                UserTable.insert {
                    it[username] = author.name
                    it[email] = author.email
                }
            }

        }
    }

    private fun addPaperAndGetID(name: String, abstract: String, field: String, keywords: String, topics: String): Int {
        var paperID: Int? = null
        transaction(DatabaseSettings.connection) {
            //adds paper to the paper table
            paperID = PaperTable.insertAndGetId { newPaper ->
                newPaper[PaperTable.name] = name
                newPaper[PaperTable.abstract] = abstract
                newPaper[PaperTable.field] = field
                newPaper[PaperTable.keywords] = keywords
                newPaper[PaperTable.topics] = topics
                newPaper[status] = PaperStatus.UNDECIDED.value
            }.value
        }
        return paperID?: throw RuntimeException("Something went wrong when adding the paper")
    }

    fun uploadFullPaper(documentPath: String, paperID: Int, userID: Int)
    {
        transaction(DatabaseSettings.connection) {
            PaperTable.update({PaperTable.id eq paperID}) {
                it[PaperTable.documentPath] = documentPath
            }
        }
    }

    fun changeAbstract(userID: Int, paperID: Int, abstract: String)
    {
        transaction (DatabaseSettings.connection){
            PaperTable.update({PaperTable.id eq paperID}) {
                it[PaperTable.abstract] = abstract
            }
        }
    }
}
