package com.example.trending.domain.usecase

import com.example.core.di.IOContext
import com.example.core.usecase.ExceptionsResolver
import com.example.core.usecase.UseCaseWithParam
import com.example.trending.domain.models.TrendingRepo
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetTrendingRepositoriesUseCase @Inject constructor(
    @IOContext context: CoroutineContext,
    resolver: ExceptionsResolver,
    private val repository: TrendingRepository
) : UseCaseWithParam<Int, List<TrendingRepo>>(context, resolver) {
    override suspend fun execute(param: Int): List<TrendingRepo> {
        val perPage = 20
        return repository.getTrendingRepositories(page = param, perPage = perPage)
    }
}