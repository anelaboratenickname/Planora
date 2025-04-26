package ru.zalesov.planora.features.home.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.zalesov.planora.features.home.screens.home.HomeViewModel
import ru.zalesov.planora.features.home.screens.task_edit_sheet.TaskEditSheetViewModel
import ru.zalesov.planora.usecases.AddTagUseCase
import ru.zalesov.planora.usecases.AddTaskUseCase
import ru.zalesov.planora.usecases.EditSubtaskCompletionUseCase
import ru.zalesov.planora.usecases.EditTaskCompletionUseCase
import ru.zalesov.planora.usecases.GetAllNotesUseCase
import ru.zalesov.planora.usecases.GetAllTagsUseCase
import ru.zalesov.planora.usecases.GetAllTasksUseCase
import ru.zalesov.planora.usecases.RemoveSubtaskUseCase

val HomeModule = module {
    factory<GetAllTasksUseCase> { GetAllTasksUseCase(get()) }
    factory<GetAllNotesUseCase> { GetAllNotesUseCase(get()) }
    factory<AddTaskUseCase> { AddTaskUseCase(get()) }
    factory<EditTaskCompletionUseCase> { EditTaskCompletionUseCase(get()) }
    factory<EditSubtaskCompletionUseCase> { EditSubtaskCompletionUseCase(get()) }
    factory<RemoveSubtaskUseCase> { RemoveSubtaskUseCase(get()) }
    factory<GetAllTagsUseCase> { GetAllTagsUseCase(get()) }
    factory<AddTagUseCase> { AddTagUseCase(get()) }
    viewModel<HomeViewModel> { HomeViewModel(get(), get(), get(), get()) }
    viewModel<TaskEditSheetViewModel> { (onTaskSave: () -> Unit) ->
        TaskEditSheetViewModel(
            get(),
            get(),
            get(),
            get(),
            onTaskSave
        )
    }
}