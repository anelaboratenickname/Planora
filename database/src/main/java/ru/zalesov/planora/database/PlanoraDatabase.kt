package ru.zalesov.planora.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.zalesov.planora.database.converters.PriorityConverters
import ru.zalesov.planora.database.daos.NoteDao
import ru.zalesov.planora.database.daos.TagDao
import ru.zalesov.planora.database.daos.TaskDao
import ru.zalesov.planora.database.entities.NoteEntity
import ru.zalesov.planora.database.entities.NoteTagCrossRef
import ru.zalesov.planora.database.entities.SubtaskEntity
import ru.zalesov.planora.database.entities.TagEntity
import ru.zalesov.planora.database.entities.TaskEntity
import ru.zalesov.planora.database.entities.TaskNoteCrossRef
import ru.zalesov.planora.database.entities.TaskTagCrossRef

@Database(
    entities = [
        TaskEntity::class,
        NoteEntity::class,
        SubtaskEntity::class,
        TagEntity::class,
        TaskNoteCrossRef::class,
        TaskTagCrossRef::class,
        NoteTagCrossRef::class
    ],
    version = 1
)
@TypeConverters(PriorityConverters::class)
abstract class PlanoraDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun noteDao(): NoteDao
    abstract fun tagDao(): TagDao
}