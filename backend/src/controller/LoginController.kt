import ro.runtimeterror.cms.model.AccessLevel
import ro.runtimeterror.cms.model.User
import ro.runtimeterror.cms.repository.Repository
import kotlin.random.Random

/**
 *  Controller that is responsible for verifying user login and registration
 */
object LoginController{
    /**
     * logs an existing user to the application by checking his log-in details with the database
     *
     * @param {String} userName - A user's unique username
     * @param {String} password - a user's password
     *
     * @return a boolean specifying if if the login attempt was successful
     *
     */
    fun login(userName: String, password: String): Boolean{
        if(TODO("check if the user is not valid"))  return false
//        if(!validLogins(userName, password)) return false;

        //TODO {maybe} a new session should be created every time the user logs in?
        return TODO("Start the session")
    }
    /**
     * Register's a normal user to the application by adding their details to the database
     *
     * @param {String} name - The name of the new user
     * @param {String} email - the email of the new user
     * @param {String} password - the user's password
     * @param {String} affiliation - the organization that the user is affiliated with
     *
     * @return {Boolean} returns a boolean if the user's registration was done successfully
     */
    fun register(name: String, email: String, userName: String, password: String, affiliation: String? = null): Boolean{

        val userAccessLevel = AccessLevel.NORMAL
        //TODO {maybe} when the user first makes an account he doesn't have a ticket
        val hasTicket = false

        return TODO("Register the user")
    }
}