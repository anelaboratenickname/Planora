package ru.zalesov.planora.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.zalesov.planora.data.mappers.toTask
import ru.zalesov.planora.data.mappers.toTaskDetailed
import ru.zalesov.planora.database.PlanoraDatabase
import ru.zalesov.planora.database.entities.TaskDetailed
import ru.zalesov.planora.database.entities.TaskNoteCrossRef
import ru.zalesov.planora.database.entities.TaskTagCrossRef
import ru.zalesov.planora.models.Task
import ru.zalesov.planora.repositories.TasksRepository

class TasksRepositoryImpl(
    database: PlanoraDatabase
) : TasksRepository {

    private val taskDao = database.taskDao()
    private val tagDao = database.tagDao()

    override fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks().map { detailedTasks -> detailedTasks.map { it.toTask() } }
    }

    override suspend fun addTask(task: Task) {
        val taskDetailed = task.toTaskDetailed()
        val taskTagCrossRefs = buildList {
            taskDetailed.tags.forEach {
                add(
                    TaskTagCrossRef(
                        task.id,
                        it.tagEntityId
                    )
                )
            }
        }
        val taskNoteCrossRefs = buildList {
            taskDetailed.notes.forEach {
                add(
                    TaskNoteCrossRef(
                        task.id,
                        it.noteEntityId
                    )
                )
            }
        }
        taskDao.insertTask(taskDetailed.taskEntity)
        taskDao.insertSubtasks(taskDetailed.subtasks)
        tagDao.insertTags(taskDetailed.tags)
        tagDao.insertTaskTagCrossRefs(taskTagCrossRefs)
        taskDao.insertTaskNoteCrossRefs(taskNoteCrossRefs)
    }

    override suspend fun updateTask(task: Task) {
        val taskDetailed = task.toTaskDetailed()
        taskDao.insertTask(taskDetailed.taskEntity)
        updateSubtasks(taskDetailed)
        updateTags(taskDetailed)
        updateTaskNoteCrossRefs(taskDetailed)
    }

    override suspend fun removeTask(task: Task) {
        val taskEntity = task.toTaskDetailed().taskEntity
        taskDao.deleteTask(taskEntity)
        taskDao.deleteAllSubtasks(taskEntity.taskEntityId)
        tagDao.deleteTaskTagCrossRefs(taskEntity.taskEntityId)
        taskDao.deleteTaskNoteCrossRefs(taskEntity.taskEntityId)
        deleteUnusedTags()
    }

    private suspend fun updateSubtasks(taskDetailed: TaskDetailed) {
        taskDao.deleteAllSubtasks(taskDetailed.taskEntity.taskEntityId)
        taskDao.insertSubtasks(taskDetailed.subtasks)
    }

    private suspend fun updateTags(taskDetailed: TaskDetailed) {
        val taskTagCrossRefs = buildList {
            taskDetailed.tags.forEach {
                add(
                    TaskTagCrossRef(
                        taskDetailed.taskEntity.taskEntityId,
                        it.tagEntityId
                    )
                )
            }
        }
        tagDao.deleteTaskTagCrossRefs(taskDetailed.taskEntity.taskEntityId)
        tagDao.insertTags(taskDetailed.tags)
        tagDao.insertTaskTagCrossRefs(taskTagCrossRefs)
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

    private suspend fun updateTaskNoteCrossRefs(taskDetailed: TaskDetailed) {
        val taskNoteCrossRefs = buildList {
            taskDetailed.notes.forEach {
                add(
                    TaskNoteCrossRef(
                        taskDetailed.taskEntity.taskEntityId,
                        it.noteEntityId
                    )
                )
            }
        }
        taskDao.deleteTaskNoteCrossRefs(taskDetailed.taskEntity.taskEntityId)
        taskDao.insertTaskNoteCrossRefs(taskNoteCrossRefs)
    }

}