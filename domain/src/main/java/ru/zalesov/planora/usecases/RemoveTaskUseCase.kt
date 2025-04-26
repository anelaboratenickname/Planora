package ru.zalesov.planora.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.zalesov.planora.models.Task
import ru.zalesov.planora.repositories.TasksRepository

class RemoveTaskUseCase(
    private val repository: TasksRepository
) {

    suspend operator fun invoke(task: Task) {
        withContext(Dispatchers.IO) {
            repository.removeTask(task)
        }
    }

}