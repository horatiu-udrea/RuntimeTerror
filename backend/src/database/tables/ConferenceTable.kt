package ro.runtimeterror.cms.database.tables

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