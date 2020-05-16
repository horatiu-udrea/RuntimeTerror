package ro.runtimeterror.cms.database.tables

import org.jetbrains.exposed.sql.Table

object PaperSubmissionTable : Table("PaperSubmissions")
{

    val paperID =  PaperSubmissionTable.reference("FK_PaperID", PaperTable.id)
    val userID = PaperSubmissionTable.reference("FK_UserID", UserTable.id)

    override val primaryKey = PrimaryKey(
        paperID,
        userID, name = "PK_UserSectionChoice"
    )

}