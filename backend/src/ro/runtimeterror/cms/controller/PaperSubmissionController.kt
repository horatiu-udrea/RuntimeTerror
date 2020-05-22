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
import ro.runtimeterror.cms.database.DatabaseSettings.connection

class PaperSubmissionController
{
    /**
     * Get all papers of the user
     */
    fun getPapers(userId: Int): List<Paper> = transaction(connection){
            UserValidator.exists(userId)
            return@transaction getPaperIdsFromUser(userId)
                .map { PaperDAO.findById(it)!! }
                .toList()
        }

    private fun getPaperIdsFromUser(userId: Int): MutableList<Int> = transaction(connection) {
            return@transaction PaperSubmissionTable
                    .select{PaperSubmissionTable.userID eq userId}
                    .map { it[PaperSubmissionTable.paperID] }
                    .toMutableList()
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
    ) =
        transaction(connection) {
            UserValidator.exists(userID)

            PaperSubmissionTable.insert{
                it[paperID] = addPaperAndGetID(name, abstract, field, keywords, topics)
                it[PaperSubmissionTable.userID] = userID
            }
            authors
                .forEach{author ->
                    UserTable.insert {
                        it[username] = author.name
                        it[email] = author.email
                    }
                }
        }

    private fun addPaperAndGetID(name: String, abstract: String, field: String, keywords: String, topics: String): Int = transaction(connection) {
            //adds paper to the paper table
            return@transaction PaperTable.insertAndGetId { newPaper ->
                newPaper[PaperTable.name] = name
                newPaper[PaperTable.abstract] = abstract
                newPaper[PaperTable.field] = field
                newPaper[PaperTable.keywords] = keywords
                newPaper[PaperTable.topics] = topics
                newPaper[status] = PaperStatus.UNDECIDED.value
            }.value
        }

    fun uploadFullPaper(documentPath: String, paperID: Int, userID: Int) =
        transaction(connection) {
            PaperTable.update({PaperTable.id eq paperID}) {
                it[PaperTable.documentPath] = documentPath
        }
    }

    fun changeAbstract(userID: Int, paperID: Int, abstract: String) =
        transaction (connection){
            PaperTable.update({PaperTable.id eq paperID}) {
                it[PaperTable.abstract] = abstract
            }
    }
}
