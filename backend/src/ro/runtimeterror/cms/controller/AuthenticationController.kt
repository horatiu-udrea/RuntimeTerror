package ro.runtimeterror.cms.controller

import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import ro.runtimeterror.cms.database.DatabaseSettings.connection
import ro.runtimeterror.cms.database.daos.UserDAO
import ro.runtimeterror.cms.database.tables.UserTable
import ro.runtimeterror.cms.exceptions.UserAlreadyExistsException
import ro.runtimeterror.cms.model.User
import ro.runtimeterror.cms.model.UserType
import ro.runtimeterror.cms.model.validators.UserValidator

class AuthenticationController
{
    /**
     * Authenticates the user
     *
     * @return The found user or null if the credentials are invalid
     */
    fun authenticate(username: String, password: String): User? = transaction(connection) {
        return@transaction UserDAO
            .find {
                (UserTable.username eq username) and (UserTable.password eq password)
            }
            .firstOrNull()
    }


    /**
     * @return The user with the specified id or null if there is no such user
     */
    fun getUser(id: Int): User? = transaction(connection) {
        UserValidator.exists(id)
        return@transaction UserDAO.findById(id)
    }


    /**
     * Create new user
     */
    fun newUser(
        name: String,
        username: String,
        password: String,
        affiliation: String,
        email: String,
        webPage: String
    ) = transaction(connection) {

        val existingUser = UserDAO.find { UserTable.email eq email }.firstOrNull()
        if (existingUser == null)
        {
            if (!UserTable.select { UserTable.username eq username }.empty())
            {
                throw UserAlreadyExistsException("The username '$username' already exists!")
            }

            UserTable.insert {
                it[UserTable.name] = name
                it[UserTable.username] = username
                it[UserTable.password] = password
                it[UserTable.affiliation] = affiliation
                it[UserTable.email] = email
                it[UserTable.webPage] = webPage
                it[validated] = false
                it[type] = UserType.AUTHOR.value
            }
            return@transaction
        }
        if (existingUser.username == "")
        {
            existingUser.apply {
                this@apply.name = name
                this@apply.affiliation = affiliation
                this@apply.username = username
                this@apply.webPage = webPage
                this@apply.validated = false
                this@apply.type = UserType.AUTHOR
            }
            return@transaction
        }

        throw UserAlreadyExistsException("The email $email is already registered!")
    }
}
