package ro.runtimeterror.cms.networking.dto

import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import ro.runtimeterror.cms.model.Conference


class ConferenceDTO(
    val name: String,
    val startDate: String,
    val endDate: String,
    val submissionDeadline: String,
    val proposalDeadline: String,
    val biddingDeadline: String,
    val submitPaperEarly: Boolean,
    val currentPhase: Int
)

val dateTimeFormatter: DateTimeFormatter = DateTimeFormat.forPattern("dd.MM.yyyy")

fun ConferenceDTO.toModel(): Conference
{
    return Conference(
        name,
        dateTimeFormatter.parseDateTime(startDate),
        dateTimeFormatter.parseDateTime(endDate),
        dateTimeFormatter.parseDateTime(submissionDeadline),
        dateTimeFormatter.parseDateTime(proposalDeadline),
        dateTimeFormatter.parseDateTime(biddingDeadline),
        submitPaperEarly,
        currentPhase
    )
}


fun Conference.toDTO(): ConferenceDTO
{
    return ConferenceDTO(
        name,
        startDate.toString(dateTimeFormatter),
        endDate.toString(dateTimeFormatter),
        submissionDeadline.toString(dateTimeFormatter),
        proposalDeadline.toString(dateTimeFormatter),
        biddingDeadline.toString(dateTimeFormatter),
        submitPaperEarly,
        currentPhase
    )
}