package ro.runtimeterror.cms.database

import org.jetbrains.exposed.sql.Table

object UserSectionChoiceTable : Table("UserSectionChoices")
{
    val sectionID = reference("FK_SectionID", SectionTable.id)
    val userID = reference("FK_UserID", UserTable.id)

    override val primaryKey = PrimaryKey(sectionID, userID, name = "PK_UserSectionChoice")
}