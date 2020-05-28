package ro.runtimeterror.cms.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.jodatime.datetime

object SectionTable : IntIdTable("Sections", "PK_SectionID")
{
    val sessionChair = reference("FK_SessionChair", UserTable.id).nullable()
    val userId = integer("FK_UserID").references(UserTable.id).nullable()
    val paperId =integer("FK_PaperID").references(PaperTable.id).nullable()
    val name = varchar("name", 100).default("")
    val presentationDocumentPath = varchar("presentationDocumentPath", 100).default("")
    val startTime = datetime("startTime")
    val endTime = datetime("endTime")
    val roomName = varchar("roomName", 100)
}