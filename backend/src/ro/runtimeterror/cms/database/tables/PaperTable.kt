package ro.runtimeterror.cms.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object PaperTable : IntIdTable("Papers", "PK_PaperID")
{
    var userid = reference("FK_UserID", UserTable.id)
    val field = varchar("Field", 100)
    val documentPath = varchar("DocumentPath", 100)
    val conflicting = bool("Conflicting")
    val proposalName = varchar("ProposalName", 100)
    val keywords = varchar("Keywords", 100)
    val topics = varchar("Topics", 100)
    val listOfAuthors = varchar("ListOfAuthors", 100)
    val accepted = bool("Accepted")
}