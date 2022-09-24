package com.kadabengaran.rickalmanac.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

class CharacterResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("status")
    val status: String,


    @field:SerializedName("gender")
    val gender: String,

    @field:SerializedName("species")
    val species: String,

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("location")
    val location: LocationResponse,

    @field:SerializedName("origin")
    val origin: OriginResponse
)
