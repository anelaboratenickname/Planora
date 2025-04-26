package ru.zalesov.planora.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.zalesov.planora.repositories.TasksRepository

class EditTaskCompletionUseCase(
    private val tasksRepository: TasksRepository
) {

    suspend operator fun invoke(taskId: String, isCompleted: Boolean) {
        withContext(Dispatchers.IO) {
            tasksRepository.editTaskCompletion(taskId = taskId, isCompleted = isCompleted)
        }
    }

}