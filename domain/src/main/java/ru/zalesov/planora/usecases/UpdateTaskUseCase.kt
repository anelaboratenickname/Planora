package ru.zalesov.planora.usecases

import ru.zalesov.planora.models.Task
import ru.zalesov.planora.repositories.TasksRepository

class UpdateTaskUseCase(
    private val repository: TasksRepository
) {

    suspend operator fun invoke(task: Task) {
        repository.updateTask(task)
    }

}