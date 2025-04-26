package ru.zalesov.planora.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import ru.zalesov.planora.core.common.SortingType
import ru.zalesov.planora.ui.R
import ru.zalesov.planora.ui.dimensions.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortingMenu(
    sortTypeValue: String,
    modifier: Modifier = Modifier,
    onSortTypeChange: (SortingType) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Spacing.default.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_sort),
            contentDescription = "sorting",
            tint = MaterialTheme.colorScheme.onBackground,
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it },
        ) {
            Text(
                text = sortTypeValue,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                SortingType.entries.forEach {
                    DropdownMenuItem(
                        text = { Text(it.value) },
                        onClick = {
                            expanded = false
                            onSortTypeChange(it)
                        }
                    )
                }
            }
        }
    }
}