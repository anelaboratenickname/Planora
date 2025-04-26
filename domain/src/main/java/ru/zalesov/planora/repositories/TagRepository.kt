package ru.zalesov.planora.repositories

import kotlinx.coroutines.flow.Flow
import ru.zalesov.planora.models.Tag

interface TagRepository {

    fun getAllTags(): Flow<List<Tag>>

    suspend fun addTag(tag: Tag)

    suspend fun updateTag()

    suspend fun removeTag()

}