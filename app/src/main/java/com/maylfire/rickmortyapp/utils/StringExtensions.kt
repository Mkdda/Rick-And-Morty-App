package com.maylfire.rickmortyapp.utils

val String?.findNumber: Int?
    get() = this?.filter { it.isDigit() }?.toInt()

val List<String>.filterNumbersAsString: String
    get() {

        val result: MutableList<String> = mutableListOf()

        this.forEach {

            result.add("${it.findNumber}")
        }

        return result.toString()
            .replace("[","")
            .replace("]","")
            .filter { !it.isWhitespace() }
    }