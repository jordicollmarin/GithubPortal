package cat.jorcollmar.githubportal.commons.utils

import cat.jorcollmar.githubportal.ui.githubrepositories.view.GithubRepositoriesViewModel.Companion.SORT_FORKS
import cat.jorcollmar.githubportal.ui.githubrepositories.view.GithubRepositoriesViewModel.Companion.SORT_HELP_WANTED_ISSUES
import cat.jorcollmar.githubportal.ui.githubrepositories.view.GithubRepositoriesViewModel.Companion.SORT_STARS
import cat.jorcollmar.githubportal.ui.githubrepositories.view.GithubRepositoriesViewModel.Companion.SORT_UPDATED

object GithubPanelUtils {

    fun getSortOption(sortingOption: Int): String {
        return when (sortingOption) {
            0 -> SORT_STARS
            1 -> SORT_FORKS
            2 -> SORT_HELP_WANTED_ISSUES
            else -> SORT_UPDATED
        }
    }

    fun getSortOption(sortingOption: String): Int {
        return when (sortingOption) {
            SORT_STARS -> 0
            SORT_FORKS -> 1
            SORT_HELP_WANTED_ISSUES -> 2
            else -> 3
        }
    }
}