package com.maylfire.rickmortyapp.utils

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UiText {

    data class DynamicString(val text: String): UiText()

    data class StringResource(@StringRes val resourceId: Int): UiText()

    @Composable
    fun asTextValue(): String {

        return when(this) {

            is DynamicString -> this.text

            is StringResource -> stringResource(id = this.resourceId)
        }
    }
}