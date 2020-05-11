package ro.runtimeterror.cms.networking

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.util.pipeline.PipelineContext
import ro.runtimeterror.cms.exceptions.UnauthorizedException
import ro.runtimeterror.cms.model.UserType

fun PipelineContext<Unit, ApplicationCall>.authorize(level: UserType)
{
    val user = call.sessions.get<UserSession>() ?: throw UnauthorizedException("User not logged in!")
    val authorized = user.type.value >= level.value
    if (!authorized)
    {
        throw UnauthorizedException("Elevated privileges required!")
    }
}

fun PipelineContext<Unit, ApplicationCall>.userSession() =
    call.sessions.get<UserSession>() ?: throw UnauthorizedException("Not logged in!")