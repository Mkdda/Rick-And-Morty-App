package com.maylfire.rickmortyapp.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.lang.NumberFormatException

class StringExtensionsTest {

    @Test
    fun findNumberNonNullTest() {

        val expectedResult = 2

        val page = "https://rickandmortyapi.com/api/character/?page=$expectedResult"

        val result: Int? = page.findNumber

        assertThat(result).isNotNull()
        assertThat(result).isEqualTo(expectedResult)
    }

    @Test(expected = NumberFormatException::class)
    fun findNumberExceptionTest() {

        val page = "https://rickandmortyapi.com/api/character/?"

        val result: Int? = page.findNumber

        assertThat(result).isNull()
    }

    @Test
    fun findNumberNullTest() {

        val result: Int? = null.findNumber

        assertThat(result).isNull()
    }
}