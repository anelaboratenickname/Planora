package ru.zalesov.planora.database.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.zalesov.planora.database.PlanoraDatabase
import ru.zalesov.planora.database.daos.NoteDao
import ru.zalesov.planora.database.daos.TagDao
import ru.zalesov.planora.database.daos.TaskDao

val DatabaseModule = module {
    single<PlanoraDatabase> {
        Room.databaseBuilder(
            context = androidContext(),
            klass = PlanoraDatabase::class.java,
            name = "planora_db"
        ).build()
    }
    single<TaskDao> { get<PlanoraDatabase>().taskDao() }
    single<NoteDao> { get<PlanoraDatabase>().noteDao() }
    single<TagDao> { get<PlanoraDatabase>().tagDao() }
}