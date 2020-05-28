package ro.runtimeterror.cms.model.validators

import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import ro.runtimeterror.cms.database.DatabaseSettings
import ro.runtimeterror.cms.database.DatabaseSettings.connection
import ro.runtimeterror.cms.database.daos.PaperDAO
import ro.runtimeterror.cms.database.daos.UserDAO
import ro.runtimeterror.cms.database.tables.PaperSubmissionTable
import ro.runtimeterror.cms.database.tables.UserTable
import ro.runtimeterror.cms.exceptions.PCMemberIsAuthorException
import ro.runtimeterror.cms.exceptions.UserNotValidatedException
import java.lang.RuntimeException

class UserValidator {
    companion object UserValidator{
        fun exists(userID: Int){
            var exists = true
            transaction(connection) {
                exists = !UserTable.select{
                    UserTable.id eq userID
                }.empty()
            }
            if(!exists){
                throw RuntimeException("The user doesn't exists")
            }
        }

        fun pcMemberIsAuthor(userID: Int, paperID: Int) {
            if(
                PaperSubmissionTable
                    .select { PaperSubmissionTable.paperID eq paperID }
                    .filter { it[PaperSubmissionTable.userID] == userID }
                    .isNotEmpty()
            ){
                throw PCMemberIsAuthorException("The PCMember cannot bid a paper that he authored")
            }
        }

        fun authorValidated(userID: Int) = transaction(connection) {
            if (!UserDAO.findById(userID)!!.validated){
                throw UserNotValidatedException("The user is not validated!")
            }
        }
    }
}