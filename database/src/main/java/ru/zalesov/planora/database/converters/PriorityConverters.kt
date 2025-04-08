package ru.zalesov.planora.database.converters

import androidx.room.TypeConverter
import ru.zalesov.planora.database.entities.TaskEntityPriority

class PriorityConverters {

    @TypeConverter
    fun toString(priority: TaskEntityPriority): String {
        return priority.name
    }

    @TypeConverter
    fun fromString(priorityName: String): TaskEntityPriority {
        return TaskEntityPriority.valueOf(priorityName)
    }

}