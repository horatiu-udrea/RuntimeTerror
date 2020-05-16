package ro.runtimeterror.cms.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object PaperTable : IntIdTable("Papers", "PK_PaperID")
{
    val name = varchar("name", 100)
    val abstract = varchar("abstract", 500)
    val field = varchar("field", 100)
    val keywords = varchar("keywords", 100)
    val topics = varchar("topics", 100)
    val documentPath = varchar("documentPath", 100)
    val accepted = bool("accepted")
    val conflicting = bool("conflicting")

}