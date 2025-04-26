package ru.zalesov.planora.features.home.screens.home

import ru.zalesov.planora.core.common.SortingType
import ru.zalesov.planora.models.Note
import ru.zalesov.planora.models.Tag
import ru.zalesov.planora.models.Task

data class HomeScreenState(
    val tasks: List<Task> = emptyList(),
    val notes: List<Note> = emptyList(),
    val tags: List<Tag> = emptyList(),
    val sortingType: SortingType = SortingType.PRIORITY,
    val bottomSheetMode: BottomSheetMode = BottomSheetMode.NONE,
    val editableTask: Task? = null,
    val isSortingMenuOpen: Boolean = false
)

enum class BottomSheetMode {
    NONE, EDIT, FILTER
}