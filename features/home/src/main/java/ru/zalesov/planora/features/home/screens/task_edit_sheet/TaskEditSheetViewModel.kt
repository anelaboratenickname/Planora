package ru.zalesov.planora.features.home.screens.task_edit_sheet

import android.util.Log
import kotlinx.coroutines.flow.update
import ru.zalesov.planora.features.home.HomeScreenAction
import ru.zalesov.planora.features.home.TagSelectDialogState
import ru.zalesov.planora.models.Tag
import ru.zalesov.planora.ui.base.BaseViewModel
import ru.zalesov.planora.usecases.AddTagUseCase
import ru.zalesov.planora.usecases.AddTaskUseCase
import ru.zalesov.planora.usecases.GetAllTagsUseCase
import ru.zalesov.planora.usecases.RemoveSubtaskUseCase

class TaskEditSheetViewModel(
    private val addTaskUseCase: AddTaskUseCase,
    private val removeSubtaskUseCase: RemoveSubtaskUseCase,
    private val getAllTagsUseCase: GetAllTagsUseCase,
    private val addTagUseCase: AddTagUseCase,
    private val onTaskSave: () -> Unit,
) : BaseViewModel<TaskEditSheetState, HomeScreenAction, TaskEditSheetEvent>(TaskEditSheetState()) {

    private val tempSelectedTags = mutableListOf<Tag>()

    override fun onEvent(event: TaskEditSheetEvent) {
        when (event) {

            is TaskEditSheetEvent.NewStateProvided -> {
                event.task?.let { task ->
                    _state.update {
                        it.copy(
                            id = task.id,
                            title = task.title,
                            description = task.description,
                            isCompleted = task.isCompleted,
                            priority = task.priority,
                            subtasks = task.subtasks,
                            tags = task.tags,
                            notes = task.notes,
                        )
                    }
                }
            }

            is TaskEditSheetEvent.DescriptionChanged -> {
                _state.update { it.copy(description = event.newDescription) }
            }

            is TaskEditSheetEvent.NotesChanged -> {
                _state.update { it.copy(notes = event.notes) }
            }

            is TaskEditSheetEvent.PriorityChanged -> {
                _state.update { it.copy(priority = event.newPriority) }
            }

            is TaskEditSheetEvent.SubtasksChanged -> {
                _state.update { it.copy(subtasks = event.subtasks) }
            }

            is TaskEditSheetEvent.TagDeleted -> {
                val newTagList = _state.value.tags.toMutableList()
                newTagList.remove(event.tag)
                _state.update { it.copy(tags = newTagList) }
            }

            is TaskEditSheetEvent.TitleChanged -> {
                _state.update { it.copy(title = event.newTitle) }
            }

            is TaskEditSheetEvent.SubtaskTitleChanged -> { id: String, title: String ->
                _state.update {
                    it.copy(
                        subtasks = it.subtasks.map { subtask ->
                            if (subtask.id == id)
                                subtask.copy(title = title)
                            else
                                subtask
                        }
                    )
                }
            }


            is TaskEditSheetEvent.SubtaskDeleteButtonClicked -> {
                deleteSubtask(event.subtaskId)
            }

            TaskEditSheetEvent.SaveButtonClicked -> {
                saveTask()
                onTaskSave()
            }

            TaskEditSheetEvent.TagButtonClicked -> {
                tempSelectedTags.addAll(_state.value.tags)
                getAllTags()
                _state.update {
                    it.copy(
                        isTagSelectDialogOpen = true,
                        tagSelectDialogState = TagSelectDialogState(selectedTags = tempSelectedTags)
                    )
                }
            }

            is TaskEditSheetEvent.TagSelectDialogClosed -> {
                if (event.isChangesSaved) {
                    Log.d("SUKA", "${tempSelectedTags.size}")
                    _state.update {
                        it.copy(
                            tags = tempSelectedTags.toList(),
                            isTagSelectDialogOpen = false,
                            tagSelectDialogState = null
                        )
                    }
                    Log.d("SUKA", "${tempSelectedTags.size}")
                    tempSelectedTags.clear()
                }
            }

            is TaskEditSheetEvent.TagTempAdded -> {
                tempSelectedTags.add(event.tag)
                _state.update {
                    it.copy(
                        tagSelectDialogState = it.tagSelectDialogState?.copy(
                            unselectedTags = it.tagSelectDialogState.unselectedTags.minus(event.tag),
                            selectedTags = tempSelectedTags
                        )
                    )
                }
            }

            is TaskEditSheetEvent.TagTempDeleted -> {
                tempSelectedTags.remove(event.tag)
                _state.update {
                    it.copy(
                        tagSelectDialogState = it.tagSelectDialogState?.copy(
                            unselectedTags = it.tagSelectDialogState.unselectedTags.plus(event.tag),
                            selectedTags = tempSelectedTags
                        ),
                    )
                }
            }

            is TaskEditSheetEvent.NewTagNameChanged -> {
                _state.update {
                    it.copy(
                        tagSelectDialogState = it.tagSelectDialogState?.copy(
                            newTagName = event.name
                        )
                    )
                }
            }

            TaskEditSheetEvent.TadCreationCardClicked -> {
                _state.update { state ->
                    state.copy(
                        tagSelectDialogState = state.tagSelectDialogState?.copy(
                            isTagCreationCardExpanded = !state.tagSelectDialogState.isTagCreationCardExpanded
                        )
                    )
                }
            }

            TaskEditSheetEvent.TagCreated -> {
                val title = _state.value.tagSelectDialogState?.newTagName
                if (!title.isNullOrBlank())
                    safeLaunch(errorAction = HomeScreenAction.Error("Не удалось сохранить метку :(")) {
                        addTagUseCase(title = title)
                        _state.update {
                            it.copy(
                                tagSelectDialogState = it.tagSelectDialogState?.copy(
                                    isTagCreationCardExpanded = false
                                )
                            )
                        }
                    }
            }

        }
    }

    private fun saveTask() {
        if (_state.value.title.isNotBlank())
            safeLaunch(errorAction = HomeScreenAction.Error("Не удалось сохранить задачу :(")) {
                with(_state.value) {
                    addTaskUseCase.invoke(
                        id = id,
                        title = title,
                        description = description,
                        isCompleted = isCompleted,
                        priority = priority,
                        subtasks = subtasks,
                        tags = tags,
                        notes = notes
                    )
                }
            }
        else
            _state.update { it.copy(taskTitleError = true) }
    }

    private fun deleteSubtask(subtaskId: String) {
        safeLaunch(errorAction = HomeScreenAction.Error("Не удалось удалить подзадачу :(")) {
            removeSubtaskUseCase(subtaskId = subtaskId)
        }
    }

    private fun getAllTags() {
        safeLaunch(errorAction = HomeScreenAction.Error("Не удалось отобразить метки :(")) {
            getAllTagsUseCase().collect { newTagList ->
                _state.update {
                    it.copy(
                        tagSelectDialogState = it.tagSelectDialogState?.copy(
                            unselectedTags = newTagList.minus(
                                tempSelectedTags
                            )
                        )
                    )
                }
            }
        }
    }

}