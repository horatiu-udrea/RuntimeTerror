package ro.runtimeterror.cms

import ro.runtimeterror.cms.controller.AuthenticationController
import ro.runtimeterror.cms.controller.Controller
import ro.runtimeterror.cms.controller.ConferenceController
import ro.runtimeterror.cms.database.DatabaseRepository
import ro.runtimeterror.cms.repository.Repository

fun main(args: Array<String>): Unit
{
    io.ktor.server.netty.EngineMain.main(args)
}

object Components
{
    private val repository: Repository = DatabaseRepository()
    val controller: Controller = Controller(repository);
    val authenticationController: AuthenticationController = AuthenticationController(repository)
    val conferenceController: ConferenceController = ConferenceController(repository)
}