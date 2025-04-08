package ru.zalesov.planora.repositories

import kotlinx.coroutines.flow.Flow
import ru.zalesov.planora.models.Note

interface NotesRepository {

    fun getAllNotes(): Flow<List<Note>>

    suspend fun addNote(note: Note)

    suspend fun updateNote(note: Note)

    suspend fun removeNote(note: Note)

}