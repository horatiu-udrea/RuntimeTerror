package ro.runtimeterror.cms.model.validators

import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import ro.runtimeterror.cms.database.DatabaseSettings
import ro.runtimeterror.cms.database.daos.PaperDAO
import ro.runtimeterror.cms.database.tables.UserTable
import ro.runtimeterror.cms.exceptions.PCMemberIsAuthorException
import java.lang.RuntimeException

class UserValidator {
    companion object UserValidator{
        fun exists(userID: Int){
            var exists = true
            transaction(DatabaseSettings.connection) {
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
                PaperDAO.findById(paperID)!!.authors.filter { it.userId == userID }.isNotEmpty()
                    ){
                throw PCMemberIsAuthorException("The PCMember cannot bid a paper that he authored")
            }
        }
    }
}