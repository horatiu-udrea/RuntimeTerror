package ro.runtimeterror.cms.database.daos

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ro.runtimeterror.cms.database.tables.SectionTable
import ro.runtimeterror.cms.model.Paper
import ro.runtimeterror.cms.model.Section
import ro.runtimeterror.cms.model.User

class SectionDAO(id: EntityID<Int>) : IntEntity(id), Section
{
    companion object : IntEntityClass<SectionDAO>(SectionTable)

    override var roomName by SectionTable.roomName
    var sessionChairId by SectionTable.sessionChair
    var userId by SectionTable.userId
    var paperId by SectionTable.paperId
    override var name by SectionTable.name
    override var startTime by SectionTable.startTime
    override var endTime by SectionTable.endTime
    override var presentationDocumentPath by SectionTable.presentationDocumentPath

    override val sectionId: Int
        get() = id.value

    override var sessionChair: String = ""
    override var user: String = ""
    override var paper: String = ""
}