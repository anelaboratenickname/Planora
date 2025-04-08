package ru.zalesov.planora.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.zalesov.planora.database.entities.NoteTagCrossRef
import ru.zalesov.planora.database.entities.TagEntity
import ru.zalesov.planora.database.entities.TaskTagCrossRef

@Dao
interface TagDao {

    @Upsert
    suspend fun insertTags(tagEntities: List<TagEntity>)

    @Upsert
    suspend fun insertTaskTagCrossRefs(crossRefs: List<TaskTagCrossRef>)

    @Upsert
    suspend fun insertNoteTagCrossRefs(noteTagCrossRefs: List<NoteTagCrossRef>)

    @Query("SELECT * FROM tag")
    suspend fun getAllTags(): Flow<List<TagEntity>>

    @Query("SELECT * FROM task_tag")
    suspend fun getAllTaskTagCrossRefs(): List<TaskTagCrossRef>

    @Query("SELECT * FROM note_tag")
    suspend fun getAllNoteTagCrossRefs(): List<NoteTagCrossRef>

    @Delete
    suspend fun deleteTags(tagEntities: List<TagEntity>)

    @Query("DELETE FROM task_tag WHERE taskEntityId = :taskEntityId")
    suspend fun deleteTaskTagCrossRefs(taskEntityId: String)

    @Query("DELETE FROM note_tag WHERE noteEntityId = :noteEntityId")
    suspend fun deleteNoteTagCrossRefs(noteEntityId: String)

}