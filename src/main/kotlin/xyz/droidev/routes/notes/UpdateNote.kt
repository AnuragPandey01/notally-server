package xyz.droidev.routes.notes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import xyz.droidev.dao.notes.notesDao
import xyz.droidev.model.NoteDTO

fun Route.updateNoteRoute() {

    put("/{id?}") {

        try{
            val noteId = call.parameters["id"] ?: return@put call.respond(
                status = io.ktor.http.HttpStatusCode.BadRequest,
                mapOf("message" to "Invalid data")
            )

            if (
                notesDao.editNoteById(noteId, call.receive<NoteDTO>())
            ) {
                call.respond(
                    status = HttpStatusCode.OK,
                    mapOf("message" to "Note updated successfully")
                )
            } else {
                call.respond(
                    status = HttpStatusCode.NotFound,
                    mapOf("message" to "Note not found")
                )
            }
        }catch(e: ContentTransformationException){
            call.respond(
                status = HttpStatusCode.BadRequest,
                mapOf("message" to "Invalid data")
            )
        }catch (e: Exception){
            call.respond(
                status = HttpStatusCode.InternalServerError,
                mapOf("message" to "Something went wrong")
            )
        }
    }
}