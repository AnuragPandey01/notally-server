package xyz.droidev.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import xyz.droidev.routes.notes.notesRoutes
import xyz.droidev.routes.user.userRoutes
import xyz.droidev.security.token.TokenConfig
import xyz.droidev.security.token.TokenService

fun Application.configureRouting(
    tokenConfig: TokenConfig,
    tokenService: TokenService
) {
    routing {
        userRoutes(tokenConfig, tokenService)
        notesRoutes()
    }
}
