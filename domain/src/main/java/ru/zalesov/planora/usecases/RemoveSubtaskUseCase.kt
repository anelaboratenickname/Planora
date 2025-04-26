package ru.zalesov.planora.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.zalesov.planora.repositories.TasksRepository

class RemoveSubtaskUseCase(
    private val tasksRepository: TasksRepository
) {

    suspend operator fun invoke(subtaskId: String) {
        withContext(Dispatchers.IO) {
            tasksRepository.removeSubtask(subtaskId = subtaskId)
        }
    }

}