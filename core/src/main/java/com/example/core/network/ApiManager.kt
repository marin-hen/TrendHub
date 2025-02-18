package com.example.core.network

import retrofit2.Retrofit

interface ApiExecutor {
    suspend fun <T> execute(block: suspend () -> T): T
}

class ApiManager(
    val retrofit: Retrofit,
    private val mapper: NetworkExceptionMapper
) : ApiExecutor {
    inline fun <reified T> create() = retrofit.create<T>(T::class.java)

    override suspend fun <T> execute(block: suspend () -> T): T = try {
        block.invoke()
    } catch (throwable: Throwable) {
        throw mapper.map(throwable)
    }
}