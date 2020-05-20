package ro.runtimeterror.cms

import ro.runtimeterror.cms.controller.*
import ro.runtimeterror.cms.networking.session.CookieSessionManager
import ro.runtimeterror.cms.networking.session.ServerSessionManager
import ro.runtimeterror.cms.networking.session.SessionManager

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
    val paperReviewController: PaperReviewController = PaperReviewController()
    val paperAssignController: PaperAssignController = PaperAssignController()
    val paperDecisionController: PaperDecisionController = PaperDecisionController()
    val sessionManager: SessionManager = if (true) CookieSessionManager() else ServerSessionManager()
    val sectionController: SectionController = SectionController()
}