package ro.runtimeterror.cms.model

interface User
{
    val name: String
    val username: String
    val password: String
    val accessLevel: AccessLevel
    val sessionID: Int
    val affiliation: String
    val email: String
    val hasTicket: Boolean
}