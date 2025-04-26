package ru.zalesov.planora.ui.styles

import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

data class PlanoraCheckbox(
    val colors: @Composable () -> CheckboxColors
) {
    companion object {
        val default = PlanoraCheckbox(
            colors = {
                CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    uncheckedColor = MaterialTheme.colorScheme.primary,
                    checkmarkColor = MaterialTheme.colorScheme.onPrimary,
                    disabledCheckedColor = MaterialTheme.colorScheme.outline,
                    disabledUncheckedColor = MaterialTheme.colorScheme.outline,
                    disabledIndeterminateColor = MaterialTheme.colorScheme.outline
                )
            }
        )
    }
}