package com.kadabengaran.rickalmanac.core.data

import com.kadabengaran.rickalmanac.core.data.source.local.LocalDataSource
import com.kadabengaran.rickalmanac.core.data.source.local.entity.CharacterEntity
import com.kadabengaran.rickalmanac.core.data.source.remote.RemoteDataSource
import com.kadabengaran.rickalmanac.core.data.source.remote.network.ApiResponse
import com.kadabengaran.rickalmanac.core.data.source.remote.response.CharacterResponse
import com.kadabengaran.rickalmanac.core.domain.model.Character
import com.kadabengaran.rickalmanac.core.domain.repository.ICharacterRepository
import com.kadabengaran.rickalmanac.core.utils.AppExecutors
import com.kadabengaran.rickalmanac.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : ICharacterRepository {

    override fun getAllCharacter(): Flow<Resource<List<Character>>> =
        object : NetworkBoundResource<List<Character>, List<CharacterResponse>>() {
            override fun loadFromDB(): Flow<List<Character>> {
                return localDataSource.getAllCharacter().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Character>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<CharacterResponse>>> =
                remoteDataSource.getAllCharacter()

            override suspend fun saveCallResult(data: List<CharacterResponse>) {
                val characterList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertCharacter(characterList)
            }
        }.asFlow()

    override fun getFavoriteCharacter(): Flow<List<Character>> {
        return localDataSource.getFavoriteCharacter().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteCharacter(character: Character, state: Boolean) {
        val characterEntity = DataMapper.mapDomainToEntity(character)
        appExecutors.diskIO().execute { localDataSource.setFavoriteCharacter(characterEntity, state) }
    }


    override suspend fun searchCharacter(query: String): Flow<Resource<List<Character>>> {
        return remoteDataSource.searchCharacter(query).map {
            when (it) {
                is ApiResponse.Success -> {
                    val data = it.data
                    val characterList = DataMapper.mapResponsesToEntities(data)
                    Resource.Success(DataMapper.mapEntitiesToDomain(characterList))
                }
                is ApiResponse.Error -> {
                    Resource.Error(it.errorMessage)
                }
                else -> {
                    Resource.Error("Unknown error")
                }
            }
        }
    }
}