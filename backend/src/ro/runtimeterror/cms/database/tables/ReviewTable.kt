package ro.runtimeterror.cms.database.tables

import org.jetbrains.exposed.sql.Table

object ReviewTable : Table("Reviews")
{
    val userID = integer("user").references( UserTable.id)
    val paperID = integer("paper").references(PaperTable.id)
    val recommandation = varchar("recommandation", 500)
    val qualifier = integer("qualifier")

    override val primaryKey = PrimaryKey(
        userID,
        paperID, name = "PK_Review"
    )
}