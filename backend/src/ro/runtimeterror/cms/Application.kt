package ro.runtimeterror.cms

import ro.runtimeterror.cms.controller.AuthenticationController
import ro.runtimeterror.cms.controller.ConferenceController
import ro.runtimeterror.cms.controller.PaperSubmissionController

fun main(args: Array<String>)
{
    io.ktor.server.netty.EngineMain.main(args)
}

object Components
{
    val authenticationController: AuthenticationController = AuthenticationController()
    val conferenceController: ConferenceController = ConferenceController()
    val paperSubmissionController: PaperSubmissionController = PaperSubmissionController()
}