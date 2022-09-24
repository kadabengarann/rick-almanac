package com.kadabengaran.rickalmanac.core.data.source.remote

import android.util.Log
import com.kadabengaran.rickalmanac.core.data.source.remote.network.ApiResponse
import com.kadabengaran.rickalmanac.core.data.source.remote.network.ApiService
import com.kadabengaran.rickalmanac.core.data.source.remote.response.CharacterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getAllCharacter(): Flow<ApiResponse<List<CharacterResponse>>> {
        return flow {
            try {
                val response = apiService.getList()
                val dataArray = response.results
                if (dataArray.isNotEmpty()) emit(ApiResponse.Success(response.results)) else emit(ApiResponse.Empty)
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailCharacter(id: Int): Flow<ApiResponse<CharacterResponse>> {
        return flow {
            try {
                val response = apiService.getCharacterDetail(id)
                if (response != null) emit(ApiResponse.Success(response)) else emit(ApiResponse.Empty)
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun searchCharacter(query: String): Flow<ApiResponse<List<CharacterResponse>>> {
        return flow {
            try {
                val response = apiService.getSearchList(query)
                val dataArray = response.results
                if (dataArray.isNotEmpty()) emit(ApiResponse.Success(response.results)) else emit(ApiResponse.Empty)
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}
