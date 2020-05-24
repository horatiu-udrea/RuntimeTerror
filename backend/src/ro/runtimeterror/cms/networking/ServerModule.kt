package ro.runtimeterror.cms.networking

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.routing
import io.ktor.sessions.SessionStorageMemory
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import ro.runtimeterror.cms.Components
import ro.runtimeterror.cms.exceptions.ProgramException
import ro.runtimeterror.cms.exceptions.UnauthorizedException
import ro.runtimeterror.cms.model.UserType
import ro.runtimeterror.cms.networking.route.*

data class UserSession(val id: Int, val type: UserType)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false)
{
    install(CallLogging)
    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.ContentType)
        header(HttpHeaders.AccessControlAllowHeaders)
        header(HttpHeaders.AccessControlAllowOrigin)
        allowCredentials = true
        anyHost()
    }

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    install(StatusPages) {
        exception<UnauthorizedException> { exception ->
            call.respond(HttpStatusCode.Unauthorized, mapOf("error" to (exception.message ?: "")))
        }
        exception<ProgramException> { exception ->
            call.respond(HttpStatusCode.InternalServerError, mapOf("error" to (exception.message ?: "")))
        }
    }

    install(Sessions) {
        cookie<UserSession>("CMS_SESSION", SessionStorageMemory()) {
            cookie.path = "/"
        }
    }

    routing {
        authenticationRoute(Components.authenticationController)
        userRoute(Components.userController)
        conferenceRoute(Components.conferenceController)
        paperSubmissionRoute(Components.paperSubmissionController)
        paperBidRoute(Components.paperBidController)
        paperReviewRoute(Components.paperReviewController)
        paperAssignRoute(Components.paperAssignController)
        paperDecisionRoute(Components.paperDecisionController)
        sectionRoute(Components.sectionController)
        paperPresentationRoute(Components.paperPresentationController)
    }
}

