package ro.runtimeterror.cms.controller

import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import ro.runtimeterror.cms.database.DatabaseSettings.connection
import ro.runtimeterror.cms.database.daos.PaperDAO
import ro.runtimeterror.cms.database.daos.UserDAO
import ro.runtimeterror.cms.database.daos.withAuthors
import ro.runtimeterror.cms.database.tables.PaperSubmissionTable
import ro.runtimeterror.cms.database.tables.PaperTable
import ro.runtimeterror.cms.database.tables.UserTable
import ro.runtimeterror.cms.model.Author
import ro.runtimeterror.cms.model.Paper
import ro.runtimeterror.cms.model.PaperStatus
import ro.runtimeterror.cms.model.UserType
import ro.runtimeterror.cms.model.validators.UserValidator

class PaperSubmissionController
{
    /**
     * Get all papers of the user
     */
    fun getPapers(userId: Int): List<Paper> = transaction(connection) {
        UserValidator.exists(userId)
        return@transaction PaperDAO.wrapRows(
            PaperTable.innerJoin(PaperSubmissionTable)
                .slice(PaperTable.columns)
                .select { PaperSubmissionTable.userID eq userId }
        ).map { withAuthors(it) }
    }

    private fun getPaperIdsFromUser(userId: Int): MutableList<Int> = transaction(connection) {
        return@transaction PaperSubmissionTable
            .select { PaperSubmissionTable.userID eq userId }
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
    ) = transaction(connection) {
        UserValidator.exists(userID)

        val newPaperId = addPaperAndGetID(name, abstract, field, keywords, topics)
        markAsAuthor(userID, newPaperId)

        authors.forEach { author ->
            val user = UserDAO.find { UserTable.email eq author.email }.firstOrNull() ?: UserDAO.new {
                this@new.name = author.name
                this@new.username = ""
                this@new.password = ""
                this@new.affiliation = ""
                this@new.email = author.email
                this@new.webPage = ""
                this@new.validated = false
                this@new.typeValue = UserType.AUTHOR.value
            }
            markAsAuthor(user.id.value, newPaperId)
        }
    }

    private fun markAsAuthor(userID: Int, newPaperId: Int)
    {
        PaperSubmissionTable.insert {
            it[this@insert.paperID] = newPaperId
            it[this@insert.userID] = userID
        }
    }

    private fun addPaperAndGetID(name: String, abstract: String, field: String, keywords: String, topics: String): Int =
        transaction(connection) {
            //adds paper to the paper table
            return@transaction PaperTable.insertAndGetId { newPaper ->
                newPaper[PaperTable.name] = name
                newPaper[PaperTable.abstract] = abstract
                newPaper[PaperTable.field] = field
                newPaper[PaperTable.keywords] = keywords
                newPaper[PaperTable.topics] = topics
                newPaper[PaperTable.documentPath] = ""
                newPaper[PaperTable.status] = PaperStatus.UNDECIDED.value
            }.value
        }

    fun uploadFullPaper(documentPath: String, paperID: Int, userID: Int) = transaction(connection) {
        UserValidator.exists(userID)
        PaperTable.update({ PaperTable.id eq paperID }) {
            it[PaperTable.documentPath] = documentPath
        }
    }

    fun changeAbstract(userID: Int, paperID: Int, abstract: String) = transaction(connection) {
        UserValidator.exists(userID)
        PaperTable.update({ PaperTable.id eq paperID }) {
            it[PaperTable.abstract] = abstract
        }
    }
}
