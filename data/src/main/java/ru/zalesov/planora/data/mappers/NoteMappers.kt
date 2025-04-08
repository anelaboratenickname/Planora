package ru.zalesov.planora.data.mappers

import ru.zalesov.planora.data.utils.generateUuid
import ru.zalesov.planora.database.entities.NoteDetailed
import ru.zalesov.planora.database.entities.NoteEntity
import ru.zalesov.planora.models.Note

fun NoteDetailed.toNote(): Note {
    return Note(
        id = noteEntity.noteEntityId,
        title = noteEntity.title,
        description = noteEntity.description,
        tags = tags.map { it.toTag() }
    )
}

fun Note.toNoteDetailed(): NoteDetailed {
    return NoteDetailed(
        noteEntity = NoteEntity(
            noteEntityId = if (id.isEmpty()) generateUuid() else id,
            title = title,
            description = description
        ),
        tags = tags.map { it.toTagEntity() }
    )
}

fun NoteEntity.toNote(): Note {
    return Note(
        id = noteEntityId,
        title = title,
        description = description
    )
}

fun Note.toNoteEntity(): NoteEntity {
    return NoteEntity(
        noteEntityId = id,
        title = title,
        description = description
    )
}