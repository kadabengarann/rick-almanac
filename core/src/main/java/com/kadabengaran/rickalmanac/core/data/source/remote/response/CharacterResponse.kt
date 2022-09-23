package com.kadabengaran.rickalmanac.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

class CharacterResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("image")
    val image: String
)
