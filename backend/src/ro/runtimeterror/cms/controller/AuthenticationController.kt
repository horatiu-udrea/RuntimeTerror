package ro.runtimeterror.cms.controller

import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import ro.runtimeterror.cms.database.DatabaseSettings
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

//        Suggests that the user has an account but hasn't activated it
        if(
            !UserTable.select {
                (UserTable.email eq email) and
                (UserTable.username eq username) and
                (UserTable.name eq "") and
                (UserTable.affiliation eq "")
            }.empty()
        ){
            UserTable.insert {
                it[UserTable.name] = name
                it[UserTable.affiliation] = affiliation
                it[UserTable.email] = email
                it[UserTable.webPage] = webPage
                it[validated] = true
                it[type] = UserType.AUTHOR.value
            }
            return@transaction
        }
//            Checks if the username exists in the database
            if(
                !UserTable.select {
                UserTable.username eq username
                }.empty()
            ) {
                throw UserAlreadyExistsException("The user $username already exists!")
            }else if(
                !UserTable.select {
                UserTable.username eq username
                }.empty()
            ){
                throw UserAlreadyExistsException("The email $email is already registered!")
            }

            UserTable.insert {
                it[UserTable.name] = name
                it[UserTable.username] = username
                it[UserTable.password] = password
                it[UserTable.affiliation] = affiliation
                it[UserTable.email] = email
                it[UserTable.webPage] = webPage
                it[validated] = true
                it[type] = UserType.AUTHOR.value
            }
        }
}
