package ru.zalesov.planora.usecases

import kotlinx.coroutines.flow.Flow
import ru.zalesov.planora.models.Tag
import ru.zalesov.planora.repositories.TagRepository

class GetAllTagsUseCase(
    private val tagRepository: TagRepository
) {

    suspend operator fun invoke(): Flow<List<Tag>> {
        return tagRepository.getAllTags()
    }

}