package ru.zalesov.planora.usecases

import ru.zalesov.planora.models.Note
import ru.zalesov.planora.repositories.NotesRepository

class RemoveNoteUseCase(
    private val notesRepository: NotesRepository
) {

    suspend operator fun invoke(note: Note) {
        notesRepository.removeNote(note)
    }

}