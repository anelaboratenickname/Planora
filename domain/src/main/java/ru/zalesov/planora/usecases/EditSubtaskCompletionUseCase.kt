package ru.zalesov.planora.usecases

import ru.zalesov.planora.repositories.TasksRepository

class EditSubtaskCompletionUseCase(
    private val tasksRepository: TasksRepository
) {

    suspend operator fun invoke(subtaskId: String, isCompleted: Boolean) {
        tasksRepository.editSubtaskCompletion(subtaskId = subtaskId, isCompleted = isCompleted)
    }

}