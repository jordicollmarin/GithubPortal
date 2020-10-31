package cat.jorcollmar.data.api.model

import com.squareup.moshi.Json

data class GithubRepositoriesApiResult(
    @Json(name = "total_count") val totalCount: Int?,
    @Json(name = "incomplete_results") val incompleteResults: Boolean?,
    val items: List<GithubRepositoryApiResult>?
)