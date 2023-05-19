package xyz.droidev.routes.notes

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import xyz.droidev.dao.notes.notesDao
import xyz.droidev.model.NoteDTO

fun Route.createNoteRoute(){

    post {
        try{
            val userId = call.principal<JWTPrincipal>()!!.payload.getClaim("user_id").asString()

            val userOut = notesDao.addNote(call.receive<NoteDTO>(), userId) ?: return@post call.respond(
                status = io.ktor.http.HttpStatusCode.InternalServerError,
                mapOf("message" to "Something went wrong")
            )

            call.respond(
                status = io.ktor.http.HttpStatusCode.Created,
                mapOf("note" to userOut)
            )
        }catch (e: Exception){
            call.respond(
                status = io.ktor.http.HttpStatusCode.InternalServerError,
                mapOf("message" to "Something went wrong")
            )
        }
    }
}