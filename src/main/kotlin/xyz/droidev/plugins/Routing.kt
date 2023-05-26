package xyz.droidev.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import xyz.droidev.routes.notes.exportNotesRoutes
import xyz.droidev.routes.user.exportUserRoutes
import xyz.droidev.security.token.TokenConfig
import xyz.droidev.security.token.TokenService

fun Application.configureRouting(
    tokenConfig: TokenConfig,
    tokenService: TokenService
) {
    routing {
        route("/api/v1") {
            exportUserRoutes(tokenConfig, tokenService)
            exportNotesRoutes()
        }
    }
}
