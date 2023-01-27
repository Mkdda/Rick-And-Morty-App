package com.maylfire.rickmortyapp

import com.google.common.truth.Truth.assertThat
import com.maylfire.rickmortyapp.data.remote.dto.LocationModel
import com.maylfire.rickmortyapp.utils.Result
import com.maylfire.rickmortyapp.utils.UiText
import com.maylfire.rickmortyapp.utils.handleResultResponse
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.Response
import java.net.HttpURLConnection

class ResultTest {

    @Test
    fun handleResultResponseSuccessTest() {

        val expectedResult: LocationModel = LocationModel.Builder()
            .build()

        val response: Response<LocationModel> = Response.success(expectedResult)

        val result: Result<LocationModel> = handleResultResponse(response = response)

        assertThat(result).isInstanceOf(Result.Success::class.java)

        result as Result.Success

        assertThat(result.data).isEqualTo(expectedResult)
    }

    @Test
    fun handleResultResponseInvalidDataTest() {

        val response: Response<LocationModel> = Response.success(null)

        val result: Result<LocationModel> = handleResultResponse(response = response)

        assertThat(result).isInstanceOf(Result.InvalidData::class.java)
    }

    @Test
    fun handleResultResponseErrorTest01() {

        val jsonError = "{\"error\": \"There is nothing here.\"}"
        val expectedErrorMessage = "There is nothing here."

        val responseBody: ResponseBody = jsonError.toResponseBody()

        val response: Response<LocationModel> = Response
            .error(HttpURLConnection.HTTP_NOT_FOUND,responseBody)

       val result: Result<LocationModel> = handleResultResponse(response = response)

        assertThat(result).isInstanceOf(Result.ErrorResponse::class.java)

        result as Result.ErrorResponse

        val resultMessage = result.message as UiText.DynamicString

        assertThat(resultMessage.text).isEqualTo(expectedErrorMessage)
    }

    @Test
    fun handleResultResponseErrorTest02() {

        val jsonError = "{\"error\": \"Hey! you must provide an id\"}"
        val expectedErrorMessage = "Hey! you must provide an id"

        val responseBody: ResponseBody = jsonError.toResponseBody()

        val response: Response<LocationModel> = Response
            .error(HttpURLConnection.HTTP_INTERNAL_ERROR,responseBody)

        val result: Result<LocationModel> = handleResultResponse(response = response)

        assertThat(result).isInstanceOf(Result.ErrorResponse::class.java)

        result as Result.ErrorResponse

        val resultMessage = result.message as UiText.DynamicString

        assertThat(resultMessage.text).isEqualTo(expectedErrorMessage)
    }

    @Test
    fun handleResultResponseErrorTest03() {

        val responseBody: ResponseBody = "".toResponseBody()

        val response: Response<LocationModel> = Response.error(499,responseBody)

        val result: Result<LocationModel> = handleResultResponse(response = response)

        assertThat(result).isInstanceOf(Result.ErrorResponse::class.java)

        result as Result.ErrorResponse

        val resultMessage = result.message as UiText.StringResource

        assertThat(resultMessage.resourceId).isEqualTo(R.string.unknown_error_message)
    }
}