package ru.zalesov.planora.data.mappers

import ru.zalesov.planora.data.utils.generateUuid
import ru.zalesov.planora.database.entities.TaskDetailed
import ru.zalesov.planora.database.entities.TaskEntity
import ru.zalesov.planora.database.entities.TaskEntityPriority
import ru.zalesov.planora.models.Task
import ru.zalesov.planora.models.TaskPriority

fun TaskDetailed.toTask(): Task {
    return Task(
        id = taskEntity.taskEntityId,
        title = taskEntity.title,
        description = taskEntity.description,
        priority = when (taskEntity.priority) {
            TaskEntityPriority.NONE -> TaskPriority.NONE
            TaskEntityPriority.LOW -> TaskPriority.LOW
            TaskEntityPriority.MEDIUM -> TaskPriority.MEDIUM
            TaskEntityPriority.HIGH -> TaskPriority.HIGH
        },
        subtasks = subtasks.map { it.toSubtask() },
        tags = tags.map { it.toTag() },
        notes = notes.map { it.toNote() }
    )
}

fun Task.toTaskDetailed(): TaskDetailed {
    val newId = if (id.isEmpty()) generateUuid() else id
    return TaskDetailed(
        taskEntity = TaskEntity(
            taskEntityId = newId,
            title = title,
            description = description,
            priority = when (priority) {
                TaskPriority.NONE -> TaskEntityPriority.NONE
                TaskPriority.LOW -> TaskEntityPriority.LOW
                TaskPriority.MEDIUM -> TaskEntityPriority.MEDIUM
                TaskPriority.HIGH -> TaskEntityPriority.HIGH
            }
        ),
        subtasks = subtasks.map { it.toSubtaskEntity(newId) },
        tags = tags.map { it.toTagEntity() },
        notes = notes.map { it.toNoteEntity() }
    )
}