package com.kadabengaran.rickalmanac.core.domain.repository

import com.kadabengaran.rickalmanac.core.data.Resource
import com.kadabengaran.rickalmanac.core.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface ICharacterRepository {

    fun getAllCharacter(): Flow<Resource<List<Character>>>

    fun getFavoriteCharacter(): Flow<List<Character>>

    fun setFavoriteCharacter(character: Character, state: Boolean)

    fun getDetailCharacter(id: Int): Flow<Resource<Character>>

    suspend fun searchCharacter(query: String): Flow<Resource<List<Character>>>
}
