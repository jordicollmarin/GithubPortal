package cat.jorcollmar.data.api.model

import com.squareup.moshi.Json

data class GithubRepositoryApiResult(
    val id: Int,
    val name: String?,
    val description: String?,
    @Json(name = "html_url") val htmlUrl: String?,
    val language: String?,
    @Json(name = "stargazers_count") val stargazersCount: Int?,
    val owner: GithubOwnerApiResult?,
    val license: GithubLicenseApiResult?
)