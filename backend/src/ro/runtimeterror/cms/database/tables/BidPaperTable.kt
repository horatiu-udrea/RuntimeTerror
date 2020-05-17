package ro.runtimeterror.cms.database.tables

import org.jetbrains.exposed.sql.Table

object BidPaperTable : Table("BidPapers")
{
    val userID = integer("user").references(UserTable.id)
    val paperID = integer("paper").references(PaperTable.id)
    val paperBidResult = integer("paperBidResult")

    override val primaryKey = PrimaryKey(
        userID,
        paperID, name = "PK_BidPaper"
    )
}