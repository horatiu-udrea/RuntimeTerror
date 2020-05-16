package ro.runtimeterror.cms.networking.route

import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.routing.Route
import io.ktor.routing.put
import io.ktor.routing.route
import ro.runtimeterror.cms.controller.PaperDecisionController
import ro.runtimeterror.cms.model.UserType
import ro.runtimeterror.cms.networking.authorize
import ro.runtimeterror.cms.networking.dto.PaperDecisionDTO

fun Route.paperDecisionRoute(paperDecisionController: PaperDecisionController)
{
    route("/paper/decision") {
        put {
            authorize(UserType.CO_CHAIR)
            val (paperId, status) = call.receive<PaperDecisionDTO>()
            paperDecisionController.decide(paperId, status)
        }
    }
}