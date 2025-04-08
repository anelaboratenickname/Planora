package ru.zalesov.planora.database.entities

import androidx.room.Entity

@Entity(tableName = "task_tag", primaryKeys = ["taskEntityId", "tagEntityId"])
data class TaskTagCrossRef(
    val taskEntityId: String,
    val tagEntityId: String
)
