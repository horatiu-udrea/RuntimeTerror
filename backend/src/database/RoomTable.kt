package ro.runtimeterror.cms.database

import org.jetbrains.exposed.dao.id.IntIdTable

object RoomTable : IntIdTable("Rooms", "PK_RoomID")
{
    val name = varchar("Name", 50)
    val capacity = integer("Capacity")
}