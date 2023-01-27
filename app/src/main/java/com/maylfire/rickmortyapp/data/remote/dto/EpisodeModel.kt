package com.maylfire.rickmortyapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class EpisodeModel(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("air_date") val airDate: String,
    @SerializedName("episode") val episode: String,
    @SerializedName("characters") val characters: List<String>,
    @SerializedName("url") val url: String,
    @SerializedName("created") val createdDate: String
) {

    class Builder {

        private var id: Int = 0
        private var name: String = ""
        private var airDate: String = ""
        private var episode: String = ""
        private var characters: List<String> = emptyList()
        private var url: String = ""
        private var createdDate: String = ""

        fun id(id: Int) = this.apply { this.id = id}
        fun name(name: String) = this.apply { this.name = name }
        fun airDate(airDate: String) = this.apply { this.airDate = airDate }
        fun episode(episode: String) = this.apply { this.episode = episode }
        fun characters(characters: List<String>) = this.apply { this.characters = characters }
        fun url(url: String) = this.apply { this.url = url }
        fun createdDate(createdDate: String) = this.apply { this.createdDate = createdDate }

        fun build() = EpisodeModel(
            id = this.id,
            name = this.name,
            airDate = this.airDate,
            episode = this.episode,
            characters = this.characters,
            url = this.url,
            createdDate = this.createdDate
        )
    }
}
