package ro.runtimeterror.cms.model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ro.runtimeterror.cms.database.PaperTable

class Paper(id: EntityID<Int>) : IntEntity(id)
{
    companion object : IntEntityClass<Paper>(PaperTable)

    val field by PaperTable.field
    val documentPath by PaperTable.documentPath
    val conflicting by PaperTable.conflicting
    val proposalName by PaperTable.proposalName
    val keywords by PaperTable.keywords
    val topics by PaperTable.topics
    val listOfAuthors by PaperTable.listOfAuthors
    val accepted by PaperTable.accepted
}