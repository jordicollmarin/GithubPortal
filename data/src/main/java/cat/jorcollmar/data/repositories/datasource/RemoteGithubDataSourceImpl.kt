package cat.jorcollmar.data.repositories.datasource

import cat.jorcollmar.data.api.GithubApiFactory
import cat.jorcollmar.data.repositories.exceptions.EmptyRepositoriesException
import cat.jorcollmar.data.repositories.exceptions.RepositoriesException
import cat.jorcollmar.data.repositories.mappers.map
import cat.jorcollmar.domain.commons.ResponseWrapper
import cat.jorcollmar.domain.repositories.datasource.RemoteGithubDataSource
import com.squareup.moshi.JsonEncodingException
import retrofit2.HttpException
import java.io.IOException

class RemoteGithubDataSourceImpl(
    private val githubApi: GithubApiFactory.GithubApi
) : RemoteGithubDataSource {

    override suspend fun getTrendingRepositories(
        language: String, sort: String
    ) = try {
        val response = githubApi.searchRepositories(language = language, sort = sort)
        if (response.isSuccessful) {
            response.body()?.items?.let { repositories ->
                ResponseWrapper.Success(repositories.map { it.map() })
            } ?: run {
                throw EmptyRepositoriesException()
            }
        } else {
            throw RepositoriesException(response.code().toString(), response.message())
        }
    } catch (throwable: Throwable) {
        when (throwable) {
            is NumberFormatException -> ResponseWrapper.ParsingError
            is JsonEncodingException -> ResponseWrapper.ParsingError
            is IOException -> ResponseWrapper.NetworkError
            is HttpException -> ResponseWrapper.ServerError(throwable.code())
            else -> ResponseWrapper.UnknownError(throwable)
        }
    }
}