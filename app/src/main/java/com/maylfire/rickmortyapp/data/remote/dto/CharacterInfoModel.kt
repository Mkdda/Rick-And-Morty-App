package com.maylfire.rickmortyapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.maylfire.rickmortyapp.utils.findNumber

data class CharacterInfoModel(
    @SerializedName("count") private val count: Int,
    @SerializedName("pages") private val pages: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("prev") val prev: String?
) {

    val nextPage: Int? get() = this.next.findNumber

    class Builder {

        private var count: Int = 0
        private var pages: Int = 0
        private var next: String? = null
        private var prev: String? = null

        fun count(count: Int) = this.apply { this.count = count }
        fun pages(pages: Int) = this.apply { this.pages = pages }
        fun next(nextPage: String) = this.apply { this.next = nextPage }
        fun prev(prevPage: String) = this.apply { this.prev = prevPage }

        fun build() = CharacterInfoModel(
            count = this.count,
            pages = pages,
            next = next,
            prev = prev
        )
    }
}