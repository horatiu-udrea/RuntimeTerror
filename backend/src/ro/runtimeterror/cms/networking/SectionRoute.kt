package ro.runtimeterror.cms.networking

import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import ro.runtimeterror.cms.controller.ConferenceController
import ro.runtimeterror.cms.controller.SectionController
import ro.runtimeterror.cms.model.AccessLevel

fun Route.sectionRoute(sectionController: SectionController)
{
    route("/sections") {
        TODO("not implemented")
    }
}