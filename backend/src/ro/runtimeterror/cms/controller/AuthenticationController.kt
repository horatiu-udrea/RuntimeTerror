package ro.runtimeterror.cms.controller

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
        TODO("Not yet implemented")
    }

    /**
     * @return The user with the specified id or null if there is no such user
     */
    fun getUser(id: Int): User?
    {
        TODO("Not yet implemented")
    }

}
