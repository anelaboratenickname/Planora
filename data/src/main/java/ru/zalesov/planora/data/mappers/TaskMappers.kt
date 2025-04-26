package ru.zalesov.planora.data.mappers

import ru.zalesov.planora.data.utils.generateUuid
import ru.zalesov.planora.database.entities.TaskDetailed
import ru.zalesov.planora.database.entities.TaskEntity
import ru.zalesov.planora.models.Task

fun TaskDetailed.toTask(): Task {
    return Task(
        id = taskEntity.taskEntityId,
        title = taskEntity.title,
        description = taskEntity.description,
        isCompleted = taskEntity.isCompleted,
        priority = taskEntity.priority,
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
            isCompleted = isCompleted,
            priority = priority
        ),
        subtasks = subtasks.map { it.toSubtaskEntity(newId) },
        tags = tags.map { it.toTagEntity() },
        notes = notes.map { it.toNoteEntity() }
    )
}