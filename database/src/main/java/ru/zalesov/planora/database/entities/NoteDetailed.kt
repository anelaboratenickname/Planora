package ru.zalesov.planora.database.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class NoteDetailed(
    @Embedded val noteEntity: NoteEntity,
    @Relation(
        parentColumn = "noteEntityId",
        entityColumn = "tagEntityId",
        associateBy = Junction(NoteTagCrossRef::class)
    )
    val tags: List<TagEntity>
)
