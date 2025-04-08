package ru.zalesov.planora.data.utils

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun generateUuid(): String {
    return Uuid.random().toString()
}