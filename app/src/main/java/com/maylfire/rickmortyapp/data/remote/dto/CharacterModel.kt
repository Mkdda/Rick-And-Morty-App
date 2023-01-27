package com.maylfire.rickmortyapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.maylfire.rickmortyapp.presentation.model.Character

data class CharacterModel(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("status") val status: String,
    @SerializedName("species") val species: String,
    @SerializedName("type") val type: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("origin") val origin: OriginModel,
    @SerializedName("location") val location: Any,
    @SerializedName("image") val image: String,
    @SerializedName("episode") val episode: List<String>,
    @SerializedName("url") val url: String,
    @SerializedName("created") val createdDate: String
) {

    class Builder {

        private var id: Int = 0
        private var name: String = ""
        private var status: String = ""
        private var species: String = ""
        private var type: String = ""
        private var gender: String = ""
        private var origin = OriginModel()
        private var location = LocationReferenceModel()
        private var image: String = ""
        private var episode: List<String> = emptyList()
        private var url: String = ""
        private var createdDate: String = ""

        fun id(id: Int) = this.apply { this.id = id }
        fun name(name: String) = this.apply { this.name = name }
        fun status(status: String) = this.apply { this.status = status }
        fun species(species: String) = this.apply { this.species = species }
        fun type(type: String) = this.apply { this.type = type }
        fun gender(gender: String) = this.apply { this.gender = gender }
        fun origin(origin: OriginModel) = this.apply { this.origin = origin }
        fun location(location: LocationReferenceModel) = this.apply { this.location = location }
        fun image(image: String) = this.apply { this.image = image }
        fun episode(episode: List<String>) = this.apply { this.episode = episode }
        fun url(url: String) = this.apply { this.url = url }
        fun createdDate(createdDate: String) = this.apply { this.createdDate = createdDate }

        fun build() = CharacterModel(
            id = this.id,
            name = this.name,
            status = this.status,
            species = this.species,
            type = this.type,
            gender = this.gender,
            origin = this.origin,
            location = this.location,
            image = this.image,
            episode = this.episode,
            url = this.url,
            createdDate = this.createdDate
        )
    }
}

val CharacterModel.toCharacter: Character
    get() = Character(
        id = this.id,
        name = this.name,
        urlImage = this.image,
        specie = this.species,
        gender = this.gender,
        status = this.status,
        originPlanetName = this.origin.name,
        originPlanetUrl = this.origin.url,
        episodes = this.episode,
        episodesCount = this.episode.size
    )