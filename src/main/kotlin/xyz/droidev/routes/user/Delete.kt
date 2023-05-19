package xyz.droidev.routes.user

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import xyz.droidev.dao.user.userDao

fun Route.deleteUserRoute() {

    delete("/{id?}"){

        try{
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)


            if(userDao.deleteUser(id)){
                call.respond(
                    status = HttpStatusCode.OK,
                    mapOf("message" to "User deleted successfully")
                )
            }else{
                call.respond(
                    status = HttpStatusCode.NotFound,
                    mapOf("message" to "User not found")
                )
            }
        }
        catch (e: Exception){
            call.respond(
                status = HttpStatusCode.BadRequest,
                mapOf("message" to "Something went wrong")
            )
        }
    }
}