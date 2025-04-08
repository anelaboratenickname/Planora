package ru.zalesov.planora.models

data class Task(
    val id: String = "",
    val title: String,
    val description: String = "",
    val priority: TaskPriority = TaskPriority.NONE,
    val subtasks: List<Subtask> = emptyList(),
    val tags: List<Tag> = emptyList(),
    val notes: List<Note> = emptyList()
)

enum class TaskPriority {
    NONE, LOW, MEDIUM, HIGH
}