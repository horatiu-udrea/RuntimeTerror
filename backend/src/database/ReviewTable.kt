package ro.runtimeterror.cms.database

import org.jetbrains.exposed.sql.Table

object ReviewTable : Table("Reviews")
{
    val userID = reference("FK_UserID", UserTable.id)
    val paperID = reference("FK_PaperID", PaperTable.id)
    val content = varchar("Content", 5000)
    val score = integer("Score")

    override val primaryKey = PrimaryKey(userID, paperID, name = "PK_Review")
}