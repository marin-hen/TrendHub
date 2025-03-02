package com.example.trending.domain.usecase

import com.example.core.di.IOContext
import com.example.core.usecase.ExceptionsResolver
import com.example.core.usecase.UseCaseWithParam
import com.example.trending.domain.models.GetRepositoryByNameAndOwnerParams
import com.example.trending.domain.models.TrendingRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetRepositoryByNameAndOwnerUseCase @Inject constructor(
    @IOContext context: CoroutineContext,
    resolver: ExceptionsResolver,
    private val repository: TrendRepository
) : UseCaseWithParam<GetRepositoryByNameAndOwnerParams, TrendingRepository>(context, resolver) {
    override suspend fun execute(param: GetRepositoryByNameAndOwnerParams): TrendingRepository {
        return repository.getTrendingRepository(param)
    }
}