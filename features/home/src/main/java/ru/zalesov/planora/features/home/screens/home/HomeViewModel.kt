package ru.zalesov.planora.features.home.screens.home

import kotlinx.coroutines.flow.update
import ru.zalesov.planora.features.home.HomeScreenAction
import ru.zalesov.planora.models.Task
import ru.zalesov.planora.ui.base.BaseViewModel
import ru.zalesov.planora.usecases.EditSubtaskCompletionUseCase
import ru.zalesov.planora.usecases.EditTaskCompletionUseCase
import ru.zalesov.planora.usecases.GetAllNotesUseCase
import ru.zalesov.planora.usecases.GetAllTasksUseCase

class HomeViewModel(
    private val getAllTasksUseCase: GetAllTasksUseCase,
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val editTaskCompletionUseCase: EditTaskCompletionUseCase,
    private val editSubtaskCompletionUseCase: EditSubtaskCompletionUseCase
) : BaseViewModel<HomeScreenState, HomeScreenAction, HomeScreenEvent>(HomeScreenState()) {

    init {
        getAllTasks()
    }

    override fun onEvent(event: HomeScreenEvent) {
        when (event) {

            HomeScreenEvent.TaskTabSelected -> {
                getAllTasks()
            }

            HomeScreenEvent.NoteTabSelected -> {
                getAllNotes()
            }

            HomeScreenEvent.AddButtonClicked -> {
                _state.update {
                    it.copy(
                        bottomSheetMode = BottomSheetMode.EDIT,
                        editableTask = Task()
                    )
                }
            }

            HomeScreenEvent.BottomSheetClosed -> {
                _state.update {
                    it.copy(
                        bottomSheetMode = BottomSheetMode.NONE,
                        editableTask = null
                    )
                }
            }

            is HomeScreenEvent.EditButtonClicked -> {
                _state.update {
                    it.copy(
                        bottomSheetMode = BottomSheetMode.EDIT,
                        editableTask = event.task
                    )
                }
            }

            is HomeScreenEvent.TaskCheckboxClicked -> {
                editTaskCompletion(taskId = event.taskId, isCompleted = event.isChecked)
            }

            is HomeScreenEvent.SubtaskCheckboxClicked -> {
                editSubtaskCompletion(subtaskId = event.subtaskId, isCompleted = event.isChecked)
            }

            is HomeScreenEvent.SortTypeChanged -> {
                _state.update { it.copy(sortingType = event.sortingType) }
            }

        }
    }

    private fun getAllTasks() {
        safeLaunch(errorAction = HomeScreenAction.Error("Не удалось загрузить задачи :(")) {
            getAllTasksUseCase().collect { newTaskList ->
                _state.update { it.copy(tasks = newTaskList) }
            }
        }
    }

    private fun getAllNotes() {
        safeLaunch(errorAction = HomeScreenAction.Error("Не удалось загрузить заметки :(")) {
            getAllNotesUseCase().collect { newNoteList ->
                _state.update { it.copy(notes = newNoteList) }
            }
        }
    }

    private fun editTaskCompletion(taskId: String, isCompleted: Boolean) {
        safeLaunch(errorAction = HomeScreenAction.Error("Не удалось изменить статус задачи :(")) {
            editTaskCompletionUseCase(taskId = taskId, isCompleted = isCompleted)
        }
    }

    private fun editSubtaskCompletion(subtaskId: String, isCompleted: Boolean) {
        safeLaunch(errorAction = HomeScreenAction.Error("Не удалось изменить статус подзадачи :(")) {
            editSubtaskCompletionUseCase(subtaskId = subtaskId, isCompleted = isCompleted)
        }
    }

}