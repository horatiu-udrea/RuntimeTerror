package ro.runtimeterror.cms.networking.dto

import ro.runtimeterror.cms.model.User

data class UserCredentials(val username: String, val password: String)

data class UserInformation(
    val id: Int,
    val name: String,
    val username: String,
    val type: Int,
    val affiliation: String,
    val email: String,
    val webPage: String
)
//TODO add validated field
fun User.toUserInformation(): UserInformation
{
    return UserInformation(
        userId,
        name,
        username,
        type.value,
        affiliation,
        email,
        webPage
    )
}

data class UserDTO(
    val name: String,
    val username: String,
    val password: String,
    val affiliation: String,
    val email: String,
    val webPage: String
)

class UserUpdateDTO(
    val userId: Int,
    val type: Int,
    val validated: Boolean
)
