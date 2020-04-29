package ro.runtimeterror.cms.networking

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.util.pipeline.PipelineContext
import ro.runtimeterror.cms.exceptions.UnauthorizedException
import ro.runtimeterror.cms.model.AccessLevel

fun PipelineContext<Unit, ApplicationCall>.authorize(vararg levels: AccessLevel)
{
    val user = call.sessions.get<UserSession>() ?: throw UnauthorizedException("User not logged in!")
    val authorized = levels.any { accessLevel ->
        user.level == accessLevel
    }
    if (!authorized)
    {
        throw UnauthorizedException("Elevated privileges required!")
    }
}