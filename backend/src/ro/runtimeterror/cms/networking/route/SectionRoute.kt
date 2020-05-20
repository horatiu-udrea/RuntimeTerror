package ro.runtimeterror.cms.networking.route

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.*
import ro.runtimeterror.cms.controller.SectionController
import ro.runtimeterror.cms.model.UserType
import ro.runtimeterror.cms.networking.authorize
import ro.runtimeterror.cms.networking.dto.toDTO
import ro.runtimeterror.cms.networking.uploadFile
import ro.runtimeterror.cms.networking.userSession

fun Route.sectionRoute(sectionController: SectionController)
{
    route("/section") {
        get { // Get all sections

        }
        put("/choice") { // Choose section
            data class SectionId(val sectionId: Int)

        }
        put { // Create section

        }
        post { // Choose section chair

        }
        put("/presenter") { // Choose presenter for section

        }
        post("/room") { // Change room name

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
            val path = uploadFile()
            sectionController.uploadPresentation(user.id, path)
            call.respond(HttpStatusCode.OK)
        }
    }
}