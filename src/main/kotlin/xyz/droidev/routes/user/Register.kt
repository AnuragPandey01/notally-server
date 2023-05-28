package xyz.droidev.routes.user

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import xyz.droidev.dao.user.userDao
import xyz.droidev.model.UserDTO
import xyz.droidev.model.UserOut
import xyz.droidev.security.hash.SHAHashing
import xyz.droidev.security.token.TokenClaim
import xyz.droidev.security.token.TokenConfig
import xyz.droidev.security.token.TokenService

fun Route.registerUserRoute(
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {
    post("/register"){
        try{
            val userDTO = call.receive<UserDTO>()

            val user =  userDao.addUser(
                userDTO.name,
                userDTO.email,
                SHAHashing.getHashedValue(userDTO.password)
            )!!

            val userOut =  UserOut(
                id = user.id,
                name = user.name,
                email = user.email
            )
            val accessToken = tokenService.generateToken(
                tokenConfig,
                TokenClaim(
                    name = "user_id",
                    value = user.id
                )
            )

            call.respond(
                status = HttpStatusCode.Created,
                mapOf("user" to userOut,"accessToken" to accessToken)
            )
        }
        catch (e: ContentTransformationException){
            call.respond(
                status = HttpStatusCode.BadRequest,
                mapOf("message" to "Invalid data")
            )
        }
        catch (e: ExposedSQLException){

            if(e.sqlState == "23505") {
                call.respond(
                    status = HttpStatusCode.Conflict,
                    mapOf("message" to "Email already exists")
                )
            }else{
                call.respond(
                    status = HttpStatusCode.InternalServerError,
                    mapOf("message" to "Something went wrong")
                )
            }
        }
    }
}