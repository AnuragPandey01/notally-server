package xyz.droidev.routes.user

import io.ktor.server.auth.*
import io.ktor.server.routing.*
import xyz.droidev.security.token.TokenConfig
import xyz.droidev.security.token.TokenService

fun Route.exportUserRoutes(
    tokenConfig: TokenConfig,
    tokenService: TokenService
) {

    route("/user") {
        registerUserRoute(tokenService, tokenConfig)

        authenticate("access-jwt") {


            deleteUserRoute()
            loginUserRoute(tokenService, tokenConfig)
        }
    }

}