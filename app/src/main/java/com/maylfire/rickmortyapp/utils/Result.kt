package com.maylfire.rickmortyapp.utils

import com.google.gson.Gson
import com.maylfire.rickmortyapp.R
import com.maylfire.rickmortyapp.data.remote.dto.ErrorModel
import retrofit2.Response

sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()

    data class ErrorResponse(val message: UiText) : Result<Nothing>()

    object InvalidData: Result<Nothing>()
}

fun <T>handleResultResponse(response: Response<T>): Result<T> {

    val body:T? = response.body()

    return when {

        response.isSuccessful && body != null -> Result.Success(body)

        response.isSuccessful && body == null -> Result.InvalidData

        else -> {

            try {

                val error: ErrorModel = Gson()
                    .fromJson(
                        response.errorBody()!!.charStream(), ErrorModel::class.java
                    )

                val message = UiText.DynamicString(error.messageError)

                Result.ErrorResponse(message)
            }catch (ex: Exception) {

                val message = UiText.StringResource(R.string.unknown_error_message)

                Result.ErrorResponse(message)
            }
        }
    }
}