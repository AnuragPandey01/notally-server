package xyz.droidev.plugins

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import xyz.droidev.dao.user.userDao

fun Application.configureSecurity() {

    authentication {
        jwt("access-jwt"){
            realm = this@configureSecurity.environment.config.property("jwt.realm").getString()
            verifier(
                JWT.require(Algorithm.HMAC256(this@configureSecurity.environment.config.property("jwt.secret").getString()))
                    .withIssuer(this@configureSecurity.environment.config.property("jwt.domain").getString())
                    .build()
            )
            validate { credential ->
                if (
                    userDao.getUser(credential.payload.getClaim("user_id").asString()) != null
                ) {
                    JWTPrincipal(credential.payload)
                } else{
                    null
                }
            }
        }

        /*jwt("refresh-jwt"){
            realm = this@configureSecurity.environment.config.property("jwt.realm").getString()
            verifier(
                JWT.require(Algorithm.HMAC256("secret"))
                    .withAudience(jwtAudience)
                    .withClaim("is_refresh_token", true)
                    .withIssuer(this@configureSecurity.environment.config.property("jwt.domain").getString())
                    .build()
            )
            validate{ credential ->
                if (
                    credential.payload.audience.contains(jwtAudience)
                ) {
                    JWTPrincipal(credential.payload)
                } else{
                    null
                }
            }
        }*/
    }
}
