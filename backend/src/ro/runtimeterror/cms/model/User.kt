package ro.runtimeterror.cms.model

interface User
{
    val userId: Int
    val name: String
    val username: String
    val password: String
    val affiliation: String
    val email: String
    val webPage: String
    val validated: Boolean
    val hasTicket: Boolean
    val type: Int
}