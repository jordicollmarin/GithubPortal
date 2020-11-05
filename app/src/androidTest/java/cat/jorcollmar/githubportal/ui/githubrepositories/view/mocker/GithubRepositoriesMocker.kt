package cat.jorcollmar.githubportal.ui.githubrepositories.view.mocker

import cat.jorcollmar.data.repositories.exceptions.EmptyRepositoriesException
import cat.jorcollmar.domain.commons.ResponseWrapper
import cat.jorcollmar.domain.repositories.model.GithubLicenseModel
import cat.jorcollmar.domain.repositories.model.GithubOwnerModel
import cat.jorcollmar.domain.repositories.model.GithubRepositoryModel
import cat.jorcollmar.domain.repositories.repository.GithubRepository
import io.mockk.coEvery
import org.koin.test.KoinTest
import org.koin.test.inject

internal const val KOTLIN_REPOSITORY_NAME = "KotlinRepositoryName"
internal const val KOTLIN_REPOSITORY_NAME_2 = "KotlinRepositoryName2"
internal const val SWIFT_REPOSITORY_NAME = "SwiftRepositoryName2"

internal const val HTTP_ERROR_CODE_404 = 404

class GithubRepositoriesMocker(koinTest: KoinTest) {
    private val githubRepository: GithubRepository by koinTest.inject()

    internal fun getKotlinTrendingRepositoriesSuccess() {
        coEvery {
            githubRepository.getTrendingRepositories(any(), any())
        } returns ResponseWrapper.Success(
            listOf(
                GithubRepositoryModel(
                    0,
                    KOTLIN_REPOSITORY_NAME,
                    "",
                    "",
                    "Kotlin",
                    1,
                    GithubOwnerModel(0, "", "", ""),
                    GithubLicenseModel("", "")
                ),
                GithubRepositoryModel(
                    0,
                    KOTLIN_REPOSITORY_NAME_2,
                    "",
                    "",
                    "Kotlin",
                    10,
                    GithubOwnerModel(0, "", "", ""),
                    GithubLicenseModel("", "")
                )
            )
        )
    }

    internal fun getSwiftTrendingRepositoriesSuccess() {
        coEvery {
            githubRepository.getTrendingRepositories(any(), any())
        } returns ResponseWrapper.Success(
            listOf(
                GithubRepositoryModel(
                    0,
                    SWIFT_REPOSITORY_NAME,
                    "",
                    "",
                    "Swift",
                    15,
                    GithubOwnerModel(0, "", "", ""),
                    GithubLicenseModel("", "")
                )
            )
        )
    }

    internal fun forceNetworkError() {
        coEvery {
            githubRepository.getTrendingRepositories(
                any(),
                any()
            )
        } returns ResponseWrapper.NetworkError
    }

    internal fun forceServerError() {
        coEvery {
            githubRepository.getTrendingRepositories(
                any(),
                any()
            )
        } returns ResponseWrapper.ServerError(
            HTTP_ERROR_CODE_404
        )
    }

    internal fun forceParsingError() {
        coEvery {
            githubRepository.getTrendingRepositories(
                any(),
                any()
            )
        } returns ResponseWrapper.ParsingError
    }

    internal fun forceEmptyRepositoriesException() {
        coEvery {
            githubRepository.getTrendingRepositories(
                any(),
                any()
            )
        } returns ResponseWrapper.UnknownError(EmptyRepositoriesException())
    }

    internal fun forceUnknownError() {
        coEvery {
            githubRepository.getTrendingRepositories(
                any(),
                any()
            )
        } returns ResponseWrapper.UnknownError(
            RuntimeException("Unhandled exception")
        )
    }

}