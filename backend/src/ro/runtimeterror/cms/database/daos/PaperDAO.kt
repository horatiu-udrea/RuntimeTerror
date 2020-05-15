package ro.runtimeterror.cms.database.daos

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ro.runtimeterror.cms.database.tables.PaperTable
import ro.runtimeterror.cms.model.Paper

class PaperDAO(id: EntityID<Int>) : IntEntity(id), Paper
{
    companion object : IntEntityClass<PaperDAO>(PaperTable)

    override val paperId: Int
        get() = id.value

    override var user by UserDAO referencedOn PaperTable.userid
    override var name by PaperTable.name
    override var field by PaperTable.field
    override var keywords by PaperTable.keywords
    override var topics by PaperTable.topics
    override var authors by PaperTable.authors
    override var documentPath by PaperTable.documentPath
    override var conflicting by PaperTable.conflicting
    override var accepted by PaperTable.accepted
}