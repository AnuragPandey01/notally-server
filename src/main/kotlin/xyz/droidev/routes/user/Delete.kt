package xyz.droidev.routes.user

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import xyz.droidev.dao.user.userDao

fun Route.deleteUserRoute() {

    delete {

        try{
            val id = call.principal<JWTPrincipal>()!!.payload.getClaim("user_id").asString()

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