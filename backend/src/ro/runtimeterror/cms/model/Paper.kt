package ro.runtimeterror.cms.model

interface Paper
{
    val paperId: Int
    val field: String
    val documentPath: String
    val conflicting: Boolean
    val proposalName: String
    val keywords: String
    val topics: String
    val listOfAuthors: String
    val accepted: Boolean
}