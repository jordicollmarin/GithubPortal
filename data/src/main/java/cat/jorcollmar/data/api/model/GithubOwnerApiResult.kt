package cat.jorcollmar.data.api.model

import com.squareup.moshi.Json

data class GithubOwnerApiResult(
    val id: Int,
    val login: String?,
    @Json(name = "avatar_url") val avatarUrl: String?,
    @Json(name = "html_url") val htmlUrl: String?
)