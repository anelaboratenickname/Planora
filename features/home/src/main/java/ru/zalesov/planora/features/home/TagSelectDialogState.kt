package ru.zalesov.planora.features.home

import ru.zalesov.planora.models.Tag

data class TagSelectDialogState(
    val unselectedTags: List<Tag> = emptyList(),
    val selectedTags: List<Tag> = emptyList(),
    val isTagCreationCardExpanded: Boolean = false,
    val newTagName: String = ""
)