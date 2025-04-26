package ru.zalesov.planora.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.zalesov.planora.models.Tag
import ru.zalesov.planora.repositories.TagRepository

class AddTagUseCase(
    private val tagRepository: TagRepository
) {

    suspend operator fun invoke(title: String) {
        withContext(Dispatchers.IO) {
            tagRepository.addTag(
                Tag(title = title)
            )
        }
    }

}