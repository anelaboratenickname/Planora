package ru.zalesov.planora.usecases

import kotlinx.coroutines.flow.Flow
import ru.zalesov.planora.models.Note
import ru.zalesov.planora.repositories.NotesRepository

class GetAllNotesUseCase(
    private val notesRepository: NotesRepository
) {

    operator fun invoke(): Flow<List<Note>> {
        return notesRepository.getAllNotes()
    }

}