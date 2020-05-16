package ro.runtimeterror.cms

import ro.runtimeterror.cms.controller.*

fun main(args: Array<String>)
{
    io.ktor.server.netty.EngineMain.main(args)
}

object Components
{
    val authenticationController: AuthenticationController = AuthenticationController()
    val userController: UserController = UserController()
    val conferenceController: ConferenceController = ConferenceController()
    val paperSubmissionController: PaperSubmissionController = PaperSubmissionController()
    val paperBidController: PaperBidController = PaperBidController()
}