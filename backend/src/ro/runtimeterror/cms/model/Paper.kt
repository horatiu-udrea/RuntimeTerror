package ro.runtimeterror.cms.model

interface Paper
{
    val user: User
    val paperId: Int
    val name: String
    val field: String
    val keywords: String
    val topics: String
    val authors: String
    val documentPath: String
    val accepted: Boolean
    val conflicting: Boolean
}