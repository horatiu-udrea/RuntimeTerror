package ro.runtimeterror.cms.controller

import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import ro.runtimeterror.cms.database.daos.PaperDAO
import ro.runtimeterror.cms.database.daos.UserDAO
import ro.runtimeterror.cms.database.tables.BidPaperTable
import ro.runtimeterror.cms.database.tables.ReviewTable
import ro.runtimeterror.cms.database.tables.UserTable
import ro.runtimeterror.cms.database.DatabaseSettings.connection
import ro.runtimeterror.cms.exceptions.BidDoesNotExistException
import ro.runtimeterror.cms.exceptions.NoPapersException
import ro.runtimeterror.cms.model.*
import ro.runtimeterror.cms.model.validators.PaperValidator
import ro.runtimeterror.cms.model.validators.UserValidator
import java.lang.RuntimeException

class PaperAssignController
{
    /**
     * Retrieve all papers
     */
    fun getPapers(): List<Paper> =
        transaction (connection){
            return@transaction PaperDAO
                    .all()
                    .toList()
        }

    /**
     * Get the bidding result of the pc m,ember on the paper
     */
    fun getBidResult(paperId: Int, userId: Int): PaperBidResult =
        transaction(connection) {
            return@transaction PaperBidResult.from(
                BidPaperTable
                    .select{((BidPaperTable.paperID eq paperId) and (BidPaperTable.userID eq userId))}
                    .map {it[BidPaperTable.paperBidResult]}
                    .firstOrNull()?: throw BidDoesNotExistException("The bid does not exist!")
            )
        }

    /**
     * Get all PC Members
     */
    fun getPCMembers(): List<User> = transaction(connection) {
            return@transaction UserTable
                    .select { UserTable.type eq UserType.PC_MEMBER.value}
                    .map {user -> UserDAO.get(user[UserTable.id])}
                    .toList()
        }

    /**
     * Assign the paper to the pc member
     */
    fun assign(paperID: Int, userID: Int) = transaction(connection) {
            PaperValidator.exists(paperID)
            UserValidator.exists(userID)
            ReviewTable.insert {
                it[ReviewTable.userID] = userID
                it[ReviewTable.paperID] = paperID
            }
        }
}
