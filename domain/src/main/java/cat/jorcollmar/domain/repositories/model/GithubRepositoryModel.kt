package cat.jorcollmar.domain.repositories.model

data class GithubRepositoryModel(
    val id: Int,
    val name: String?,
    val description: String?,
    val htmlUrl: String?,
    val language: String?,
    val stargazersCount: Int?,
    val owner: GithubOwnerModel?,
    val license: GithubLicenseModel?
)