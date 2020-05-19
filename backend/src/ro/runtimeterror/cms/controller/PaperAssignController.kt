package ro.runtimeterror.cms.controller

import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import ro.runtimeterror.cms.database.DatabaseSettings
import ro.runtimeterror.cms.database.daos.PaperDAO
import ro.runtimeterror.cms.database.daos.UserDAO
import ro.runtimeterror.cms.database.tables.BidPaperTable
import ro.runtimeterror.cms.database.tables.PaperTable
import ro.runtimeterror.cms.database.tables.ReviewTable
import ro.runtimeterror.cms.database.tables.UserTable
import ro.runtimeterror.cms.model.*
import ro.runtimeterror.cms.model.validators.PaperValidator
import ro.runtimeterror.cms.model.validators.UserValidator

class PaperAssignController
{
    /**
     * Retrieve all papers
     */
    fun getPapers(): List<Paper>
    {
        val listOfPapers: MutableList<Paper> = ArrayList<Paper>()
        transaction (DatabaseSettings.connection){
            PaperDAO
                    .all()
                    .forEach {paper -> listOfPapers += paper }
        }
        return listOfPapers
    }

    /**
     * Get the bidding result of the pc m,ember on the paper
     */
    fun getBidResult(paperId: Int, userId: Int): PaperBidResult
    {
        var bidResult: Int? = null
        transaction(DatabaseSettings.connection) {
            bidResult = BidPaperTable
                    .select{((BidPaperTable.paperID eq paperId) and (BidPaperTable.userID eq userId))}
                    .map {it[BidPaperTable.paperBidResult]}
                    .first()
        }
        return PaperBidResult.from(bidResult!!)
    }

    /**
     * Get all PC Members
     */
    fun getPCMembers(): List<User>
    {
        var users: List<User> = ArrayList()
        transaction(DatabaseSettings.connection) {
            users = UserTable
                    .select { UserTable.type eq UserType.PC_MEMBER.value}
                    .map {user -> UserDAO.get(user[UserTable.id])}
                    .toList()
        }
        return users
    }

    /**
     * Assign the paper to the pc member
     */
//    TODO not sure about this, if I don't add the qualifier and the status, is it null or would it give an error?
    fun assign(paperID: Int, userID: Int)
    {
        PaperValidator.exists(paperID)
        UserValidator.exists(userID)
        transaction(DatabaseSettings.connection) {
            ReviewTable.insert {
                it[ReviewTable.userID] = userID
                it[ReviewTable.paperID] = paperID
            }
        }
    }

}
