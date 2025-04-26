package ru.zalesov.planora.features.home.screens.task_edit_sheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.zalesov.planora.core.common.TaskPriority
import ru.zalesov.planora.features.home.TagItem
import ru.zalesov.planora.features.home.TagSelectDialog
import ru.zalesov.planora.features.home.TagSelectDialogState
import ru.zalesov.planora.models.Tag
import ru.zalesov.planora.models.Task
import ru.zalesov.planora.ui.R
import ru.zalesov.planora.ui.dimensions.Spacing
import ru.zalesov.planora.ui.themes.theme.PlanoraTheme

@Composable
fun TaskEditSheet(
    task: Task?,
    modifier: Modifier = Modifier,
    onClose: () -> Unit,
    viewModel: TaskEditSheetViewModel = koinViewModel(
        parameters = { parametersOf(onClose) }
    )
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(task?.id) {
        viewModel.onEvent(TaskEditSheetEvent.NewStateProvided(task))
    }

    TaskEditSheetContent(
        state = state,
        modifier = modifier,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun TaskEditSheetContent(
    state: TaskEditSheetState,
    modifier: Modifier = Modifier,
    onEvent: (TaskEditSheetEvent) -> Unit
) {
    var priorityExpanded by remember { mutableStateOf(false) }
    val descriptionExpanded by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing.default.small)
    ) {

        item {
            OutlinedTextField(
                value = state.title,
                onValueChange = { onEvent(TaskEditSheetEvent.TitleChanged(newTitle = it)) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.title)) }
            )
        }

        if (descriptionExpanded || state.description.isNotBlank())
            item {
                OutlinedTextField(
                    value = state.description,
                    onValueChange = { onEvent(TaskEditSheetEvent.DescriptionChanged(newDescription = it)) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(stringResource(R.string.description)) }
                )
            }

        item {
            ExposedDropdownMenuBox(
                expanded = priorityExpanded,
                onExpandedChange = { priorityExpanded = it }) {
                OutlinedTextField(
                    value = state.priority.value,
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                    label = { Text(stringResource(R.string.priority)) },
                    readOnly = true,
                )
                ExposedDropdownMenu(
                    expanded = priorityExpanded,
                    onDismissRequest = { priorityExpanded = false }
                ) {
                    TaskPriority.entries.forEach {
                        DropdownMenuItem(
                            text = { Text(it.value) },
                            onClick = {
                                onEvent(
                                    TaskEditSheetEvent.PriorityChanged(
                                        newPriority = it
                                    )
                                )
                                priorityExpanded = false
                                focusManager.clearFocus()
                            }
                        )
                    }
                }
            }
        }

        itemsIndexed(items = state.subtasks) { index, subtask ->
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = Spacing.default.medium),
                value = subtask.title,
                onValueChange = {
                    onEvent(
                        TaskEditSheetEvent.SubtaskTitleChanged(
                            subtaskId = subtask.id,
                            newTitle = it
                        )
                    )
                },
                label = { Text(stringResource(R.string.subtask) + "${index + 1}") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "delete subtask",
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.clickable {
                            onEvent(
                                TaskEditSheetEvent.SubtaskDeleteButtonClicked(
                                    subtaskId = subtask.id
                                )
                            )
                        }
                    )
                }
            )
        }

        if (state.tags.isNotEmpty())
            item {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(Spacing.default.tiny),
                    verticalArrangement = Arrangement.spacedBy(Spacing.default.tiny)
                ) {
                    state.tags.forEach {
                        TagItem(tag = it, showDeleteButton = true, onDeleteButtonClick = { tag ->
                            onEvent(
                                TaskEditSheetEvent.TagDeleted(tag)
                            )
                        })
                    }
                }
            }

//        if (state.notes.isNotEmpty()) {
//            items(items = state.notes) {
//
//            }
//        }

        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Spacing.default.large)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_tag),
                    contentDescription = "add tag",
                    modifier = Modifier.clickable { onEvent(TaskEditSheetEvent.TagButtonClicked) }
                )
                Icon(
                    painter = painterResource(R.drawable.ic_subtask),
                    contentDescription = "add subtask"
                )
                Icon(
                    painter = painterResource(R.drawable.ic_note),
                    contentDescription = "add note"
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = { onEvent(TaskEditSheetEvent.SaveButtonClicked) }) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Save",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }

    }
    if (state.isTagSelectDialogOpen)
        TagSelectDialog(
            state = state.tagSelectDialogState ?: TagSelectDialogState(),
            onSelect = { onEvent(TaskEditSheetEvent.TagTempAdded(it)) },
            onUnselect = { onEvent(TaskEditSheetEvent.TagTempDeleted(it)) },
            onNewTagNameChange = { onEvent(TaskEditSheetEvent.NewTagNameChanged(it)) },
            onTagCreationCardClick = { onEvent(TaskEditSheetEvent.TadCreationCardClicked) },
            onTagCreationCardSave = { onEvent(TaskEditSheetEvent.TagCreated) },
            onTagCreationCardCancel = { },
            onClose = { onEvent(TaskEditSheetEvent.TagSelectDialogClosed(it)) }
        )
}

@Preview(showBackground = true)
@Composable
fun AddSheetPreview() {
    PlanoraTheme {
        TaskEditSheetContent(
            state = TaskEditSheetState(
                tags = listOf(
                    Tag(title = "Работа"),
                    Tag(title = "Учеба"),
                    Tag(title = "Программирование"),
                    Tag(title = "Туда"),
                    Tag(title = "Сюда")
                )
            ), onEvent = {})
    }
}