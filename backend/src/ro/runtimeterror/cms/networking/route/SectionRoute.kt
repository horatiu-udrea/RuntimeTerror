package ro.runtimeterror.cms.networking.route

import io.ktor.routing.Route
import io.ktor.routing.route
import ro.runtimeterror.cms.controller.SectionController

fun Route.sectionRoute(sectionController: SectionController)
{
    route("/sections") {
        TODO("not implemented")
    }
}