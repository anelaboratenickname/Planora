package ru.zalesov.planora.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class NoteEntity(
    @PrimaryKey val noteEntityId: String,
    val title: String,
    val description: String
)
