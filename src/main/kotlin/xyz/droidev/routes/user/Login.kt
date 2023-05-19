package xyz.droidev.routes.user

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import xyz.droidev.dao.user.userDao
import xyz.droidev.model.UserOut
import xyz.droidev.security.token.TokenClaim
import xyz.droidev.security.token.TokenConfig
import xyz.droidev.security.token.TokenService

fun Route.loginUserRoute(
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {

    post("/login"){

        try {
            val credentials = call.receive<LoginCredentials>()

            val user = userDao.getUserByEmail(credentials.email)
                ?: return@post call.respond(HttpStatusCode.NotFound, object {
                    val message = "User not found"
                })

            val userOut = UserOut(
                id = user.id,
                name = user.name,
                email = user.email,
            )

            val accessToken = tokenService.generateToken(
                tokenConfig,
                TokenClaim(
                    name = "user_id",
                    value = user.id
                )
            )
            if (user.password == credentials.password) {
                call.respond(HttpStatusCode.OK, mapOf("user" to userOut, "accessToken" to accessToken))
            } else {
                call.respond(HttpStatusCode.Unauthorized, object {
                    val message = "Invalid credentials"
                })
            }
            userDao.updateLastActive(user.id)
            userDao.updateLastSignedIn(user.id)

        } catch (e:Exception){
            call.respond(HttpStatusCode.InternalServerError, object {
                val message = "something went wrong"
            })
        }

    }
}

data class LoginCredentials(
    val email: String,
    val password: String
)


