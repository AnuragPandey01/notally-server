package xyz.droidev.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import xyz.droidev.dao.user.userDao

val MiddlewarePlugin = createApplicationPlugin("MiddlewarePlugin"){

    onCallRespond{call ->

        val userId = call.principal<JWTPrincipal>()?.payload?.getClaim("user_id")?.asString() ?: return@onCallRespond

        CoroutineScope(Dispatchers.IO).launch {
            userDao.updateLastActive(userId)
        }
    }
}