package ru.zalesov.planora.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.zalesov.planora.core.common.TaskPriority
import ru.zalesov.planora.models.Subtask
import ru.zalesov.planora.models.Tag
import ru.zalesov.planora.models.Task
import ru.zalesov.planora.ui.dimensions.Spacing
import ru.zalesov.planora.ui.themes.theme.PlanoraTheme

@Composable
fun TaskList(
    tasks: List<Task>,
    modifier: Modifier = Modifier,
    onTaskCheckboxClick: (id: String, result: Boolean) -> Unit,
    onSubtaskCheckboxClick: (id: String, result: Boolean) -> Unit,
    onEditClick: (Task) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing.default.medium)
    ) {
        items(items = tasks, key = { it.id }) { task ->
            TaskItem(
                task = task,
                onCheckboxClick = { id, isChecked -> onTaskCheckboxClick(id, isChecked) },
                onSubCheckboxClick = { id, isChecked -> onSubtaskCheckboxClick(id, isChecked) },
                onEditClick = { clickedTask -> onEditClick(clickedTask) }
            )
        }
    }
}

@Preview
@Composable
fun TaskListPreview() {
    PlanoraTheme {
        TaskList(
            listOf(
                Task(
                    id = "task_001",
                    title = "Купить продукты",
                    description = "Молоко, хлеб, яйца",
                    isCompleted = false,
                    priority = TaskPriority.MEDIUM,
                ),
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
                ),
                Task(
                    id = "task_003",
                    title = "Прочитать книгу",
                    description = "'Чистый код' Роберта Мартина",
                    isCompleted = true,
                    tags = listOf(
                        Tag("tag_3", "Саморазвитие"),
                        Tag("tag_4", "Программирование")
                    )
                ),
                Task(
                    id = "task_004",
                    title = "Разобрать старые файлы",
                    description = "Удалить ненужные документы",
                    priority = TaskPriority.LOW,
                    subtasks = listOf(
                        Subtask("sub_4", "Проверить папку 'Загрузки'", isCompleted = false),
                        Subtask("sub_5", "Архивировать важные файлы", isCompleted = false)
                    )
                ),
                Task(
                    id = "task_005",
                    title = "Позвонить клиенту",
                    subtasks = listOf(
                        Subtask("sub_6", "Уточнить детали проекта", isCompleted = false),
                        Subtask("sub_7", "Записать feedback", isCompleted = false)
                    ),
                    tags = listOf(
                        Tag("tag_5", "Важно"),
                        Tag("tag_6", "Клиенты")
                    ),
                    priority = TaskPriority.HIGH
                ),
            ),
            onTaskCheckboxClick = { _, _ -> },
            onSubtaskCheckboxClick = { _, _ -> },
            onEditClick = {}
        )
    }
}