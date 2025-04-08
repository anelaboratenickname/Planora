package ru.zalesov.planora.database.entities

import androidx.room.Entity

@Entity(tableName = "note_tag", primaryKeys = ["noteEntityId", "tagEntityId"])
data class NoteTagCrossRef(
    val noteEntityId: String,
    val tagEntityId: String
)
