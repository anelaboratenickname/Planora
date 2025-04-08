package ru.zalesov.planora.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.zalesov.planora.data.mappers.toNote
import ru.zalesov.planora.data.mappers.toNoteDetailed
import ru.zalesov.planora.database.PlanoraDatabase
import ru.zalesov.planora.database.entities.NoteDetailed
import ru.zalesov.planora.database.entities.NoteTagCrossRef
import ru.zalesov.planora.models.Note
import ru.zalesov.planora.repositories.NotesRepository
import kotlin.collections.map

class NotesRepositoryImpl(
    database: PlanoraDatabase
) : NotesRepository {

    private val noteDao = database.noteDao()
    private val tagDao = database.tagDao()

    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes().map { notes -> notes.map { it.toNote() } }
    }

    override suspend fun addNote(note: Note) {
        val noteDetailed = note.toNoteDetailed()
        val noteTagCrossRefs = buildList {
            noteDetailed.tags.forEach {
                add(
                    NoteTagCrossRef(
                        note.id,
                        it.tagEntityId
                    )
                )
            }
        }
        noteDao.insertNote(noteDetailed.noteEntity)
        tagDao.insertTags(noteDetailed.tags)
        tagDao.insertNoteTagCrossRefs(noteTagCrossRefs)
    }

    override suspend fun updateNote(note: Note) {
        val noteDetailed = note.toNoteDetailed()
        noteDao.insertNote(noteDetailed.noteEntity)
        updateTags(noteDetailed)
    }

    override suspend fun removeNote(note: Note) {
        val noteEntity = note.toNoteDetailed().noteEntity
        noteDao.deleteNote(noteEntity)
        tagDao.deleteNoteTagCrossRefs(noteEntity.noteEntityId)
        noteDao.deleteTaskNoteCrossRefs(noteEntity.noteEntityId)
        deleteUnusedTags()
    }

    private suspend fun updateTags(noteDetailed: NoteDetailed) {
        val noteTagCrossRefs = buildList {
            noteDetailed.tags.forEach {
                add(
                    NoteTagCrossRef(
                        noteDetailed.noteEntity.noteEntityId,
                        it.tagEntityId
                    )
                )
            }
        }
        tagDao.deleteNoteTagCrossRefs(noteDetailed.noteEntity.noteEntityId)
        tagDao.insertTags(noteDetailed.tags)
        tagDao.insertNoteTagCrossRefs(noteTagCrossRefs)
        deleteUnusedTags()
    }

    private suspend fun deleteUnusedTags() = coroutineScope {
        launch(Dispatchers.IO) {
            val allTags = tagDao.getAllTags().firstOrNull()
            if (!allTags.isNullOrEmpty()) {
                val usedTagIds = (tagDao.getAllTaskTagCrossRefs()
                    .map { it.tagEntityId } + tagDao.getAllNoteTagCrossRefs()
                    .map { it.tagEntityId }).toSet()
                val unusedTags = allTags.filterNot { usedTagIds.contains(it.tagEntityId) }
                tagDao.deleteTags(unusedTags)
            }
        }
    }

}