package ro.runtimeterror.cms.networking.session

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.sessions.clear
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.util.pipeline.PipelineContext
import ro.runtimeterror.cms.networking.UserSession

class CookieSessionManager : SessionManager
{
    override fun getUserSession(context: PipelineContext<*, ApplicationCall>): UserSession?
    {
        return context.call.sessions.get<UserSession>()
    }

    override fun clearUserSession(context: PipelineContext<*, ApplicationCall>)
    {
        context.call.sessions.clear<UserSession>()
    }

}