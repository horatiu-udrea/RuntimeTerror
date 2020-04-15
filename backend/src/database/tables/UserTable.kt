package ro.runtimeterror.cms.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object UserTable : IntIdTable("Users", "PK_UserID")
{
    val name = varchar("Name", 50)
    val username = varchar("Username", 10)
    val password = varchar("Password", 10)
    val accessLevel = integer("AccessLevel")
    val sessionID = integer("SessionID").uniqueIndex()
    val affiliation = varchar("Affiliation", 50)
    val email = varchar("Email", 50)
    val hasTicket = bool("HasTicket")
}