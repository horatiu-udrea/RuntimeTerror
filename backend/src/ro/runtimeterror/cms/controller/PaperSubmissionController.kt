package ro.runtimeterror.cms.controller

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import ro.runtimeterror.cms.database.DatabaseSettings
import ro.runtimeterror.cms.database.daos.PaperDAO
import ro.runtimeterror.cms.database.daos.UserDAO
import ro.runtimeterror.cms.database.tables.PaperTable
import ro.runtimeterror.cms.database.tables.UserTable
import ro.runtimeterror.cms.model.Paper
import ro.runtimeterror.cms.model.User

data class Author(
    val name: String,
    val email: String
)

fun User.toAuthor(): Author
{
    return Author(name, email)
}

class PaperSubmissionController
{
    /**
     * Get all papers of the user
     */
    fun getPapers(userId: Int): List<Paper>
    {
        var listOfPapers: List<Paper> = ArrayList<Paper>()
        transaction (DatabaseSettings.connection){
            listOfPapers = PaperDAO.all().toList()
        }
        return listOfPapers
    }

    /**
     * Author submitted a paper
     */
    fun submitProposal(
        userId: Int,
        name: String,
        field: String,
        keywords: String,
        topics: String,
        abstract: String,
        authors: List<Author>
    )
    {
//        checks if the user exists
        transaction(DatabaseSettings.connection) {
            if(
                UserDAO.find{
                    UserTable.id eq userId
                }.empty()
            ) {
                throw RuntimeException("User does not exist!")
            }
//            Checks if the user already as a paper
            else if(
                        !PaperDAO.find{
                            PaperTable.userid eq userId
                        }.empty()
                    ){
                throw RuntimeException("User already has a paper!")
            }
        }
        //adds paper to the paper table
        SchemaUtils.create(PaperTable)

        transaction(DatabaseSettings.connection) {
            PaperTable.insert{ newPaper ->
                newPaper[userid] = userId
                newPaper[PaperTable.field] = field
                newPaper[PaperTable.name] = name
                newPaper[PaperTable.keywords] = keywords
                newPaper[PaperTable.topics] = topics
                newPaper[PaperTable.authors] = authors
                newPaper[accepted] = false
                newPaper[conflicting] = false

            }
        }
    }

    fun uploadFullPaper(path: String, paperId: Int, userId: Int)
    {
        transaction(DatabaseSettings.connection) {
            PaperTable.update({PaperTable.userid eq userId}) {
                it[documentPath] = path
            }
        }
    }

    fun changeAbstract(userId:Int, paperId: Int, abstract: String)
    {
        TODO("Not yet implemented")
    }


}
