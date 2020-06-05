package ro.runtimeterror.cms.controller

import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import ro.runtimeterror.cms.database.daos.UserDAO
import ro.runtimeterror.cms.database.tables.UserTable
import ro.runtimeterror.cms.model.User
import ro.runtimeterror.cms.model.UserType
import ro.runtimeterror.cms.model.validators.UserValidator
import ro.runtimeterror.cms.database.DatabaseSettings.connection

class UserController
{
    fun userList(): List<User> = transaction(connection) {
            return@transaction UserDAO
                .all()
                .toList()
        }

    fun changeUser(userId: Int, type: UserType, validated: Boolean) = transaction {
        UserValidator.exists(userId)

            UserTable.update({UserTable.id eq userId}) {
                it[UserTable.type] = type.value
                it[UserTable.validated] = validated
            }
        }
}
