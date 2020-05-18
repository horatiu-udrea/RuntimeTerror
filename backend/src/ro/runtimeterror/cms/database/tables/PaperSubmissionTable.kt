package ro.runtimeterror.cms.database.tables

import org.jetbrains.exposed.sql.Table

object PaperSubmissionTable : Table("PaperSubmissions")
{


    val paperID = integer("FK_PaperID")
            .references(PaperTable.id)
//                    PaperSubmissionTable.reference("FK_PaperID", PaperTable.id)
    val userID = integer("FK_UserID")
        .references(UserTable.id)
//        PaperSubmissionTable.reference("FK_UserID", UserTable.id)

    override val primaryKey = PrimaryKey(
        paperID,
        userID, name = "PK_UserSectionChoice"
    )

}