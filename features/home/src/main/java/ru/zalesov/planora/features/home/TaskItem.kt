package ru.zalesov.planora.features.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.zalesov.planora.core.common.TaskPriority
import ru.zalesov.planora.models.Subtask
import ru.zalesov.planora.models.Tag
import ru.zalesov.planora.models.Task
import ru.zalesov.planora.ui.dimensions.Spacing
import ru.zalesov.planora.ui.styles.PlanoraCheckbox
import ru.zalesov.planora.ui.themes.theme.PlanoraTheme

@Composable
fun TaskItem(
    task: Task,
    modifier: Modifier = Modifier,
    onEditClick: (Task) -> Unit,
    onCheckboxClick: (id: String, isChecked: Boolean) -> Unit,
    onSubCheckboxClick: (id: String, isChecked: Boolean) -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = if (task.priority == TaskPriority.HIGH)
                MaterialTheme.colorScheme.inversePrimary
            else
                MaterialTheme.colorScheme.surfaceContainer,
        ),
        shape = RoundedCornerShape(24.dp),
        border = if (task.priority == TaskPriority.HIGH) BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary
        ) else null
    ) {
        var expanded by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .clickable { expanded = !expanded }
                .padding(Spacing.default.medium),
            verticalArrangement = Arrangement.spacedBy(Spacing.default.medium),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = task.isCompleted,
                    onCheckedChange = { onCheckboxClick(task.id, it) },
                    colors = PlanoraCheckbox.default.colors(),
                )
                Text(
                    modifier = Modifier
                        .padding(horizontal = Spacing.default.medium)
                        .weight(1f),
                    text = task.title,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = true,
                    style = MaterialTheme.typography.titleLarge
                )
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "task settings",
                    modifier = Modifier
                        .clickable { onEditClick(task) },
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            if (task.description.isNotEmpty() || task.subtasks.isNotEmpty())
                AnimatedVisibility(expanded) {
                    Column(
                        modifier = modifier.padding(start = Spacing.default.medium),
                    ) {
                        if (task.description.isNotEmpty())
                            Text(
                                text = task.description,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(bottom = Spacing.default.medium),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        if (task.subtasks.isNotEmpty())
                            task.subtasks.forEach {
                                SubtaskItem(
                                    subtask = it,
                                    modifier = Modifier.fillMaxWidth()
                                ) { id, isChecked -> onSubCheckboxClick(id, isChecked) }
                            }
                    }
                }
            if (task.tags.isNotEmpty())
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Spacing.default.small),
                ) {
                    task.tags.forEach {
                        TagItem(tag = it)
                    }
                }
        }
    }
}

@Preview
@Composable
fun TaskItemPreview() {
    PlanoraTheme {
        TaskItem(
            Task(
                id = "task_123",
                title = "Разработать мобильное приложение",
                description = "Создать кроссплатформенное приложение на Kotlin Multiplatform",
                isCompleted = false,
                priority = TaskPriority.HIGH,
                subtasks = listOf(
                    Subtask(
                        id = "sub_1",
                        title = "Проектирование архитектуры",
                        isCompleted = false
                    ),
                    Subtask("sub_2", "Реализация UI", isCompleted = true),
                    Subtask("sub_3", "Интеграция с API", isCompleted = false)
                ),
                tags = listOf(
                    Tag("tag_1", "Разработка"),
                    Tag("tag_2", "Высокий приоритет")
                )
            ),
            onEditClick = {},
            onCheckboxClick = { _, _ -> },
            onSubCheckboxClick = { _, _ -> }
        )
    }
}