package ru.zalesov.planora.repositories

import kotlinx.coroutines.flow.Flow
import ru.zalesov.planora.models.Task

interface TasksRepository {

    fun getAllTasks(): Flow<List<Task>>

    suspend fun addTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun removeTask(task: Task)

}