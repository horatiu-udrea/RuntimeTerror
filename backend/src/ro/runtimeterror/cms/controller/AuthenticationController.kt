package ro.runtimeterror.cms.controller

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import ro.runtimeterror.cms.database.DatabaseSettings
import ro.runtimeterror.cms.database.DatabaseSettings.connection
import ro.runtimeterror.cms.database.daos.UserDAO
import ro.runtimeterror.cms.database.tables.UserTable
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
    fun authenticate(username: String, password: String): User?
    {
        try {
            var user: User? = null
            transaction(DatabaseSettings.connection) {
                user = UserDAO
                    .find {
                        (UserTable.username eq username) and (UserTable.password eq password)
                    }
                    .first()
            }
            return user
        }catch(exception: NoSuchElementException){
            exception.printStackTrace()
            return null
        }

    }

    /**
     * @return The user with the specified id or null if there is no such user
     */
    fun getUser(id: Int): User?
    {
        UserValidator.exists(id)
        var user: User? = null
        transaction(DatabaseSettings.connection) {
            user = UserDAO.findById(id)
        }
        return user
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
    )
    {
        transaction(connection) {//TODO fix this: you need to connect to the database here
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
        }
    }

}
