package ro.runtimeterror.cms.database.daos

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ro.runtimeterror.cms.database.tables.SectionTable
import ro.runtimeterror.cms.model.Section
import ro.runtimeterror.cms.model.User

class SectionDAO(id: EntityID<Int>) : IntEntity(id), Section
{
    companion object : IntEntityClass<SectionDAO>(SectionTable)

    override val sectionId: Int
        get() = id.value
    override val roomName by SectionTable.roomName
    override val name by SectionTable.name
    override val description by SectionTable.description
    override val startTime by SectionTable.startTime
    override val endTime by SectionTable.endTime
    override val documentPath by SectionTable.documentPath
    private val sessionChairs by UserDAO optionalReferrersOn SectionTable.sessionChair
    override val sessionChair: User?
        get()
        {
            try
            {
                return sessionChairs.first()
            } catch (e: NoSuchElementException)
            {
                return null
            }
        }
}