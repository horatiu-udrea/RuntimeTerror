package ro.runtimeterror.cms.networking.route

import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import ro.runtimeterror.cms.controller.PaperSubmissionController
import ro.runtimeterror.cms.model.UserType
import ro.runtimeterror.cms.networking.authorize
import ro.runtimeterror.cms.networking.dto.AbstractDTO
import ro.runtimeterror.cms.networking.dto.PaperDTO
import ro.runtimeterror.cms.networking.dto.toDTO
import ro.runtimeterror.cms.networking.dto.toDTOWithId
import ro.runtimeterror.cms.networking.uploadFile
import ro.runtimeterror.cms.networking.userSession


fun Route.paperSubmissionRoute(paperSubmissionController: PaperSubmissionController)
{
    route("/paper") {
        get {
            authorize(UserType.AUTHOR)
            val user = userSession()
            val papers = paperSubmissionController.getPapers(user.id)
            call.respond(papers.toDTO())
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
                    abstract,
                    authors
                )
            }
        }
        put {
            authorize(UserType.AUTHOR)
            val user = userSession()
            val abstract = call.receive<AbstractDTO>()
            paperSubmissionController.changeAbstract(user.id, abstract.paperId, abstract.abstract)
        }

        put("/full/{paperId}") {
            authorize(UserType.AUTHOR)
            val user = userSession()
            val path = uploadFile()
            val paperId = call.parameters["paperId"]?.toInt() ?: throw NumberFormatException()
            paperSubmissionController.uploadFullPaper(path, paperId, user.id)
        }
    }
}