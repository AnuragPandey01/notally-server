package xyz.droidev.routes.notes

import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Route.exportNotesRoutes(){

    route("/notes"){
        authenticate("access-jwt") {
            getAllNotesRoute()
            createNoteRoute()
            getNoteRoute()
            deleteNoteRoute()
            updateNoteRoute()
        }
    }
}