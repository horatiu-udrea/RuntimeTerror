package ro.runtimeterror.cms

import ro.runtimeterror.cms.controller.Controller
import ro.runtimeterror.cms.database.DatabaseRepository
import ro.runtimeterror.cms.repository.Repository

fun main(args: Array<String>): Unit
{
    io.ktor.server.netty.EngineMain.main(args)
}

object Components
{
    val repository: Repository = DatabaseRepository()
    val controller = Controller(repository);
}