package ro.runtimeterror.cms.networking.route

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import ro.runtimeterror.cms.controller.ConferenceController
import ro.runtimeterror.cms.model.Conference
import ro.runtimeterror.cms.model.UserType
import ro.runtimeterror.cms.networking.authorize
import ro.runtimeterror.cms.networking.dto.ConferenceDTO
import ro.runtimeterror.cms.networking.dto.toDTO
import ro.runtimeterror.cms.networking.dto.toModel


fun Route.conferenceRoute(conferenceController: ConferenceController)
{
    route("/conference") {
        get {
            val conference = conferenceController.getConferenceDetails()
            call.respond(conference.toDTO())
        }

        post {
            authorize(UserType.CO_CHAIR)
            val conferenceDTO = call.receive<ConferenceDTO>()
            conferenceController.changeConferenceInformation(conferenceDTO.toModel())
            call.respond(HttpStatusCode.OK)
        }
    }
}