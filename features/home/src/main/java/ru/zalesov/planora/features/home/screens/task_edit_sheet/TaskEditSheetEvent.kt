package ru.zalesov.planora.features.home.screens.task_edit_sheet

import ru.zalesov.planora.core.common.TaskPriority
import ru.zalesov.planora.models.Note
import ru.zalesov.planora.models.Subtask
import ru.zalesov.planora.models.Tag
import ru.zalesov.planora.models.Task

sealed interface TaskEditSheetEvent {

    data class NewStateProvided(
        val task: Task?
    ) : TaskEditSheetEvent

    data class TitleChanged(
        val newTitle: String
    ) : TaskEditSheetEvent

    data class DescriptionChanged(
        val newDescription: String
    ) : TaskEditSheetEvent

    data class PriorityChanged(
        val newPriority: TaskPriority
    ) : TaskEditSheetEvent

    data class SubtasksChanged(
        val subtasks: List<Subtask>
    ) : TaskEditSheetEvent

    data class TagDeleted(
        val tag: Tag
    ) : TaskEditSheetEvent

    data class NotesChanged(
        val notes: List<Note>
    ) : TaskEditSheetEvent

    data class SubtaskTitleChanged(
        val subtaskId: String,
        val newTitle: String
    ) : TaskEditSheetEvent

    data class SubtaskDeleteButtonClicked(
        val subtaskId: String
    ) : TaskEditSheetEvent

    data object SaveButtonClicked : TaskEditSheetEvent

    data object TagButtonClicked : TaskEditSheetEvent

    data class TagSelectDialogClosed(
        val isChangesSaved: Boolean
    ) : TaskEditSheetEvent

    data object TadCreationCardClicked: TaskEditSheetEvent

    data object TagCreated: TaskEditSheetEvent

    data class TagTempAdded(
        val tag: Tag
    ) : TaskEditSheetEvent

    data class TagTempDeleted(
        val tag: Tag
    ) : TaskEditSheetEvent

    data class NewTagNameChanged(
        val name: String
    ): TaskEditSheetEvent

}