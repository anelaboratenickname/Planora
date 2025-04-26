package ru.zalesov.planora.features.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.zalesov.planora.models.Subtask
import ru.zalesov.planora.ui.dimensions.Spacing
import ru.zalesov.planora.ui.styles.PlanoraCheckbox
import ru.zalesov.planora.ui.themes.theme.PlanoraTheme

@Composable
fun SubtaskItem(
    subtask: Subtask,
    modifier: Modifier = Modifier,
    onCheckboxClick: (id: String, isChecked: Boolean) -> Unit,
) {
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = subtask.isCompleted,
            onCheckedChange = { onCheckboxClick(subtask.id, it) },
            colors = PlanoraCheckbox.default.colors()
        )
        Text(
            text = subtask.title,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(start = Spacing.default.medium),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview
@Composable
fun SubtaskItemPreview() {
    PlanoraTheme {
        SubtaskItem(
            subtask = Subtask("sub_2", "Создать дизайн", isCompleted = true),
            onCheckboxClick = { _, _ -> }
        )
    }
}