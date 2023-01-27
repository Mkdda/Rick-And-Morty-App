package com.maylfire.rickmortyapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LocationReferenceModel(
    @SerializedName("name") val name: String = "",
    @SerializedName("url") val url: String = ""
)