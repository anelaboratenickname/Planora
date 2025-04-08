package ru.zalesov.planora.database.entities

import androidx.room.Entity

@Entity(tableName = "task_note", primaryKeys = ["taskEntityId", "noteEntityId"])
data class TaskNoteCrossRef(
    val taskEntityId: String,
    val noteEntityId: String
)
