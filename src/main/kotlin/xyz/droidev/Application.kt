package xyz.droidev

import io.ktor.server.application.*
import xyz.droidev.dao.DatabaseFactory
import xyz.droidev.plugins.*
import xyz.droidev.security.token.JWTTokenService
import xyz.droidev.security.token.TokenConfig

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {

    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.domain").getString(),
        validityInMs = 1000*60*60*24*7,
        secret = environment.config.property("jwt.secret").getString()
    )
    DatabaseFactory.init(
        driverClassName = environment.config.property("db.driverClassName").getString(),
        jdbcUrl = environment.config.property("db.jdbcUrl").getString(),
    )

    configureStatusPages()
    configureSecurity()
    configureCustomPlugin()
    configureSerialization()
    configureRouting(tokenConfig, tokenService = JWTTokenService())
}




