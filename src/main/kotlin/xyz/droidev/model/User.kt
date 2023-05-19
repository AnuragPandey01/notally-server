package xyz.droidev.model

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

data class User (
    val id: String,
    val name: String,
    val email: String,
    val password: String,
    val createdAt: LocalDateTime,
    val lastSignedIn : LocalDateTime,
    val lastActive: LocalDateTime,
)

data class UserOut (
    val id: String,
    val name: String,
    val email: String,
)

object Users: UUIDTable() {
    val name = varchar("name", 255)
    val email = varchar("email", 255).uniqueIndex()
    val password = varchar("password", 255)
    val createdAt =  datetime("created_at").clientDefault{ LocalDateTime.now() }
    val lastSignedIn = datetime("last_signed_in").clientDefault{ LocalDateTime.now() }
    val lastActive = datetime("last_active").clientDefault{ LocalDateTime.now() }
}

data class UserDTO(
    val name: String,
    val email: String,
    val password: String
)