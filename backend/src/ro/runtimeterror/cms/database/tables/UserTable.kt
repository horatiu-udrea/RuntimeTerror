package ro.runtimeterror.cms.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object UserTable : IntIdTable("Users", "PK_UserID")
{
    val name = text("name")
    val username = varchar("username", 50)
    val password = varchar("password", 50)
    val affiliation = varchar("affiliation", 50)
    val email = varchar("email", 50).uniqueIndex()
    val webPage = varchar("webPage", 50)
    val validated = bool("validated")
    val type = integer("type")
}