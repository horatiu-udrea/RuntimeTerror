package ro.runtimeterror.cms.database.daos

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ro.runtimeterror.cms.database.tables.PaperTable
import ro.runtimeterror.cms.model.Paper

class PaperDAO(id: EntityID<Int>) : IntEntity(id), Paper
{
    companion object : IntEntityClass<PaperDAO>(PaperTable)

    override val field by PaperTable.field
    override val documentPath by PaperTable.documentPath
    override val conflicting by PaperTable.conflicting
    override val proposalName by PaperTable.proposalName
    override val keywords by PaperTable.keywords
    override val topics by PaperTable.topics
    override val listOfAuthors by PaperTable.listOfAuthors
    override val accepted by PaperTable.accepted
}