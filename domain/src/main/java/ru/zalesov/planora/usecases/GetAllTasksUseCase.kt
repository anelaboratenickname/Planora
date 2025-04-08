package ru.zalesov.planora.usecases

import kotlinx.coroutines.flow.Flow
import ru.zalesov.planora.models.Task
import ru.zalesov.planora.repositories.TasksRepository

class GetAllTasksUseCase(
    private val repository: TasksRepository
) {

    operator fun invoke(): Flow<List<Task>> {
        return repository.getAllTasks()
    }
}