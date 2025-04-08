package ru.zalesov.planora.models

data class Note(
    val id: String,
    val title: String,
    val description: String = "",
    val tags: List<Tag> = emptyList()
)