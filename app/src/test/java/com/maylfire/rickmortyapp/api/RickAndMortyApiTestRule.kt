package com.maylfire.rickmortyapp.api

import com.maylfire.rickmortyapp.data.remote.RickAndMortyApi
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class RickAndMortyApiTestRule: TestWatcher() {

    private val mockWebServer: MockWebServer by lazy {

        MockWebServer()
    }

    val apiClient: RickAndMortyApi by lazy {

        this.mockWebServer.start(0)

        val url: String = this.mockWebServer.url("/").toString()

        Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(url)
            .build()
            .create(RickAndMortyApi::class.java)
    }

    override fun starting(description: Description) {
        super.starting(description)
    }

    override fun finished(description: Description) {
        super.finished(description)

        this.mockWebServer.shutdown()
    }

    fun mockServerResponse(code: Int, body: String) {

        val mockResponse = MockResponse().apply {

            this.setResponseCode(code)

            if (body.isNotEmpty()) {

                this.setBody(body)
            }
        }

        this.mockWebServer.enqueue(mockResponse)
    }

    fun getJsonStringFromRes(path:String): String {

        val fullyPath = "/com.maylfire.rickmortyapp/$path"

        val stream: InputStream = this.javaClass.getResourceAsStream(fullyPath)!!

        return stream.readTextStream
    }

    private val InputStream.readTextStream: String
        get() {

            val stringBuilder = StringBuilder()

            val inputStreamReader: InputStreamReader = this.reader(Charsets.UTF_8)

            val bufferedReader = BufferedReader(inputStreamReader)

            var line: String? = bufferedReader.readLine()

            while (line != null) {

                stringBuilder.append(line)

                line = bufferedReader.readLine()
            }

            bufferedReader.close()

            return stringBuilder.toString()
        }
}