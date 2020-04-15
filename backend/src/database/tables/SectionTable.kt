package ro.runtimeterror.cms.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.jodatime.datetime
import ro.runtimeterror.cms.database.tables.RoomTable

object SectionTable : IntIdTable("Sections", "PK_SectionID")
{
    val roomID = reference("FK_RoomID", RoomTable.id)
    val name = varchar("Name", 100)
    val description = varchar("Description", 500)
    val startTime = datetime("StartTime")
    val endTime = datetime("EndTime")
}