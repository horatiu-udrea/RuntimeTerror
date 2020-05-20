package ro.runtimeterror.cms.networking.route

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import io.ktor.sessions.sessions
import io.ktor.sessions.set
import ro.runtimeterror.cms.Components
import ro.runtimeterror.cms.controller.AuthenticationController
import ro.runtimeterror.cms.exceptions.UnauthorizedException
import ro.runtimeterror.cms.model.User
import ro.runtimeterror.cms.networking.UserSession
import ro.runtimeterror.cms.networking.dto.UserCredentials
import ro.runtimeterror.cms.networking.dto.UserDTO
import ro.runtimeterror.cms.networking.dto.toUserInformation
import ro.runtimeterror.cms.networking.userSession


fun Routing.authenticationRoute(authenticationController: AuthenticationController)
{
    route("/authentication") {
        post("/login") {
            val data = call.receive<UserCredentials>()
            with(data) {
                val user: User? = authenticationController.authenticate(username, password)
                if (user != null)
                {
                    call.sessions.set(
                        UserSession(
                            user.userId,
                            user.type
                        )
                    )
                    call.respond(HttpStatusCode.OK)
                }
                else
                {
                    throw UnauthorizedException("Invalid credentials!")
                }
            }
        }

        post("/logout") {
            Components.sessionManager.clearUserSession(this)
            call.respond(HttpStatusCode.OK)
        }

        get {
            val session = userSession()
            val user: User = authenticationController.getUser(session.id)
                ?: throw UnauthorizedException("Session error! Please log in again")
            call.respond(user.toUserInformation())
        }

        put {
            val userDTO = call.receive<UserDTO>()
            with(userDTO) {
                authenticationController.newUser(name, username, password, affiliation, email, webPage)
            }
            call.respond(HttpStatusCode.OK)
        }
    }
}