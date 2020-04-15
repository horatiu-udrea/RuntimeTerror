package ro.runtimeterror.cms.database

import org.jetbrains.exposed.sql.Table

object BidPaperTable : Table("BidPapers")
{
    val userID = reference("FK_UserID", UserTable.id)
    val paperID = reference("FK_PaperID", PaperTable.id)
    val reviewChoice = integer("ReviewChoice")

    override val primaryKey = PrimaryKey(userID, paperID, name = "PK_BidPaper")
}