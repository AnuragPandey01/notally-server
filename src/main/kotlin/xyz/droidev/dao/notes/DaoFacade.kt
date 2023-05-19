package xyz.droidev.dao.notes

import xyz.droidev.model.Note
import xyz.droidev.model.NoteDTO

interface DaoFacade {

    suspend fun addNote(note: NoteDTO,userId:String): Note?
    suspend fun editNoteById(id: String, note: NoteDTO): Boolean
    suspend fun deleteNoteById(id: String): Boolean
    suspend fun getNoteById(id: String): Note?
    suspend fun getAllNotes(userId: String): List<Note>
    suspend fun updateLastModified(id: String): Boolean
}