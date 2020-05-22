package ro.runtimeterror.cms.networking.route

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.put
import io.ktor.routing.route
import ro.runtimeterror.cms.controller.UserController
import ro.runtimeterror.cms.model.UserType
import ro.runtimeterror.cms.networking.authorize
import ro.runtimeterror.cms.networking.dto.UserDTO
import ro.runtimeterror.cms.networking.dto.UserUpdateDTO
import ro.runtimeterror.cms.networking.dto.toUserInformation

fun Routing.userRoute(userController: UserController)
{
    route("/users") {
        get {
            authorize(UserType.ADMIN)
            val userList = userController.userList()
            val userDTOList = userList.map { user -> user.toUserInformation() }
            call.respond(userDTOList)
        }
        put {
            authorize(UserType.ADMIN)
            val userDTO = call.receive<UserUpdateDTO>()
            println(userDTO.userId);
            userController.changeUser(userDTO.userId, UserType.from(userDTO.type), userDTO.validated)
            call.respond(HttpStatusCode.OK)
        }
    }
}