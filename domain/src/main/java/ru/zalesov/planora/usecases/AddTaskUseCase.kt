package ru.zalesov.planora.usecases

import ru.zalesov.planora.models.Task
import ru.zalesov.planora.repositories.TasksRepository

class AddTaskUseCase(
    private val repository: TasksRepository
) {

    suspend operator fun invoke(task: Task) {
        repository.addTask(task)
    }

}