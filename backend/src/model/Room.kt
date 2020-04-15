package ro.runtimeterror.cms.model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ro.runtimeterror.cms.database.RoomTable

class Room(id: EntityID<Int>) : IntEntity(id)
{
    companion object : IntEntityClass<Room>(RoomTable)

    val name by RoomTable.name
    val capacity by RoomTable.capacity
}