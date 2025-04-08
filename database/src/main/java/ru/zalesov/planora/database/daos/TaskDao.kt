package ru.zalesov.planora.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.zalesov.planora.database.entities.SubtaskEntity
import ru.zalesov.planora.database.entities.TaskDetailed
import ru.zalesov.planora.database.entities.TaskEntity
import ru.zalesov.planora.database.entities.TaskNoteCrossRef

@Dao
interface TaskDao {

    @Upsert
    suspend fun insertTask(taskEntity: TaskEntity)

    @Upsert
    suspend fun insertSubtasks(subtaskEntities: List<SubtaskEntity>)

    @Upsert
    suspend fun insertTaskNoteCrossRefs(crossRefs: List<TaskNoteCrossRef>)

    @Transaction
    @Query("SELECT * FROM task")
    fun getAllTasks(): Flow<List<TaskDetailed>>

    @Delete
    suspend fun deleteTask(taskEntity: TaskEntity)

    @Query("DELETE FROM subtask WHERE parentTaskId = :taskEntityId")
    suspend fun deleteAllSubtasks(taskEntityId: String)

    @Query("DELETE FROM task_note WHERE taskEntityId = :taskEntityId")
    suspend fun deleteTaskNoteCrossRefs(taskEntityId: String)

}