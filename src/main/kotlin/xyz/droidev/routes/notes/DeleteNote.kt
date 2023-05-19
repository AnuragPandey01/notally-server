package xyz.droidev.routes.notes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import xyz.droidev.dao.notes.notesDao

fun Route.deleteNoteRoute(){

    delete("/{id?}") {

        val noteId = call.parameters["id"] ?: return@delete call.respond(
            status = HttpStatusCode.BadRequest,
            mapOf("message" to "Invalid data")
        )

        try {
            if (notesDao.deleteNoteById(noteId)) {
                call.respond(
                    status = HttpStatusCode.OK,
                    mapOf("message" to "Note deleted successfully")
                )
            } else {
                call.respond(
                    status = HttpStatusCode.NotFound,
                    mapOf("message" to "Note not found")
                )
            }
        }catch(e: Exception){
            call.respond(
                status = HttpStatusCode.InternalServerError,
                mapOf("message" to "Something went wrong")
            )
        }
    }
}