package ru.zalesov.planora.usecases

import ru.zalesov.planora.models.Task
import ru.zalesov.planora.repositories.TasksRepository

class GetTaskUseCase(
    private val tasksRepository: TasksRepository
) {

    suspend operator fun invoke(taskId: String): Task? {
        return tasksRepository.getTaskById(taskId)
    }

}