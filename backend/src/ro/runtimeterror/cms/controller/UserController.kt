package ro.runtimeterror.cms.controller

import ro.runtimeterror.cms.model.User
import ro.runtimeterror.cms.model.UserType
import ro.runtimeterror.cms.repository.Repository

class UserController(private val repository: Repository)
{
    fun userList(): List<User>
    {
        TODO("Not yet implemented")
    }

    fun changeUser(userId: Int, type: UserType, validated: Boolean)
    {
        TODO("Not yet implemented")
    }

}
