package ro.runtimeterror.cms.database.daos

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ro.runtimeterror.cms.database.tables.PaperSubmissionTable
import ro.runtimeterror.cms.database.tables.PaperTable
import ro.runtimeterror.cms.model.Paper
import ro.runtimeterror.cms.model.PaperStatus
import ro.runtimeterror.cms.model.User

class PaperDAO(id: EntityID<Int>) : IntEntity(id), Paper
{
    companion object : IntEntityClass<PaperDAO>(PaperTable)

    override val paperId: Int
        get() = id.value

    override var name by PaperTable.name
    override var field by PaperTable.field
    override var keywords by PaperTable.keywords
    override var topics by PaperTable.topics
    //TODO Eager loading Exposed framework
    val authorIterable by UserDAO via PaperSubmissionTable
    override var abstract by PaperTable.abstract
    override var documentPath by PaperTable.documentPath
    override val paperStatus: PaperStatus get() = PaperStatus.from(status)
    private var status by PaperTable.status
    override val authors:List<User> get() = authorIterable.toList()
}