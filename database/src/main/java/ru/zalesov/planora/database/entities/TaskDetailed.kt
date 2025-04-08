package ru.zalesov.planora.database.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class TaskDetailed(
    @Embedded val taskEntity: TaskEntity,
    @Relation(
        parentColumn = "taskEntityId",
        entityColumn = "parentTaskId"
    )
    val subtasks: List<SubtaskEntity>,
    @Relation(
        parentColumn = "taskEntityId",
        entityColumn = "tagEntityId",
        associateBy = Junction(TaskTagCrossRef::class)
    )
    val tags: List<TagEntity>,
    @Relation(
        parentColumn = "taskEntityId",
        entityColumn = "noteEntityId",
        associateBy = Junction(TaskNoteCrossRef::class)
    )
    val notes: List<NoteEntity>
)
