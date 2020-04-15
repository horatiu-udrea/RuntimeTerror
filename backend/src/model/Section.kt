package ro.runtimeterror.cms.model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ro.runtimeterror.cms.database.SectionTable

class Section(id: EntityID<Int>) : IntEntity(id)
{
    companion object : IntEntityClass<Section>(SectionTable)

    val roomID by SectionTable.roomID
    val name by SectionTable.name
    val description by SectionTable.description
    val startTime by SectionTable.startTime
    val endTime by SectionTable.endTime
}