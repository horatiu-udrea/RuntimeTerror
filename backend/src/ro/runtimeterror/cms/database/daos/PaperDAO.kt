package ro.runtimeterror.cms.database.daos

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select
import ro.runtimeterror.cms.database.tables.PaperSubmissionTable
import ro.runtimeterror.cms.database.tables.PaperTable
import ro.runtimeterror.cms.database.tables.UserTable
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
    override var abstract by PaperTable.abstract
    override var documentPath by PaperTable.documentPath
    override val paperStatus: PaperStatus get() = PaperStatus.from(status)
    private var status by PaperTable.status
    override var authors:List<User> = mutableListOf()
}

fun withAuthors(paperDAO: PaperDAO): PaperDAO
{
    paperDAO.authors = UserDAO.wrapRows(
        UserTable.innerJoin(PaperSubmissionTable).innerJoin(PaperTable)
            .slice(UserTable.columns)
            .select { PaperSubmissionTable.paperID eq paperDAO.paperId }
    ).toList()
    return paperDAO
}