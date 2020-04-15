package ro.runtimeterror.cms.database

import org.jetbrains.exposed.sql.Table

object PresentationTable : Table("Presentations")
{
    val sectionID = reference("FK_SectionID", SectionTable.id)
    val userID = reference("FK_UserID", UserTable.id)
    val documentPath = varchar("DocumentPath", 100)

    override val primaryKey = PrimaryKey(sectionID, userID, name = "PK_Presentation")
}
