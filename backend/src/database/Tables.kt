package ro.runtimeterror.cms.database

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime

object ConferenceTable : Table("Conference")
{
    val name = varchar("Name", 100)
    val startDate = datetime("StartDate")
    val endDate = datetime("EndDate")
    val abstractDeadline = datetime("AbstractDeadline")
    val proposalDeadline = datetime("ProposalDeadline")
    val biddingDeadline = datetime("BiddingDeadline")
    val submitPaperEarly = bool("SubmitPaperEarly")
}

object UserTable : IntIdTable("Users", "PK_UserID")
{
    val name = varchar("Name", 50)
    val username = varchar("Username", 10)
    val password = varchar("Password", 10)
    val accessLevel = varchar("AccessLevel", 50)
    val sessionID = integer("SessionID").uniqueIndex()
    val affiliation = varchar("Affiliation", 50)
    val email = varchar("Email", 50)
    val hasTicket = bool("HasTicket")
}

object PaperTable : IntIdTable("Papers", "PK_PaperID")
{
    val field = varchar("Field", 100)
    val documentPath = varchar("DocumentPath", 100)
    val conflicting = bool("Conflicting")
    val proposalName = varchar("ProposalName", 100)
    val keywords = varchar("Keywords", 100)
    val topics = varchar("Topics", 100)
    val listOfAuthors = varchar("ListOfAuthors", 100)
    val accepted = bool("Accepted")
}

object BidPaperTable : Table("BidPapers")
{
    val userID = reference("FK_UserID", UserTable.id)
    val paperID = reference("FK_PaperID", PaperTable.id)
    val reviewChoice = integer("ReviewChoice")

    override val primaryKey = PrimaryKey(userID, paperID, name = "PK_BidPaper")
}

object ReviewTable : Table("Reviews")
{
    val userID = reference("FK_UserID", UserTable.id)
    val paperID = reference("FK_PaperID", PaperTable.id)
    val content = varchar("Content", 5000)
    val score = integer("Score")

    override val primaryKey = PrimaryKey(userID, paperID, name = "PK_Review")
}

object RoomTable : IntIdTable("Rooms", "PK_RoomID")
{
    val name = varchar("Name", 50)
    val capacity = integer("Capacity")
}

object SectionTable : IntIdTable("Sections", "PK_SectionID")
{
    val roomID = reference("FK_RoomID", RoomTable.id)
    val name = varchar("Name", 100)
    val description = varchar("Description", 500)
    val startTime = datetime("StartTime")
    val endTime = datetime("EndTime")
}

object UserSectionChoiceTable : Table("UserSectionChoices")
{
    val sectionID = reference("FK_SectionID", SectionTable.id)
    val userID = reference("FK_UserID", UserTable.id)

    override val primaryKey = PrimaryKey(sectionID, userID, name = "PK_UserSectionChoice")
}

object PresentationTable : Table("Presentations")
{
    val sectionID = reference("FK_SectionID", SectionTable.id)
    val userID = reference("FK_UserID", UserTable.id)
    val documentPath = varchar("DocumentPath", 100)

    override val primaryKey = PrimaryKey(sectionID, userID, name = "PK_Presentation")
}
