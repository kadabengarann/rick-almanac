package com.kadabengaran.rickalmanac.core.data.source.local

import com.kadabengaran.rickalmanac.core.data.source.local.entity.CharacterEntity
import com.kadabengaran.rickalmanac.core.data.source.local.room.CharacterDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val characterDao: CharacterDao) {

    fun getAllCharacter(): Flow<List<CharacterEntity>> = characterDao.getAllCharacter()

    fun getFavoriteCharacter(): Flow<List<CharacterEntity>> = characterDao.getFavoriteCharacter()

    suspend fun insertCharacter(characterList: List<CharacterEntity>) = characterDao.insertCharacter(characterList)

    fun setFavoriteCharacter(character: CharacterEntity, newState: Boolean) {
        character.isFavorite = newState
        characterDao.updateFavoriteCharacter(character)
    }
}