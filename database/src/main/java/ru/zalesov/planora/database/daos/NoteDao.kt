package ru.zalesov.planora.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.zalesov.planora.database.entities.NoteDetailed
import ru.zalesov.planora.database.entities.NoteEntity

@Dao
interface NoteDao {

    @Upsert
    suspend fun insertNote(noteEntity: NoteEntity)

    @Transaction
    @Query("SELECT * FROM note")
    fun getAllNotes(): Flow<List<NoteDetailed>>

    @Delete
    suspend fun deleteNote(noteEntity: NoteEntity)

    @Query("DELETE FROM task_note WHERE noteEntityId = :noteEntityId")
    suspend fun deleteTaskNoteCrossRefs(noteEntityId: String)

}