package ro.runtimeterror.cms.database.tables

import org.jetbrains.exposed.sql.Table

object BidPaperTable : Table("BidPapers")
{
    val userID = reference("FK_UserID", UserTable.id)
    val paperID = reference("FK_PaperID", PaperTable.id)
    val paperBidResult = integer("paperBidResult")

    override val primaryKey = PrimaryKey(
        userID,
        paperID, name = "PK_BidPaper"
    )
}