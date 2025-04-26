package ru.zalesov.planora.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.zalesov.planora.core.common.TaskPriority

@Entity(tableName = "task")
data class TaskEntity(
    @PrimaryKey val taskEntityId: String,
    val title: String,
    val description: String,
    val priority: TaskPriority,
    val isCompleted: Boolean
)