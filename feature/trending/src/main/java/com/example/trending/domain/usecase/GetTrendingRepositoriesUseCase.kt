package com.example.trending.domain.usecase

import com.example.core.di.IOContext
import com.example.core.usecase.ExceptionsResolver
import com.example.core.usecase.UseCaseWithParam
import com.example.trending.domain.models.GetRepositoriesParams
import com.example.trending.domain.models.TrendingResponse
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetTrendingRepositoriesUseCase @Inject constructor(
    @IOContext context: CoroutineContext,
    resolver: ExceptionsResolver,
    private val repository: TrendRepository
) : UseCaseWithParam<GetRepositoriesParams, TrendingResponse>(context, resolver) {
    public override suspend fun execute(param: GetRepositoriesParams): TrendingResponse {
        return repository.getTrendingRepositories(param)
    }
}