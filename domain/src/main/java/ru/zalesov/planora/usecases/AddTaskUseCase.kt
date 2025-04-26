package ru.zalesov.planora.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.zalesov.planora.core.common.TaskPriority
import ru.zalesov.planora.models.Note
import ru.zalesov.planora.models.Subtask
import ru.zalesov.planora.models.Tag
import ru.zalesov.planora.models.Task
import ru.zalesov.planora.repositories.TasksRepository

class AddTaskUseCase(
    private val repository: TasksRepository
) {

    suspend operator fun invoke(
        id: String,
        title: String,
        description: String,
        isCompleted: Boolean,
        priority: TaskPriority,
        subtasks: List<Subtask>,
        tags: List<Tag>,
        notes: List<Note>
    ) {
        withContext(Dispatchers.IO) {
            val task = Task(
                id = id,
                title = title,
                description = description,
                isCompleted = isCompleted,
                priority = priority,
                subtasks = subtasks,
                tags = tags,
                notes = notes,
            )
            repository.addTask(task)
        }
    }

}