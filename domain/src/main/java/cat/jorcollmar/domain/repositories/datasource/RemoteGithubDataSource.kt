package cat.jorcollmar.domain.repositories.datasource

import cat.jorcollmar.domain.commons.ResponseWrapper
import cat.jorcollmar.domain.repositories.model.GithubRepositoryModel

interface RemoteGithubDataSource {
    suspend fun getTrendingRepositories(
        language: String, sort: String
    ): ResponseWrapper<List<GithubRepositoryModel>>
}