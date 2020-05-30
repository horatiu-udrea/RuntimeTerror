package ro.runtimeterror.cms.networking.route

import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import ro.runtimeterror.cms.controller.PaperPresentationController
import ro.runtimeterror.cms.exceptions.ProgramException
import ro.runtimeterror.cms.model.UserType
import ro.runtimeterror.cms.networking.authorize
import ro.runtimeterror.cms.networking.dto.toDTO
import ro.runtimeterror.cms.networking.dto.toUserInformation

fun Route.paperPresentationRoute(paperPresentationController: PaperPresentationController)
{
    route("/paper") {
        get("/accepted") {
            val papers = paperPresentationController.getAcceptedPapers()
            call.respond(papers.toDTO())
        }

        route("/remaining") {
            get { // Get remaining papers
                authorize(UserType.ADMIN)
                val remainingPapers = paperPresentationController.getRemainingPapers()
                call.respond(remainingPapers.map { paper -> paper.toDTO() })
            }
            get("/{paperId}") {  // Get remaining authors for paper
                authorize(UserType.ADMIN)
                val paperId = call.parameters["paperId"]?.toInt() ?: throw ProgramException("Specify the paper id")
                val remainingAuthors = paperPresentationController.getRemainingAuthors(paperId)
                call.respond(remainingAuthors.map { author -> author.toUserInformation() })
            }
        }
    }
}