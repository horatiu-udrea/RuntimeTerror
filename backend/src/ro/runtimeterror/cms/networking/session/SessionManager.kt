package ro.runtimeterror.cms.networking.session

import io.ktor.application.ApplicationCall
import io.ktor.util.pipeline.PipelineContext
import ro.runtimeterror.cms.networking.UserSession

interface SessionManager
{
    fun getUserSession(context: PipelineContext<*, ApplicationCall>): UserSession?

    fun clearUserSession(context: PipelineContext<*, ApplicationCall>)

    fun setUserSession(context: PipelineContext<*, ApplicationCall>, userSession: UserSession)
}