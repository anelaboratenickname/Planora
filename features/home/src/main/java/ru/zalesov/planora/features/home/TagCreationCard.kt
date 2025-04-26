package ru.zalesov.planora.features.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.zalesov.planora.ui.R
import ru.zalesov.planora.ui.dimensions.Spacing
import ru.zalesov.planora.ui.themes.theme.PlanoraTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagCreationCard(
    name: String,
    isExpanded: Boolean,
    modifier: Modifier = Modifier,
    onNameChange: (String) -> Unit,
    onCardClick: () -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .clickable { onCardClick() },
        shape = RoundedCornerShape(50),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.tertiary),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.create),
                tint = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier
                    .padding(horizontal = Spacing.default.tiny)
                    .size(16.dp)
            )
            AnimatedVisibility(visible = !isExpanded) {
                Text(
                    text = stringResource(R.string.create),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.padding(end = Spacing.default.small)
                )
            }
            AnimatedVisibility(isExpanded) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .weight(0.6f, true)
                            .background(color = MaterialTheme.colorScheme.background)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            val interactionSource = remember {
                                mutableStateOf(
                                    MutableInteractionSource()
                                )
                            }
                            BasicTextField(
                                value = name,
                                onValueChange = { onNameChange(it) },
                                textStyle = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.tertiary
                                ),
                                decorationBox = @Composable { innerTextField ->
                                    TextFieldDefaults.DecorationBox(
                                        value = name,
                                        visualTransformation = VisualTransformation.None,
                                        innerTextField = innerTextField,
                                        placeholder = {
                                            Text(
                                                text = stringResource(R.string.title),
                                                style = MaterialTheme.typography.bodySmall,
                                            )
                                        },
                                        singleLine = true,
                                        enabled = true,
                                        interactionSource = interactionSource.value,
                                        colors = TextFieldDefaults.colors(
                                            focusedContainerColor = MaterialTheme.colorScheme.background,
                                            unfocusedContainerColor = MaterialTheme.colorScheme.background,
                                            focusedPlaceholderColor = MaterialTheme.colorScheme.tertiary.copy(
                                                alpha = 0.5f
                                            ),
                                            unfocusedPlaceholderColor = MaterialTheme.colorScheme.tertiary.copy(
                                                alpha = 0.5f
                                            ),
                                            focusedIndicatorColor = Color.Transparent,
                                            unfocusedIndicatorColor = Color.Transparent
                                        ),
                                        shape = CutCornerShape(0),
                                        contentPadding = PaddingValues(0.dp),
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = Spacing.default.tiny)
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .weight(0.4f, true)
                            .background(
                                color = MaterialTheme.colorScheme.tertiary,
                                shape = RoundedCornerShape(50)
                            )
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(0.5f)
                                .fillMaxHeight()
                                .clickable { onSaveClick() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = stringResource(R.string.save_new_tag),
                                tint = MaterialTheme.colorScheme.onTertiary,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(16.dp)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .weight(0.5f)
                                .fillMaxHeight()
                                .clickable { onCancelClick() }
                                .background(color = MaterialTheme.colorScheme.tertiaryContainer)) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = stringResource(R.string.cancel_tag_creation),
                                tint = MaterialTheme.colorScheme.onTertiaryContainer,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun TagCreationCardPreview() {
    PlanoraTheme {
        TagCreationCard(
            name = "",
            isExpanded = false,
            onNameChange = {},
            onCardClick = {},
            onSaveClick = {},
            onCancelClick = {}
        )
    }
}