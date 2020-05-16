package ro.runtimeterror.cms.networking.route

import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import ro.runtimeterror.cms.controller.PaperAssignController
import ro.runtimeterror.cms.model.UserType
import ro.runtimeterror.cms.networking.authorize
import ro.runtimeterror.cms.networking.dto.CheckBidResultDTO
import ro.runtimeterror.cms.networking.dto.PaperAssignDTO
import ro.runtimeterror.cms.networking.dto.toDTO
import ro.runtimeterror.cms.networking.dto.toMemberDTO

fun Route.paperAssignRoute(paperAssignController: PaperAssignController)
{
    route("/paper/assign") {
        get {
            authorize(UserType.CO_CHAIR)
            val papers = paperAssignController.getPapers()
            call.respond(papers.toDTO())
        }
        post {
            authorize(UserType.CO_CHAIR)
            val (paperId, userId) = call.receive<CheckBidResultDTO>()
            val bidResult = paperAssignController.getBidResult(paperId, userId)
            call.respond(mapOf("bidResult" to bidResult))
        }
        put {
            authorize(UserType.CO_CHAIR)
            val (paperId, userId) = call.receive<PaperAssignDTO>()
            paperAssignController.assign(paperId, userId)
        }
    }
    get("/member") {
        authorize(UserType.CO_CHAIR)
        val members = paperAssignController.getPCMembers().map { user -> user.toMemberDTO() }
        call.respond(members)
    }
}