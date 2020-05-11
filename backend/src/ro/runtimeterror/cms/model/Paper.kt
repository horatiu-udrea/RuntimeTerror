package ro.runtimeterror.cms.model

interface Paper
{
    val paperId: Int
    val userId: User?
    val name: String
    val field: String
    val keywords: String
    val topics: String
    val authors: String
    val documentPath: String
    val accepted: Boolean
    val conflicting: Boolean
}