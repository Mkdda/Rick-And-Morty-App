package com.maylfire.rickmortyapp.utils

sealed class UiEvent<out M> {

    object NoOne: UiEvent<Nothing>()

    object Loading: UiEvent<Nothing>()

    data class Success<out U>(val data: U): UiEvent<U>()

    data class Error(val uiText: UiText): UiEvent<Nothing>()
}