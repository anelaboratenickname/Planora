package ru.zalesov.planora.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ru.zalesov.planora.models.Tag
import ru.zalesov.planora.ui.R
import ru.zalesov.planora.ui.dimensions.Sizes
import ru.zalesov.planora.ui.dimensions.Spacing
import ru.zalesov.planora.ui.themes.theme.PlanoraTheme

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TagSelectDialog(
    state: TagSelectDialogState,
    modifier: Modifier = Modifier,
    onSelect: (Tag) -> Unit,
    onUnselect: (Tag) -> Unit,
    onNewTagNameChange: (String) -> Unit,
    onTagCreationCardClick: () -> Unit,
    onTagCreationCardSave: () -> Unit,
    onTagCreationCardCancel: () -> Unit,
    onClose: (isSave: Boolean) -> Unit
) {
    val gridSize = when (state.unselectedTags.size) {
        in (1..8) -> 24
        in (9..16) -> 52
        else -> 80
    }
    Dialog(onDismissRequest = { onClose(false) }) {
        Box(
            modifier = modifier
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(Spacing.default.medium)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(Spacing.default.small)) {
                OutlinedTextField(
                    shape = RoundedCornerShape(50),
                    value = "",
                    onValueChange = {},
                    placeholder = { Text(text = stringResource(R.string.search_tags)) },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(R.string.search_tags)
                        )
                    }
                )
                Text(
                    text = stringResource(R.string.selected_tags),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(Spacing.default.tiny),
                ) {
                    items(items = state.selectedTags, key = { it.id }) {
                        TagItem(
                            tag = it,
                            showDeleteButton = true,
                            isSelected = true,
                            modifier = Modifier.height(Sizes.default.tag),
                            onDeleteButtonClick = { onUnselect(it) }
                        )
                    }
                }
                Text(
                    text = stringResource(R.string.all_tags),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                TagCreationCard(
                    name = state.newTagName,
                    isExpanded = state.isTagCreationCardExpanded,
                    modifier = Modifier.height(Sizes.default.tag),
                    onNameChange = { onNewTagNameChange(it) },
                    onCardClick = { onTagCreationCardClick() },
                    onSaveClick = { onTagCreationCardSave() },
                    onCancelClick = { onTagCreationCardCancel() }
                )
                LazyHorizontalStaggeredGrid(
                    horizontalItemSpacing = Spacing.default.tiny,
                    verticalArrangement = Arrangement.spacedBy(Spacing.default.tiny),
                    rows = StaggeredGridCells.FixedSize(Sizes.default.tag),
                    modifier = Modifier.heightIn(max = gridSize.dp)
                ) {
                    items(items = state.unselectedTags, key = { it.id }) {
                        TagItem(
                            tag = it,
                            modifier = Modifier.clickable { onSelect(it) }
                        )
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(Spacing.default.medium)) {
                    Button(
                        onClick = { onClose(false) },
                        modifier = Modifier.weight(0.5f),
                        colors = ButtonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            disabledContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    ) { Text(text = stringResource(R.string.cancel)) }
                    Button(
                        onClick = { onClose(true) },
                        modifier = Modifier.weight(0.5f),
                    ) { Text(text = stringResource(R.string.save)) }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TagSelectDialogPreview() {
    PlanoraTheme {
//        TagSelectDialog(
//            allTags = listOf(
//                Tag(title = "🔥 Срочно"),
//                Tag(title = "🔄 Повседневное"),
//                Tag(title = "📅 Запланировано"),
//                Tag(title = "🛠 В работе"),
//                Tag(title = "✅ Выполнено"),
//                Tag(title = "❌ Отменено"),
//                Tag(title = "⏳ Отложено"),
//                Tag(title = "💡 Идея"),
//                Tag(title = "📌 Важно"),
//                Tag(title = "🤔 На подумать"),
//                Tag(title = "📞 Звонок"),
//                Tag(title = "✉️ Письмо"),
//                Tag(title = "🛒 Покупки"),
//                Tag(title = "📊 Отчет"),
//                Tag(title = "📝 Конспект"),
//                Tag(title = "🎯 Цель"),
//                Tag(title = "📖 Чтение"),
//                Tag(title = "🎨 Творчество"),
//                Tag(title = "💻 Программирование"),
//                Tag(title = "🏠 Домашние дела"),
//                Tag(title = "😊 Приятное"),
//                Tag(title = "😩 Сложное"),
//                Tag(title = "🚀 Мотивация"),
//                Tag(title = "🧘‍♂️ Релакс"),
//                Tag(title = "⚡ Энергия"),
//                Tag(title = "🆘 Помощь"),
//                Tag(title = "🎉 Праздник"),
//                Tag(title = "📚 Книга"),
//                Tag(title = "🎧 Подкаст"),
//                Tag(title = "🎵 Музыка"),
//                Tag(title = "🍿 Кино"),
//                Tag(title = "📰 Статья"),
//                Tag(title = "🧠 Мысли"),
//                Tag(title = "💭 Цитата"),
//                Tag(title = "🌍 Путешествия"),
//                Tag(title = "🍳 Рецепт")
//            ),
//            selectedTags = listOf(
//                Tag(title = "Здарова!"),
//                Tag(title = "Здарова!"),
//                Tag(title = "Здарова!"),
//                Tag(title = "Здарова!"),
//                Tag(title = "Здарова!"),
//                Tag(title = "Здарова!"),
//                Tag(title = "Здарова!"),
//                Tag(title = "Здарова!"),
//            ),
//            onClose = {},
//            onSelect = {},
//            onUnselect = {}
//        )
    }
}