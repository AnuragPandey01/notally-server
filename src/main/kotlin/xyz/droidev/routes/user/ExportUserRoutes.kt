package xyz.droidev.routes.user

import com.auth0.jwt.interfaces.Payload
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import xyz.droidev.security.token.TokenConfig
import xyz.droidev.security.token.TokenService

fun Route.userRoutes(
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