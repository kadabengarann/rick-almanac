package com.kadabengaran.rickalmanac.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

class ListCharacterResponse (

    @field:SerializedName("error")
    val error: String,

    @field:SerializedName("results")
    val results: List<CharacterResponse>
)