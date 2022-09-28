package com.kadabengaran.rickalmanac.core.data

import com.kadabengaran.rickalmanac.core.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

abstract class NetworkBoundResource<ResultType, RequestType> {

    private var result: Flow<Resource<out ResultType?>> = flow {
        emit(Resource.Loading())
        val dbSource = loadFromDB()?.first()
        if (shouldFetch(dbSource)) {
            emit(Resource.Loading())
            when (val apiResponse = createCall().first()) {
                is ApiResponse.Success -> {
                    saveCallResult(apiResponse.data)
                    loadFromDB()?.collect { emit(Resource.Success(it)) }
                }
                is ApiResponse.Empty -> loadFromDB()?.collect { emit(Resource.Success(it)) }
                is ApiResponse.Error -> {
                    onFetchFailed()
                    emit(Resource.Error<ResultType>(apiResponse.errorMessage))
                }
            }
        } else loadFromDB()?.collect { emit(Resource.Success(it)) }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flow<ResultType?>?

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow(): Flow<Resource<out ResultType?>> = result
}