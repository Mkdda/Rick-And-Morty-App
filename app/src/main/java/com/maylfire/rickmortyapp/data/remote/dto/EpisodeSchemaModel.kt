package com.maylfire.rickmortyapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.maylfire.rickmortyapp.data.remote.dto.EpisodeModel

data class EpisodeSchemaModel(
    @SerializedName("info") private val info: Any,
    @SerializedName("results") val results: List<EpisodeModel>
)