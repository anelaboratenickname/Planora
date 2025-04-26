package ru.zalesov.planora.features.home

sealed interface HomeScreenAction {
    data object OpenAddBottomSheet: HomeScreenAction
    data class Error(val message: String): HomeScreenAction
}