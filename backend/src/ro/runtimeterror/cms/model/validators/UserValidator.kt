package ro.runtimeterror.cms.model.validators

import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import ro.runtimeterror.cms.database.DatabaseSettings
import ro.runtimeterror.cms.database.tables.UserTable
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
    }
}