package ru.zalesov.planora.ui.dimensions

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Sizes(
    val tag: Dp
) {
    companion object {
        val default = Sizes(tag = 24.dp)
    }
}
