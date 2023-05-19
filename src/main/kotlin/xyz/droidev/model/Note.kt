package xyz.droidev.model

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

data class Note(
    val id: String,
    val userId: String,
    val title: String,
    val content: String,
    val pinned: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

object Notes: UUIDTable() {

    val userId = uuid("user_id").references(Users.id, onDelete = ReferenceOption.CASCADE, fkName = "fk_user_id")
    val title = varchar("title", 255)
    val content = varchar("content", 255)
    val pinned = bool("pinned")
    val createdAt =  datetime("created_at").clientDefault{ LocalDateTime.now() }
    val updatedAt = datetime("updated_at").clientDefault{ LocalDateTime.now() }

}

data class NoteDTO(
    val title: String,
    val content: String,
    val pinned: Boolean
)

