package com.maylfire.rickmortyapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ErrorModel(@SerializedName("error") val messageError: String)
