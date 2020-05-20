package ro.runtimeterror.cms.controller

import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import ro.runtimeterror.cms.database.DatabaseSettings
import ro.runtimeterror.cms.database.daos.UserDAO
import ro.runtimeterror.cms.database.tables.UserTable
import ro.runtimeterror.cms.model.User
import ro.runtimeterror.cms.model.UserType
import ro.runtimeterror.cms.model.validators.UserValidator

class UserController
{
    fun userList(): List<User>
    {
        val listOfUsers: MutableList<User> = ArrayList()
        transaction(DatabaseSettings.connection) {
            listOfUsers += UserDAO.all()
        }
        return listOfUsers
    }

    fun changeUser(userId: Int, type: UserType, validated: Boolean)
    {
        UserValidator.exists(userId)
        transaction {
            UserTable.update({UserTable.id eq userId}) {
                it[UserTable.type] = type.value
                it[UserTable.validated] = validated
            }
        }
    }

}
