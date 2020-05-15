package ro.runtimeterror.cms.database.tables

import org.jetbrains.exposed.sql.Table

object PaperSubmissionTable : Table("PaperSubmissions")
{

    val paperID = UserSectionChoiceTable.reference("FK_PaperID", PaperTable.id)
    val userID = UserSectionChoiceTable.reference("FK_UserID", UserTable.id)

    override val primaryKey = PrimaryKey(
        paperID,
        userID, name = "PK_UserSectionChoice"
    )

}