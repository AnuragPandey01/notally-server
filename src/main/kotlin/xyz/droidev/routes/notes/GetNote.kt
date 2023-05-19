package xyz.droidev.routes.notes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import xyz.droidev.dao.notes.notesDao

fun Route.getNoteRoute(){

    get("/{id?}") {

        try{
            val noteId = call.parameters["id"] ?: return@get call.respond(
                status = HttpStatusCode.BadRequest,
                mapOf("message" to "Invalid data")
            )

            val note = notesDao.getNoteById(noteId) ?: return@get call.respond(
                status = HttpStatusCode.NotFound,
                mapOf("message" to "Note not found")
            )

            call.respond(
                status = HttpStatusCode.OK,
                note
            )
        }catch(e: Exception){
            call.respond(
                status = HttpStatusCode.InternalServerError,
                mapOf("message" to "Something went wrong")
            )
        }
    }
}