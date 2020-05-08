package ro.runtimeterror.cms.database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime

object ConferenceTable : Table("Conference")
{
    val name = varchar("ame", 100)
    val startDate = datetime("startDate")
    val endDate = datetime("endDate")
    val submissionDeadline = datetime("submissionDeadline")
    val proposalDeadline = datetime("proposalDeadline")
    val biddingDeadline = datetime("biddingDeadline")
    val submitPaperEarly = bool("submitPaperEarly")
}