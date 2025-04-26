package ru.zalesov.planora.data.mappers

import ru.zalesov.planora.data.utils.generateUuid
import ru.zalesov.planora.database.entities.SubtaskEntity
import ru.zalesov.planora.models.Subtask

fun SubtaskEntity.toSubtask(): Subtask {
    return Subtask(
        id = subtaskEntityId,
        title = title,
        isCompleted = isCompleted
    )
}

fun Subtask.toSubtaskEntity(parentTaskId: String): SubtaskEntity {
    return SubtaskEntity(
        subtaskEntityId = if (id.isEmpty()) generateUuid() else id,
        parentTaskId = parentTaskId,
        title = title,
        isCompleted = isCompleted
    )
}