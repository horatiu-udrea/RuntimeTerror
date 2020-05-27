package ro.runtimeterror.cms.networking.session

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.sessions.clear
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.util.pipeline.PipelineContext
import ro.runtimeterror.cms.networking.UserSession

class ServerSessionManager : SessionManager
{
    var userSession: UserSession? = null

    override fun getUserSession(context: PipelineContext<*, ApplicationCall>): UserSession?
    {
        return userSession
    }

    override fun clearUserSession(context: PipelineContext<*, ApplicationCall>)
    {
        userSession = null
    }

    override fun setUserSession(context: PipelineContext<*, ApplicationCall>, userSession: UserSession) {
        this.userSession = userSession
    }

}