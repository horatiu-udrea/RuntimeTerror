package ro.runtimeterror.cms.controller

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import ro.runtimeterror.cms.database.DatabaseSettings
import ro.runtimeterror.cms.database.daos.UserDAO
import ro.runtimeterror.cms.database.tables.UserTable
import ro.runtimeterror.cms.model.User
import ro.runtimeterror.cms.repository.Repository

class AuthenticationController(private val repository: Repository)
{
    /**
     * Authenticates the user
     *
     * @return The found user or null if the credentials are invalid
     */
    fun authenticate(username: String, password: String): User?
    {
        var user: User? = null
        transaction(DatabaseSettings.connection) {
            SchemaUtils.create(UserTable)
            user = UserDAO
                .find {
                    (UserTable.username eq username) and (UserTable.password eq password)
                }
                .first()
        }
        return user
    }

    /**
     * @return The user with the specified id or null if there is no such user
     */
    fun getUser(id: Int): User?
    {
        var user: User? = null
        transaction(DatabaseSettings.connection) {
            user = UserDAO.findById(id)
        }
        return user
    }

}
