package ru.zalesov.planora.features.home.screens.task_edit_sheet

import ru.zalesov.planora.core.common.TaskPriority
import ru.zalesov.planora.features.home.TagSelectDialogState
import ru.zalesov.planora.models.Note
import ru.zalesov.planora.models.Subtask
import ru.zalesov.planora.models.Tag

data class TaskEditSheetState(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val isCompleted: Boolean = false,
    val priority: TaskPriority = TaskPriority.NONE,
    val subtasks: List<Subtask> = emptyList(),
    val tags: List<Tag> = emptyList(),
    val notes: List<Note> = emptyList(),
    val taskTitleError: Boolean = false,
    val subtaskTitleError: Boolean = false,
    val isTagSelectDialogOpen: Boolean = false,
    val tagSelectDialogState: TagSelectDialogState? = null
)