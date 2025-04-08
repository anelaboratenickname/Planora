package ru.zalesov.planora.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class TaskEntity(
    @PrimaryKey val taskEntityId: String,
    val title: String,
    val description: String,
    val priority: TaskEntityPriority
)

enum class TaskEntityPriority {
    NONE, LOW, MEDIUM, HIGH
}
