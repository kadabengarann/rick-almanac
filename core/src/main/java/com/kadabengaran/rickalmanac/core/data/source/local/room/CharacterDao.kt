package com.kadabengaran.rickalmanac.core.data.source.local.room

import androidx.room.*
import com.kadabengaran.rickalmanac.core.data.source.local.entity.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Query("SELECT * FROM character")
    fun getAllCharacter(): Flow<List<CharacterEntity>>

    @Query("SELECT * FROM character where isFavorite = 1")
    fun getFavoriteCharacter(): Flow<List<CharacterEntity>>

    @Query("SELECT * FROM character where characterId = :id")
    fun getDetailCharacter(id: Int): Flow<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: List<CharacterEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCharacter(character: CharacterEntity)

    @Update
    fun updateFavoriteCharacter(character: CharacterEntity)
}
