package ro.runtimeterror.cms.database.daos

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ro.runtimeterror.cms.database.tables.UserTable
import ro.runtimeterror.cms.model.AccessLevel
import ro.runtimeterror.cms.model.User

class UserDAO(id: EntityID<Int>) : IntEntity(id), User
{
    companion object : IntEntityClass<UserDAO>(UserTable)

    override val name by UserTable.name
    override val username by UserTable.username
    override val password by UserTable.password
    val level by UserTable.accessLevel
    override val sessionID by UserTable.sessionID
    override val affiliation by UserTable.affiliation
    override val email by UserTable.email
    override val hasTicket by UserTable.hasTicket

    override val accessLevel: AccessLevel
        get() = AccessLevel.from(level)
}