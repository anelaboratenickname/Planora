package ru.zalesov.planora.repositories

import kotlinx.coroutines.flow.Flow
import ru.zalesov.planora.models.Task

interface TasksRepository {

    fun getAllTasks(): Flow<List<Task>>

    suspend fun getTaskById(taskId: String): Task?

    suspend fun addTask(task: Task)

    suspend fun editTaskCompletion(taskId: String, isCompleted: Boolean)

    suspend fun editSubtaskCompletion(subtaskId: String, isCompleted: Boolean)

    suspend fun removeTask(task: Task)

    suspend fun removeSubtask(subtaskId: String)

}