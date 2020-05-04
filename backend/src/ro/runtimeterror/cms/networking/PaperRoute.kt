package ro.runtimeterror.cms.networking

import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import ro.runtimeterror.cms.controller.PaperController
import ro.runtimeterror.cms.exceptions.UnauthorizedException
import ro.runtimeterror.cms.model.AccessLevel
import ro.runtimeterror.cms.model.Paper

fun Route.paperRoute(paperController: PaperController)
{
    route("/papers") {
        get {
            val papers = paperController.getPapers()
            call.respond(papers)
        }

        post {
            authorize(AccessLevel.AUTHOR)
            val paper = call.receive<Paper>()
            val user = call.sessions.get<UserSession>() ?: throw UnauthorizedException("User not logged in!")
            paperController.addPaper(paper, user.id)
        }
    }
}