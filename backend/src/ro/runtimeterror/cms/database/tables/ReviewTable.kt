package ro.runtimeterror.cms.database.tables

import org.jetbrains.exposed.sql.Table

object ReviewTable : Table("Reviews")
{
    val userID = integer("FK_UserID").references( UserTable.id)
    val paperID = integer("FK_PaperID").references(PaperTable.id)
    val recommandation = varchar("recommandation", 500)
    val qualifier = integer("qualifier")

    override val primaryKey = PrimaryKey(
        userID,
        paperID, name = "PK_Review"
    )
}