package ru.zalesov.planora.features.home.screens.home

import ru.zalesov.planora.core.common.SortingType
import ru.zalesov.planora.models.Task

sealed interface HomeScreenEvent {

    data object TaskTabSelected : HomeScreenEvent

    data object NoteTabSelected : HomeScreenEvent

    data object AddButtonClicked : HomeScreenEvent

    data object BottomSheetClosed : HomeScreenEvent

    data class TaskCheckboxClicked(
        val taskId: String,
        val isChecked: Boolean
    ) : HomeScreenEvent

    data class SubtaskCheckboxClicked(
        val subtaskId: String,
        val isChecked: Boolean
    ) : HomeScreenEvent

    data class EditButtonClicked(
        val task: Task
    ) : HomeScreenEvent

    data class SortTypeChanged(
        val sortingType: SortingType
    ): HomeScreenEvent

}