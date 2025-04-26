package ru.zalesov.planora.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.zalesov.planora.models.Note
import ru.zalesov.planora.repositories.NotesRepository

class UpdateNoteUseCase(
    private val notesRepository: NotesRepository
) {

    suspend operator fun invoke(note: Note) {
        withContext(Dispatchers.IO) {
            notesRepository.updateNote(note)
        }
    }

}