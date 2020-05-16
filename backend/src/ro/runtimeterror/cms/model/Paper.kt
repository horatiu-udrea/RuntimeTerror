package ro.runtimeterror.cms.model

interface Paper
{
    val paperId: Int
    val name: String
    val abstract: String
    val field: String
    val keywords: String
    val topics: String
    val documentPath: String
    val accepted: Boolean
    val conflicting: Boolean
}