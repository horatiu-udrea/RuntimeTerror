package ro.runtimeterror.cms.model

interface User
{
    val userId: Int
    val name: String
    val username: String
    val password: String
    val accessLevel: AccessLevel
    val sessionID: Int
    val affiliation: String
    val email: String
    val hasTicket: Boolean
}