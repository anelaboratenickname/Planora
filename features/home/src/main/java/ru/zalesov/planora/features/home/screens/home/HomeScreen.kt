package ru.zalesov.planora.features.home.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import ru.zalesov.planora.core.common.TaskPriority
import ru.zalesov.planora.features.home.SortingMenu
import ru.zalesov.planora.features.home.TaskList
import ru.zalesov.planora.features.home.screens.task_edit_sheet.TaskEditSheet
import ru.zalesov.planora.models.Subtask
import ru.zalesov.planora.models.Tag
import ru.zalesov.planora.models.Task
import ru.zalesov.planora.ui.R
import ru.zalesov.planora.ui.dimensions.Spacing
import ru.zalesov.planora.ui.themes.theme.PlanoraTheme

@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    HomeScreen(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeScreenState,
    onEvent: (HomeScreenEvent) -> Unit
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val sheetState = rememberModalBottomSheetState()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    scrolledContainerColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                title = { Text("Planora") },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(
                        HomeScreenEvent.AddButtonClicked
                    )
                },
                containerColor = MaterialTheme.colorScheme.tertiary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add",
                    tint = MaterialTheme.colorScheme.onTertiary
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding), verticalArrangement = Arrangement.spacedBy(
                Spacing.default.medium
            )
        ) {
            TabRow(selectedTabIndex = selectedTabIndex, modifier = Modifier.fillMaxWidth()) {
                Tabs.forEachIndexed { index, name ->
                    Tab(selected = index == selectedTabIndex, onClick = {
                        when (name) {
                            R.string.tasks -> {
                                selectedTabIndex = 0
                                onEvent(HomeScreenEvent.TaskTabSelected)
                            }

                            R.string.notes -> {
                                selectedTabIndex = 1
                                onEvent(HomeScreenEvent.NoteTabSelected)
                            }
                        }
                    }, text = { Text(text = stringResource(name)) })
                }
            }
            Row(modifier = Modifier.padding(horizontal = Spacing.default.medium)) {
                SortingMenu(
                    sortTypeValue = state.sortingType.value,
                    modifier = Modifier.fillMaxWidth(0.5f),
                    onSortTypeChange = { onEvent(HomeScreenEvent.SortTypeChanged(it)) }
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(R.drawable.ic_filter),
                    contentDescription = "filter",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            if (selectedTabIndex == 0)
                TaskList(
                    tasks = state.tasks,
                    modifier = Modifier.padding(horizontal = Spacing.default.medium),
                    onTaskCheckboxClick = { id, completion ->
                        onEvent(
                            HomeScreenEvent.TaskCheckboxClicked(
                                taskId = id,
                                isChecked = completion
                            )
                        )
                    },
                    onSubtaskCheckboxClick = { id, completion ->
                        onEvent(
                            HomeScreenEvent.SubtaskCheckboxClicked(
                                subtaskId = id,
                                isChecked = completion
                            )
                        )
                    },
                    onEditClick = { task ->
                        onEvent(
                            HomeScreenEvent.EditButtonClicked(task = task)
                        )
                    }
                )
        }

        if (state.bottomSheetMode != BottomSheetMode.NONE) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = { onEvent(HomeScreenEvent.BottomSheetClosed) }) {
                when (state.bottomSheetMode) {
                    BottomSheetMode.EDIT -> {
                        TaskEditSheet(
                            task = state.editableTask,
                            modifier = Modifier.padding(Spacing.default.medium),
                            onClose = { onEvent(HomeScreenEvent.BottomSheetClosed) }
                        )
                    }

                    BottomSheetMode.FILTER -> {

                    }

                    else -> return@ModalBottomSheet
                }
            }
        }
    }
}

val Tabs = listOf<Int>(
    R.string.tasks,
    R.string.notes
)

@Preview
@Composable
fun HomeScreenPreview() {
    PlanoraTheme {
        HomeScreen(
            state = HomeScreenState(
                tasks = listOf(
                    Task(
                        id = "task_002",
                        title = "Подготовить презентацию",
                        description = "Слайды для совещания в пятницу",
                        subtasks = listOf(
                            Subtask("sub_1", "Собрать данные", false),
                            Subtask("sub_2", "Создать дизайн", isCompleted = true),
                            Subtask("sub_3", "Проверить орфографию", false)
                        ),
                        priority = TaskPriority.HIGH
                    )
                ),
                tags = listOf(
                    Tag(title = "Работа"),
                    Tag(title = "Работа"),
                    Tag(title = "Работа"),
                    Tag(title = "Работа"),
                    Tag(title = "Работа"),
                    Tag(title = "Работа"),
                )
            )
        ) {}
    }
}