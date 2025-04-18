package cat.jorcollmar.domain.repositories.repository

import cat.jorcollmar.domain.repositories.datasource.RemoteGithubDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GithubRepository(private val remoteGithubDataSource: RemoteGithubDataSource) {

    suspend fun getTrendingRepositories(language: String, sort: String) =
        withContext(Dispatchers.IO) {
            remoteGithubDataSource.getTrendingRepositories(language, sort)
        }
}