package ro.runtimeterror.cms.networking.route

import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import ro.runtimeterror.cms.controller.PaperSubmissionController
import ro.runtimeterror.cms.model.UserType
import ro.runtimeterror.cms.networking.authorize
import ro.runtimeterror.cms.networking.dto.PaperDTO
import ro.runtimeterror.cms.networking.dto.toDTO
import ro.runtimeterror.cms.networking.uploadFile
import ro.runtimeterror.cms.networking.userSession


fun Route.paperSubmissionRoute(paperSubmissionController: PaperSubmissionController)
{
    route("/paper") {
        get {
            authorize(UserType.AUTHOR)
            val session = userSession()
            val paper = paperSubmissionController.getPaper(session.id)
            call.respond(paper.toDTO())
        }

        post {
            authorize(UserType.AUTHOR)
            val paper = call.receive<PaperDTO>()
            val user = userSession()
            with(paper)
            {
                paperSubmissionController.submitProposal(
                    user.id,
                    name,
                    field,
                    keywords,
                    topics,
                    authors
                )
            }
        }
        put {
            authorize(UserType.AUTHOR)
            val user = userSession()
            val path = uploadFile()
            paperSubmissionController.fullPaperUploaded(path, user.id)
        }
    }
}