package ro.runtimeterror.cms.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.jodatime.datetime

object SectionTable : IntIdTable("Sections", "PK_SectionID")
{
    val sessionChair = reference("FK_SessionChair", UserTable.id).nullable()
    val name = varchar("Name", 100)
    val description = varchar("Description", 500)
    val startTime = datetime("StartTime")
    val endTime = datetime("EndTime")
    val documentPath = varchar("DocumentPath", 100)
    val roomName = varchar("RoomName", 100)
}