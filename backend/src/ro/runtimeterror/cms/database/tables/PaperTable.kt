package ro.runtimeterror.cms.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object PaperTable : IntIdTable("Papers", "PK_PaperID")
{
    var userid = reference("FK_UserID", UserTable.id)
    val name = varchar("name", 100)
    val field = varchar("field", 100)
    val keywords = varchar("keywords", 100)
    val topics = varchar("topics", 100)
    val authors = varchar("authors", 100)
    val documentPath = varchar("documentPath", 100)
    val accepted = bool("accepted")
    val conflicting = bool("conflicting")

}