package ru.zalesov.planora.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subtask")
data class SubtaskEntity(
    @PrimaryKey val subtaskEntityId: String,
    val parentTaskId: String,
    val title: String
)