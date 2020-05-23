package ro.runtimeterror.cms.database.tables

import org.jetbrains.exposed.sql.Table

object UserSectionChoiceTable : Table("UserSectionChoices")
{
    val sectionID = integer("FK_SectionID").references(SectionTable.id)
    val userID = integer("FK_UserID").references( UserTable.id)

    override val primaryKey = PrimaryKey(
        sectionID,
        userID, name = "PK_UserSectionChoice"
    )
}