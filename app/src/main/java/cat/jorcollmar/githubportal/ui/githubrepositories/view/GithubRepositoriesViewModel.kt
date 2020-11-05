package cat.jorcollmar.githubportal.ui.githubrepositories.view

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.jorcollmar.domain.repositories.model.GithubRepositoryModel
import cat.jorcollmar.domain.repositories.repository.GithubRepository
import cat.jorcollmar.githubportal.commons.Resource
import cat.jorcollmar.githubportal.commons.extensions.manageResponse
import kotlinx.coroutines.launch

class GithubRepositoriesViewModel(
    private val githubRepository: GithubRepository,
    private val resources: Resources
) : ViewModel() {

    private val _repositories = MutableLiveData<Resource<List<GithubRepositoryModel>>>()
    val repositories: LiveData<Resource<List<GithubRepositoryModel>>>
        get() = _repositories

    private var _repositoriesLoaded: Boolean = false
    val repositoriesLoaded: Boolean
        get() = _repositoriesLoaded

    private var _selectedRepository: GithubRepositoryModel? = null
    val selectedRepository: GithubRepositoryModel?
        get() = _selectedRepository

    private var _selectedSortingOption: String = SORT_STARS
        set(value) {
            field = value
            listTrendingRepositories()
        }
    val selectedSortingOption: String
        get() = _selectedSortingOption

    private var _selectedRepositoryLanguage: String = LANGUAGE_KOTLIN
        set(value) {
            field = value
            listTrendingRepositories()
        }
    val selectedRepositoryLanguage: String
        get() = _selectedRepositoryLanguage

    fun listTrendingRepositories() {
        viewModelScope.launch {
            _repositories.postValue(Resource.Loading)
            _repositories.postValue(
                githubRepository.getTrendingRepositories(
                    _selectedRepositoryLanguage,
                    _selectedSortingOption
                ).also {
                    _repositoriesLoaded = true
                }.manageResponse(resources) {
                    listTrendingRepositories()
                }
            )
        }
    }

    fun setSelectedRepository(repositoryModel: GithubRepositoryModel) {
        _selectedRepository = repositoryModel
    }

    fun setSelectedSortingOption(sortingOption: String) {
        _selectedSortingOption = sortingOption
    }

    fun setSelectedLanguage(language: String) {
        _selectedRepositoryLanguage = language
    }

    companion object {
        const val SORT_STARS = "stars"
        const val SORT_FORKS = "forks"
        const val SORT_HELP_WANTED_ISSUES = "help-wanted-issues"
        const val SORT_UPDATED = "updated"

        const val LANGUAGE_KOTLIN = "language:kotlin"
        const val LANGUAGE_SWIFT = "language:swift"
        const val LANGUAGE_JAVASCRIPT = "language:javascript"
        const val LANGUAGE_GO = "language:go"
    }
}