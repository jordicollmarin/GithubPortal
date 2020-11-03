package cat.jorcollmar.githubportal.ui.githubrepositories.view

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cat.jorcollmar.data.repositories.exceptions.EmptyRepositoriesException
import cat.jorcollmar.domain.repositories.model.GithubRepositoryModel
import cat.jorcollmar.githubportal.R
import cat.jorcollmar.githubportal.commons.ErrorWrapper
import cat.jorcollmar.githubportal.commons.Resource
import cat.jorcollmar.githubportal.commons.extensions.createSingleChoiceAlertDialog
import cat.jorcollmar.githubportal.commons.extensions.observe
import cat.jorcollmar.githubportal.commons.extensions.showErrorDialog
import cat.jorcollmar.githubportal.commons.utils.GithubPanelUtils
import cat.jorcollmar.githubportal.databinding.FragmentGithubRepositoriesListBinding
import cat.jorcollmar.githubportal.ui.githubrepositories.adapters.RepositoriesAdapter
import cat.jorcollmar.githubportal.ui.githubrepositories.view.GithubRepositoriesViewModel.Companion.LANGUAGE_GO
import cat.jorcollmar.githubportal.ui.githubrepositories.view.GithubRepositoriesViewModel.Companion.LANGUAGE_JAVASCRIPT
import cat.jorcollmar.githubportal.ui.githubrepositories.view.GithubRepositoriesViewModel.Companion.LANGUAGE_KOTLIN
import cat.jorcollmar.githubportal.ui.githubrepositories.view.GithubRepositoriesViewModel.Companion.LANGUAGE_SWIFT
import org.koin.android.viewmodel.ext.android.sharedViewModel

class GithubRepositoriesListFragment : Fragment() {
    lateinit var binding: FragmentGithubRepositoriesListBinding
    private val viewModel: GithubRepositoriesViewModel by sharedViewModel()

    private val repositoriesAdapter: RepositoriesAdapter by lazy {
        RepositoriesAdapter(::openRepositoryDetail)
    }

    private fun openRepositoryDetail(githubRepositoryModel: GithubRepositoryModel) {
        viewModel.setSelectedRepository(githubRepositoryModel)
        findNavController().navigate(GithubRepositoriesListFragmentDirections.actionRepositoriesListFragmentToRepositoryDetailFragment())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        if (!viewModel.repositoriesLoaded) {
            viewModel.listTrendingRepositories()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGithubRepositoriesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rcvRepositories.layoutManager = LinearLayoutManager(context)
        binding.rcvRepositories.adapter = repositoriesAdapter
        initObservers()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        viewModel.selectedRepositoryLanguage.let {
            when (it) {
                LANGUAGE_KOTLIN -> menu.findItem(R.id.action_filter_kotlin).setChecked(true)
                LANGUAGE_SWIFT -> menu.findItem(R.id.action_filter_swift).setChecked(true)
                LANGUAGE_JAVASCRIPT -> menu.findItem(R.id.action_filter_javascript).setChecked(true)
                else -> menu.findItem(R.id.action_filter_go).setChecked(true)
            }
        } ?: run {
            menu.findItem(R.id.action_filter_kotlin).setChecked(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_repositories_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_filter_kotlin -> onFilterClicked(item, LANGUAGE_KOTLIN)
            R.id.action_filter_swift -> onFilterClicked(item, LANGUAGE_SWIFT)
            R.id.action_filter_javascript -> onFilterClicked(item, LANGUAGE_JAVASCRIPT)
            R.id.action_filter_go -> onFilterClicked(item, LANGUAGE_GO)
            R.id.action_sort_by -> onSortByClicked()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onFilterClicked(item: MenuItem, filterType: String): Boolean {
        item.isChecked = item.isChecked.not()
        viewModel.setSelectedLanguage(filterType)
        return false
    }

    private fun onSortByClicked(): Boolean {
        requireContext().createSingleChoiceAlertDialog(
            getString(R.string.github_repositories_list_sort_label),
            resources.getStringArray(R.array.github_repositories_list_sort_options),
            GithubPanelUtils.getSortOption(viewModel.selectedSortingOption)
        ) { dialog, which ->
            dialog.dismiss()
            viewModel.setSelectedSortingOption(GithubPanelUtils.getSortOption(which))
        }.show()

        return false
    }

    private fun initObservers() {
        viewLifecycleOwner.observe(viewModel.repositories, { updateUI(it) })
    }

    private fun updateUI(result: Resource<List<GithubRepositoryModel>>?) {
        when (result) {
            Resource.Loading -> showLoading()
            is Resource.Success -> loadRepositoriesList(result.data)
            is Resource.KnownError -> showError(result.errorWrapper)
            is Resource.UnknownError -> handleError(result.throwable)
        }
    }

    private fun showLoading() {
        binding.prbRepositories.visibility = View.VISIBLE
        binding.rcvRepositories.visibility = View.GONE
    }

    private fun hideLoading() {
        binding.prbRepositories.visibility = View.GONE
        binding.rcvRepositories.visibility = View.VISIBLE
    }

    private fun loadRepositoriesList(repositories: List<GithubRepositoryModel>) {
        hideLoading()
        repositoriesAdapter.updateItems(repositories)
    }

    private fun showError(errorWrapper: ErrorWrapper) {
        hideLoading()
        requireContext().showErrorDialog(errorWrapper)
    }

    private fun handleError(throwable: Throwable) {
        hideLoading()
        when (throwable) {
            is EmptyRepositoriesException -> showError(wrapEmptyRepositoriesListError { viewModel.listTrendingRepositories() })
            else -> requireContext().showErrorDialog(throwable)
        }
    }

    private fun wrapEmptyRepositoriesListError(retryCallback: (() -> Unit)?) =
        ErrorWrapper(
            getString(R.string.repositories_list_empty_error_title),
            getString(R.string.repositories_list_empty_error_message),
            retryCallback
        )
}