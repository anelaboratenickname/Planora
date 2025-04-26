package ru.zalesov.planora.features.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.zalesov.planora.models.Tag
import ru.zalesov.planora.ui.dimensions.Spacing

@Composable
fun TagItem(
    tag: Tag,
    modifier: Modifier = Modifier,
    showDeleteButton: Boolean = false,
    isSelected: Boolean = false,
    onDeleteButtonClick: (Tag) -> Unit = {}
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.tertiaryContainer
        ),
        shape = RoundedCornerShape(50)
    ) {
        Row(
            modifier = Modifier.padding(Spacing.default.tiny),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = tag.title,
                color = if (isSelected)
                    MaterialTheme.colorScheme.onPrimaryContainer
                else
                    MaterialTheme.colorScheme.onTertiaryContainer,
                style = MaterialTheme.typography.labelSmall
            )
            if (showDeleteButton)
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "delete tag",
                    modifier = Modifier
                        .padding(start = Spacing.default.tiny)
                        .clickable {
                            onDeleteButtonClick(tag)
                        }
                        .size(16.dp),
                    tint = if (isSelected)
                        MaterialTheme.colorScheme.onPrimaryContainer
                    else
                        MaterialTheme.colorScheme.onTertiaryContainer
                )
        }
    }
}