package xyz.droidev.plugins

import com.google.gson.JsonObject
import com.google.gson.JsonSerializer
import io.ktor.serialization.gson.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.application.*
import java.time.LocalDateTime

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
            registerTypeAdapter(LocalDateTime::class.java, object: JsonSerializer<LocalDateTime> {
                override fun serialize(src: LocalDateTime, typeOfSrc: java.lang.reflect.Type, context: com.google.gson.JsonSerializationContext): com.google.gson.JsonElement {
                    return JsonObject().apply {
                        addProperty("year", src.year)
                        addProperty("month", src.monthValue)
                        addProperty("day", src.dayOfMonth)
                        addProperty("hour", src.hour)
                        addProperty("minute", src.minute)
                        addProperty("second", src.second)
                    }
                }
            })
        }
    }

}
