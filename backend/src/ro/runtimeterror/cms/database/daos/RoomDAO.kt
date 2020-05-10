//package ro.runtimeterror.cms.database.daos
//
//import org.jetbrains.exposed.dao.IntEntity
//import org.jetbrains.exposed.dao.IntEntityClass
//import org.jetbrains.exposed.dao.id.EntityID
//import ro.runtimeterror.cms.database.tables.RoomTable
//import ro.runtimeterror.cms.model.Room
//
//class RoomDAO(id: EntityID<Int>) : IntEntity(id), Room
//{
//    companion object : IntEntityClass<RoomDAO>(RoomTable)
//
//    override val name by RoomTable.name
//    override val capacity by RoomTable.capacity
//}