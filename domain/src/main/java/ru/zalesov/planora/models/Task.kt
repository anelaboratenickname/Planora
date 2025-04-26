package ru.zalesov.planora.models

import ru.zalesov.planora.core.common.TaskPriority

data class Task(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val isCompleted: Boolean = false,
    val priority: TaskPriority = TaskPriority.NONE,
    val subtasks: List<Subtask> = emptyList(),
    val tags: List<Tag> = emptyList(),
    val notes: List<Note> = emptyList()
)