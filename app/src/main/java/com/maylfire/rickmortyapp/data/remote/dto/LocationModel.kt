package com.maylfire.rickmortyapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LocationModel(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: String,
    @SerializedName("dimension") val dimension: String,
    @SerializedName("residents") val residents: List<String>,
    @SerializedName("url") val url: String,
    @SerializedName("created") val createdDate: String
) {

    class Builder {

        private var id: Int = 0
        private var name: String = ""
        private var type: String = ""
        private var dimension: String = ""
        private var residents: List<String> = emptyList()
        private var url: String = ""
        private var createdDate: String = ""

        fun id(id: Int) = this.apply { this.id = id}
        fun name(name: String) = this.apply { this.name = name }
        fun type(type: String) = this.apply { this.type = type }
        fun dimension(dimension: String) = this.apply { this.dimension = dimension }
        fun residents(residents: List<String>) = this.apply { this.residents = residents }
        fun url(url: String) = this.apply { this.url = url }
        fun createdDate(createdDate: String) = this.apply { this.createdDate = createdDate }

        fun build() = LocationModel(
            id = this.id,
            name = this.name,
            type = this.type,
            dimension = this.dimension,
            residents = this.residents,
            url = this.url,
            createdDate = this.createdDate
        )
    }
}