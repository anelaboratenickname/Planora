package ru.zalesov.planora.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.zalesov.planora.data.mappers.toTag
import ru.zalesov.planora.data.mappers.toTagEntity
import ru.zalesov.planora.database.daos.TagDao
import ru.zalesov.planora.models.Tag
import ru.zalesov.planora.repositories.TagRepository

class TagRepositoryImpl(
    private val tagDao: TagDao
) : TagRepository {

    override fun getAllTags(): Flow<List<Tag>> {
        return tagDao.getAllTags().map { tags -> tags.map { it.toTag() } }
    }

    override suspend fun addTag(tag: Tag) {
        tagDao.insertTag(tag.toTagEntity())
    }

    override suspend fun updateTag() {
        TODO("Not yet implemented")
    }

    override suspend fun removeTag() {
        TODO("Not yet implemented")
    }

}