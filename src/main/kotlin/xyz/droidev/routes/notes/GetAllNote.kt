package xyz.droidev.routes.notes

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import xyz.droidev.dao.notes.notesDao
import xyz.droidev.dao.user.userDao

fun Route.getAllNotesRoute(){

    get{

        try{
            val userId = call.principal<JWTPrincipal>()!!.payload.getClaim("user_id").asString()

            call.respond(
                status = io.ktor.http.HttpStatusCode.OK,
                notesDao.getAllNotes(userId)
            )
        }catch (e: Exception){
            e.printStackTrace()
            call.respond(
                status = io.ktor.http.HttpStatusCode.InternalServerError,
                mapOf("message" to "Something went wrong")
            )
        }
    }

}