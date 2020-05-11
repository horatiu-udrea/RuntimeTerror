package ro.runtimeterror.cms.database.daos

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ro.runtimeterror.cms.database.tables.SectionTable
import ro.runtimeterror.cms.model.Section

class SectionDAO(id: EntityID<Int>) : IntEntity(id), Section
{
    companion object : IntEntityClass<SectionDAO>(SectionTable)

    override var roomName by SectionTable.roomName
    override var user by UserDAO optionalReferencedOn SectionTable.userId
    override var name by SectionTable.name
    override var startTime by SectionTable.startTime
    override var endTime by SectionTable.endTime
    override var presentationDocumentPath by SectionTable.presentationDocumentPath
    override var sessionChair by UserDAO optionalReferencedOn SectionTable.sessionChair

    override val sectionId: Int
        get() = id.value
}