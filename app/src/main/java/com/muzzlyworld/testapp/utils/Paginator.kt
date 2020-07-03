package com.muzzlyworld.testapp.utils

import kotlinx.coroutines.Job
import kotlin.coroutines.coroutineContext

abstract class Paginator<V1 : Any> {

    private var loadInitialDataJob: Job? = null
    private var loadNextDataJob: Job? = null

    private var page: Int = 1
    private var maxPage: Int = 1

    suspend fun loadLatestData(): Result<List<V1>> {
        loadNextDataJob?.cancel()
        loadInitialDataJob = coroutineContext[Job]
        page = 1

        return when(val result = loadLatest(page)) {
            is Result.Success -> {
                maxPage = result.data.second
                page++
                Result.Success(result.data.first)
            }
            else -> Result.Error
        }
    }

    suspend fun loadNextData() : Result<List<V1>> {
        kotlin.runCatching { loadInitialDataJob?.join() }
        require(page <= maxPage) { "All data loaded" }

        loadNextDataJob = coroutineContext[Job]
        return when(val result = loadNext(page)) {
            is Result.Success -> {
                page++
                Result.Success(result.data.first)
            }
            else -> Result.Error
        }
    }

    fun isAllDataLoaded(): Boolean = page > maxPage

    protected abstract suspend fun loadLatest(startPage: Int) : Result<Pair<List<V1>, Int>>
    protected abstract suspend fun loadNext(page: Int) : Result<Pair<List<V1>, Int>>
}