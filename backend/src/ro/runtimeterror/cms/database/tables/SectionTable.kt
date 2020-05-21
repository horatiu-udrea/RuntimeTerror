package ro.runtimeterror.cms.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.jodatime.datetime

object SectionTable : IntIdTable("Sections", "PK_SectionID")
{
    val sessionChair = reference("FK_SessionChair", UserTable.id).nullable()
    val userId = reference("FK_UserID", UserTable.id).nullable()
    val paperId = reference("FK_PaperID", PaperTable.id).nullable()
    val name = varchar("name", 100)
    val presentationDocumentPath = varchar("presentationDocumentPath", 100)
    val startTime = datetime("startTime")
    val endTime = datetime("endTime")
    val roomName = varchar("roomName", 100)
}