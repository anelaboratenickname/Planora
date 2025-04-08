package ru.zalesov.planora.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tag")
data class TagEntity(
    @PrimaryKey val tagEntityId: String,
    val title: String
)
