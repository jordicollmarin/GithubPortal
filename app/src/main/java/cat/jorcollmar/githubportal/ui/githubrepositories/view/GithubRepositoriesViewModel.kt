package cat.jorcollmar.githubportal.ui.githubrepositories.view

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.jorcollmar.domain.repositories.model.GithubRepositoryModel
import cat.jorcollmar.domain.repositories.repository.GithubRepository
import cat.jorcollmar.githubportal.commons.Resource
import cat.jorcollmar.githubportal.extensions.manageResponse
import kotlinx.coroutines.launch

class GithubRepositoriesViewModel(
    private val resources: Resources,
    private val githubRepository: GithubRepository
) : ViewModel() {

    private val _repositories = MutableLiveData<Resource<List<GithubRepositoryModel>>>()
    val repositories: LiveData<Resource<List<GithubRepositoryModel>>>
        get() = _repositories

    fun listTrendingRepositories() {
        viewModelScope.launch {
            _repositories.postValue(Resource.Loading)
            _repositories.postValue(
                githubRepository.getTrendingRepositories()
                    .manageResponse(resources) { listTrendingRepositories() }
            )
        }
    }
}