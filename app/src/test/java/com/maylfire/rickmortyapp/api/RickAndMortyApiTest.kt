package com.maylfire.rickmortyapp.api

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.maylfire.rickmortyapp.data.remote.dto.CharacterSchemaModel
import com.maylfire.rickmortyapp.data.remote.dto.ErrorModel
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import java.net.HttpURLConnection

class RickAndMortyApiTest {

    @get:Rule
    val rule = RickAndMortyApiTestRule()

    // region characters

    @Test
    fun getAllCharacters200Test() = runBlocking {

        val json: String = rule.getJsonStringFromRes(path = "characters/response_200.json")

        val expectedResult = Gson().fromJson(json, CharacterSchemaModel::class.java)

        rule.mockServerResponse(code = HttpURLConnection.HTTP_OK, body = json)

        /*val result: Response<CharacterSchemaModel> = rule.apiClient.getAllCharacters(1)

        assertThat(result.isSuccessful).isTrue()
        assertThat(result.body()).isNotNull()
        assertThat(result.body()).isEqualTo(expectedResult)*/
    }

    @Test
    fun getAllCharacters404Test() = runBlocking {

        val json: String = rule.getJsonStringFromRes(path = "character/response_404.json")

        val expectedResult: String = Gson().fromJson(json, ErrorModel::class.java).messageError

        rule.mockServerResponse(code = HttpURLConnection.HTTP_NOT_FOUND, body = json)

        /*val request: Response<CharacterSchemaModel> = rule.apiClient.getAllCharacters(1)

        val result = Gson().fromJson(request.errorBody()!!.charStream(), ErrorModel::class.java)

        assertThat(request.isSuccessful).isFalse()
        assertThat(request.body()).isNull()
        assertThat(result.messageError).isEqualTo(expectedResult)*/
    }

    @Test
    fun getAllCharacters500Test() = runBlocking {

        rule.mockServerResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, body = "")

        /*val result: Response<CharacterSchemaModel> = rule.apiClient.getAllCharacters(1)

        assertThat(result.isSuccessful).isFalse()
        assertThat(result.body()).isNull()*/
    }

    // endregion

    // region episodes

    // TODO - do another tests

    // endregion
}