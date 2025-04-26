package ru.zalesov.planora.data.di

import org.koin.dsl.module
import ru.zalesov.planora.data.repositories.NotesRepositoryImpl
import ru.zalesov.planora.data.repositories.TagRepositoryImpl
import ru.zalesov.planora.data.repositories.TasksRepositoryImpl
import ru.zalesov.planora.database.di.DatabaseModule
import ru.zalesov.planora.repositories.NotesRepository
import ru.zalesov.planora.repositories.TagRepository
import ru.zalesov.planora.repositories.TasksRepository

val DataModule = module {
    includes(DatabaseModule)
    single<TasksRepository> { TasksRepositoryImpl(get(), get()) }
    single<NotesRepository> { NotesRepositoryImpl(get(), get()) }
    single<TagRepository> { TagRepositoryImpl(get()) }
}