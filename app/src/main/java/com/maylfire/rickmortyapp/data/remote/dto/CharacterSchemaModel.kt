package com.maylfire.rickmortyapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.maylfire.rickmortyapp.data.remote.dto.CharacterInfoModel
import com.maylfire.rickmortyapp.data.remote.dto.CharacterModel

data class CharacterSchemaModel(
    @SerializedName("info") val info: CharacterInfoModel,
    @SerializedName("results") val results: List<CharacterModel>
)