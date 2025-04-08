package ru.zalesov.planora.data.mappers

import ru.zalesov.planora.data.utils.generateUuid
import ru.zalesov.planora.database.entities.TagEntity
import ru.zalesov.planora.models.Tag

fun TagEntity.toTag(): Tag {
    return Tag(
        id = tagEntityId,
        title = title
    )
}

fun Tag.toTagEntity(): TagEntity {
    return TagEntity(
        tagEntityId = if (id.isEmpty()) generateUuid() else id,
        title = title
    )
}