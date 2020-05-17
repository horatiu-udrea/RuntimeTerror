package ro.runtimeterror.cms.database.tables

import org.jetbrains.exposed.sql.Table

object PaperSubmissionTable : Table("PaperSubmissions")
{


    val paperID = integer("paper")
            .references(PaperTable.id)
//                    PaperSubmissionTable.reference("FK_PaperID", PaperTable.id)
    val userID = integer("user")
        .references(UserTable.id)
//        PaperSubmissionTable.reference("FK_UserID", UserTable.id)

    override val primaryKey = PrimaryKey(
        paperID,
        userID, name = "PK_UserSectionChoice"
    )

}