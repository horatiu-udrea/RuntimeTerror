package ro.runtimeterror.cms.networking.route

import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import ro.runtimeterror.cms.controller.PaperController
import ro.runtimeterror.cms.exceptions.UnauthorizedException
import ro.runtimeterror.cms.model.Paper
import ro.runtimeterror.cms.model.UserType
import ro.runtimeterror.cms.networking.UserSession
import ro.runtimeterror.cms.networking.authorize
import ro.runtimeterror.cms.networking.dto.PaperDTO
import ro.runtimeterror.cms.networking.dto.toDTO
import ro.runtimeterror.cms.networking.uploadFile



fun Route.paperRoute(paperController: PaperController)
{
    route("/papers") {
        get {
            val papers = paperController.getPapers()
            call.respond(papers.map { it.toDTO() })
        }

        post {
            authorize(UserType.AUTHOR)
            val paper = call.receive<PaperDTO>()
            val user = call.sessions.get<UserSession>() ?: throw UnauthorizedException("User not logged in!")
            with(paper)
            {
                paperController.submitProposal(
                    field,
                    name,
                    keywords,
                    topics,
                    authors,
                    user.id
                )
            }
        }
        put {
            authorize(UserType.AUTHOR)
            val user = call.sessions.get<UserSession>() ?: throw UnauthorizedException("User not logged in!")
            val path = uploadFile()
            paperController.fullPaperUploaded(path, user.id)
        }
    }
}