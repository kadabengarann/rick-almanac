package com.kadabengaran.rickalmanac.core.data.source.remote.network

import com.kadabengaran.rickalmanac.core.data.source.remote.response.CharacterResponse
import com.kadabengaran.rickalmanac.core.data.source.remote.response.ListCharacterResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("character")
    suspend fun getList(): ListCharacterResponse

    @GET("character/{id}")
    suspend fun getCharacterDetail(
        @Path("id") id: Int
    ): CharacterResponse

    @GET("character")
    suspend fun getSearchList(
        @Query("name") query: String
    ): ListCharacterResponse
}