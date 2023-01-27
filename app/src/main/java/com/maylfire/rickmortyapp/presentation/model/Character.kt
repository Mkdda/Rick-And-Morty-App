package com.maylfire.rickmortyapp.presentation.model

data class Character(
    val id:Int,
    val name: String,
    val urlImage: String,
    val specie: String,
    val gender: String,
    val status: String,
    val originPlanetName: String,
    val originPlanetUrl: String,
    val episodes: List<String>,
    val episodesCount: Int
)
