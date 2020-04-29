package ro.runtimeterror.cms.networking

import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.sessions.clear
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.sessions.set
import ro.runtimeterror.cms.exceptions.ProgramException
import ro.runtimeterror.cms.exceptions.UnauthorizedException
import ro.runtimeterror.cms.model.AccessLevel
import ro.runtimeterror.cms.model.User

data class UserCredentials(val username: String, val password: String)
data class UserInformation(
    val name: String,
    val username: String,
    val accessLevel: String,
    val affiliation: String,
    val email: String
)
{
    companion object
    {
        fun from(user: User): UserInformation
        {
            with(user) {
                return UserInformation(name, username, accessLevel.toString(), affiliation, email)
            }
        }
    }
}

fun Routing.authenticationRoute(authenticationController: AuthenticationController)
{
    route("/authentication") {
        post("/login") {
            val data = call.receive<UserCredentials>()
            with(data) {
                val user: User? = authenticationController.authenticate(username, password)
                if (user != null)
                {
                    call.sessions.set(UserSession(user.userId, user.username))
                }
                else
                {
                    throw UnauthorizedException("Invalid credentials!")
                }
            }
        }

        post("/logout") {
            call.sessions.clear<UserSession>()
        }

        get {
            val session = call.sessions.get<UserSession>() ?: throw UnauthorizedException("Not logged in!")
            val user: User = authenticationController.getUser(session.id)
                ?: throw UnauthorizedException("Session error! Please log in again")
            call.respond(UserInformation.from(user))
        }
    }
}