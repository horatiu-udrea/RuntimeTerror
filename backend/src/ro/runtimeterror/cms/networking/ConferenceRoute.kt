package ro.runtimeterror.cms.networking

import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import ro.runtimeterror.cms.controller.ConferenceController
import ro.runtimeterror.cms.model.AccessLevel
import ro.runtimeterror.cms.model.Conference

class ConferenceDTO(
    val name: String,
    val startDate: String,
    val endDate: String,
    val abstractDeadline: String,
    val proposalDeadline: String,
    val biddingDeadline: String,
    val submitPaperEarly: Boolean
)

val dateTimeFormatter: DateTimeFormatter = DateTimeFormat.forPattern("dd.MM.yyyy")

fun ConferenceDTO.toModel(): Conference
{
    return Conference(
        name,
        dateTimeFormatter.parseDateTime(startDate),
        dateTimeFormatter.parseDateTime(endDate),
        dateTimeFormatter.parseDateTime(abstractDeadline),
        dateTimeFormatter.parseDateTime(proposalDeadline),
        dateTimeFormatter.parseDateTime(biddingDeadline),
        submitPaperEarly
    )
}


fun Conference.toDTO(): ConferenceDTO
{
    return ConferenceDTO(
        name,
        startDate.toString(dateTimeFormatter),
        endDate.toString(dateTimeFormatter),
        abstractDeadline.toString(dateTimeFormatter),
        proposalDeadline.toString(dateTimeFormatter),
        biddingDeadline.toString(dateTimeFormatter),
        submitPaperEarly
    )
}

data class Phase(val phase: Int)

fun Route.conferenceRoute(conferenceController: ConferenceController)
{
    route("/conference") {
        get {
            val conference = conferenceController.getConferenceDetails()
            call.respond(conference.toDTO())
        }

        post {
            authorize(AccessLevel.CO_CHAIR)
            val conferenceDTO = call.receive<ConferenceDTO>()
            conferenceController.changeConferenceInformation(conferenceDTO.toModel())
        }

        get("/phase") {
            call.respond(Phase(conferenceController.getPhase()))
        }
    }
}