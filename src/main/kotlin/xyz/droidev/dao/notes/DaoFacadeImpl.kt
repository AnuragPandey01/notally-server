package xyz.droidev.dao.notes

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import xyz.droidev.dao.DatabaseFactory.dbQuery
import xyz.droidev.model.Note
import xyz.droidev.model.NoteDTO
import xyz.droidev.model.Notes
import java.time.LocalDateTime
import java.util.*

class DaoFacadeImpl : DaoFacade {

    override suspend fun addNote(note: NoteDTO,userId:String): Note? =  dbQuery{
        Notes.insert {
            it[Notes.title] = note.title
            it[Notes.content] = note.content
            it[Notes.pinned] = note.pinned
            it[Notes.userId] = UUID.fromString(userId)
        }.resultedValues?.singleOrNull()?.let { resultRowToNotes(it) }
    }

    override suspend fun editNoteById(id: String, note: NoteDTO): Boolean = dbQuery {
        Notes.update({ Notes.id eq UUID.fromString(id) }) {
            it[Notes.title] = note.title
            it[Notes.content] = note.content
            it[Notes.pinned] = note.pinned
            it[Notes.updatedAt] = LocalDateTime.now()
        } > 0
    }

    override suspend fun deleteNoteById(id: String): Boolean = dbQuery {
        Notes.deleteWhere { Notes.id eq UUID.fromString(id) } > 0
    }

    override suspend fun getNoteById(id: String): Note? = dbQuery {
        Notes.select { Notes.id eq UUID.fromString(id) }.map { resultRowToNotes(it) }.singleOrNull()
    }

    override suspend fun getAllNotes(userId: String): List<Note> = dbQuery {
        Notes.select { Notes.userId eq UUID.fromString(userId) }.mapNotNull { resultRowToNotes(it) }
    }

    override suspend fun updateLastModified(id: String): Boolean = dbQuery {
        Notes.update({ Notes.id eq UUID.fromString(id) }) {
            it[Notes.updatedAt] = LocalDateTime.now()
        } > 0
    }

    private fun resultRowToNotes(it: ResultRow): Note = Note(
        id = it[Notes.id].toString(),
        title = it[Notes.title],
        content = it[Notes.content],
        pinned = it[Notes.pinned],
        createdAt = it[Notes.createdAt],
        updatedAt = it[Notes.updatedAt],
        userId = it[Notes.userId].toString()
    )
}

val notesDao = DaoFacadeImpl()