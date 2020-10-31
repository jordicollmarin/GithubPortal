package cat.jorcollmar.data.repositories.mappers

import cat.jorcollmar.data.api.model.GithubRepositoryApiResult
import cat.jorcollmar.domain.repositories.model.GithubLicenseModel
import cat.jorcollmar.domain.repositories.model.GithubOwnerModel
import cat.jorcollmar.domain.repositories.model.GithubRepositoryModel

fun GithubRepositoryApiResult.map(): GithubRepositoryModel {
    return GithubRepositoryModel(
        id, name, description, htmlUrl, language, stargazersCount,
        owner?.let { GithubOwnerModel(it.id, it.login, it.avatarUrl, it.htmlUrl) },
        license?.let { GithubLicenseModel(it.key, it.name) }
    )
}