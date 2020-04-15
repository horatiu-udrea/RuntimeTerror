package ro.runtimeterror.cms.model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ro.runtimeterror.cms.database.UserTable

class User(id: EntityID<Int>) : IntEntity(id)
{
    companion object : IntEntityClass<User>(UserTable)

    val name by UserTable.name
    val username by UserTable.username
    val password by UserTable.password
    val accessLevel by UserTable.accessLevel
    val sessionID by UserTable.sessionID
    val affiliation by UserTable.affiliation
    val email by UserTable.email
    val hasTicket by UserTable.hasTicket
}