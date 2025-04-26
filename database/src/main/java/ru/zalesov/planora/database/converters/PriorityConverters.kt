package ru.zalesov.planora.database.converters

import androidx.room.TypeConverter
import ru.zalesov.planora.core.common.TaskPriority

class PriorityConverters {

    @TypeConverter
    fun toString(priority: TaskPriority): String {
        return priority.name
    }

    @TypeConverter
    fun fromString(priorityName: String): TaskPriority {
        return TaskPriority.valueOf(priorityName)
    }

}