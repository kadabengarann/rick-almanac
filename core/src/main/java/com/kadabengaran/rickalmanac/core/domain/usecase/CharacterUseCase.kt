package com.kadabengaran.rickalmanac.core.domain.usecase

import com.kadabengaran.rickalmanac.core.data.Resource
import com.kadabengaran.rickalmanac.core.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterUseCase {
    fun getAllCharacter(): Flow<Resource<List<Character>>>
    fun getFavoriteCharacter(): Flow<List<Character>>
    fun setFavoriteCharacter(character: Character, state: Boolean)
    suspend fun searchCharacter(query: String): Flow<Resource<List<Character>>>
}