package ro.runtimeterror.cms.networking.route

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import org.jetbrains.exposed.sql.exposedLogger
import org.joda.time.LocalDateTime
import ro.runtimeterror.cms.controller.SectionController
import ro.runtimeterror.cms.model.UserType
import ro.runtimeterror.cms.networking.authorize
import ro.runtimeterror.cms.networking.dto.CreateSectionDTO
import ro.runtimeterror.cms.networking.dto.dateTimeFormatter
import ro.runtimeterror.cms.networking.dto.toDTO
import ro.runtimeterror.cms.networking.uploadFile
import ro.runtimeterror.cms.networking.userSession

data class SectionId(val sectionId: Int)
data class SectionChairChoice(val sectionId: Int, val userId: Int)
data class SectionPresenterChoice(val userId: Int, val paperId: Int, val sectionId: Int)
data class SectionRoomName(val sectionId: Int, val roomName: String)

fun Route.sectionRoute(sectionController: SectionController) {
    route("/section") {

        get { // Get all sections
            val sections = sectionController.getAllSections()
            call.respond(sections.map { section -> section.toDTO() })
        }

        put("/choice") {
            // Choose section
            authorize(UserType.AUTHOR)
            exposedLogger.trace("Deprecated API")
            val user = userSession()
            val sectionIdDTO = call.receive<SectionId>()
            sectionController.userSectionChoice(user.id, sectionIdDTO.sectionId)
            call.respond(HttpStatusCode.OK)
        }

        put { // Create section
            authorize(UserType.ADMIN)
            val (sessionChairId, userId, name, startTime, endTime, roomName, paperId) = call.receive<CreateSectionDTO>()
            sectionController.createSection(
                    sessionChairId,
                    userId,
                    name,
                    LocalDateTime.parse(startTime, dateTimeFormatter),
                    LocalDateTime.parse(endTime, dateTimeFormatter),
                    roomName,
                    paperId
            )
            call.respond(HttpStatusCode.OK)
        }

        post {
            // Choose section chair
            authorize(UserType.ADMIN)
            val (sectionId, userId) = call.receive<SectionChairChoice>()
            sectionController.chooseSectionChair(sectionId, userId)
            call.respond(HttpStatusCode.OK)
        }

        put("/presenter") {
            // Choose presenter for section
            authorize(UserType.ADMIN)
            val (userId, paperId, sectionId) = call.receive<SectionPresenterChoice>()
            sectionController.chooseSectionPresenter(userId, paperId, sectionId)
            call.respond(HttpStatusCode.OK)
        }
        post("/room") {
            // Change room name
            authorize(UserType.ADMIN)
            val (sectionId, roomName) = call.receive<SectionRoomName>()
            sectionController.changeSectionRoom(sectionId, roomName)
            call.respond(HttpStatusCode.OK)
        }

        get("/details") { // Get presentation details
            authorize(UserType.AUTHOR)
            val user = userSession()
            val section = sectionController.getSectionDetails(user.id)
            if (section == null) call.respond(HttpStatusCode.NotFound)
            else call.respond(section.toDTO())
        }
        post("/presentation") { // Upload presentation
            authorize(UserType.AUTHOR)
            val user = userSession()
            val path = uploadFile(user.id)
            sectionController.uploadPresentation(user.id, path)
            call.respond(HttpStatusCode.OK)
        }

        get("review") { // Get reviews for the paper he is going to present
            authorize(UserType.AUTHOR)
            val user = userSession()
            val reviews = sectionController.getReviews(user.id)
            call.respond(reviews.map { review -> review.toDTO() })
        }
    }
}