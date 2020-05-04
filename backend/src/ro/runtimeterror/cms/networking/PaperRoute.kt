package ro.runtimeterror.cms.networking

import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import ro.runtimeterror.cms.controller.PaperController
import ro.runtimeterror.cms.exceptions.UnauthorizedException
import ro.runtimeterror.cms.model.AccessLevel
import ro.runtimeterror.cms.model.Paper

data class PaperDTO(
    val field: String,
    val documentPath: String,
    val proposalName: String,
    val keywords: String,
    val topics: String,
    val listOfAuthors: String,
    val accepted: Boolean
)

fun Paper.toDTO(): PaperDTO
{
    return PaperDTO(field, documentPath, proposalName, keywords, topics, listOfAuthors, accepted)
}

fun Route.paperRoute(paperController: PaperController)
{
    route("/papers") {
        get {
            val papers = paperController.getPapers()
            call.respond(papers.map { it.toDTO() })
        }

        post {
            authorize(AccessLevel.AUTHOR)
            val paper = call.receive<PaperDTO>()
            val user = call.sessions.get<UserSession>() ?: throw UnauthorizedException("User not logged in!")
            with(paper)
            {
                paperController.submitProposal(
                    field,
                    proposalName,
                    keywords,
                    topics,
                    listOfAuthors,
                    user.id
                )
            }
        }
        put {
            authorize(AccessLevel.AUTHOR)
            val user = call.sessions.get<UserSession>() ?: throw UnauthorizedException("User not logged in!")
            val path = uploadFile()
            paperController.fullPaperUploaded(path, user.id)
        }
    }
}