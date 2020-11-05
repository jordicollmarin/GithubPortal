package cat.jorcollmar.githubportal.ui.githubrepositories.view.tester

import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import cat.jorcollmar.githubportal.R
import cat.jorcollmar.githubportal.commons.GenericTester
import cat.jorcollmar.githubportal.ui.githubrepositories.adapters.RepositoriesAdapter

class GithubRepositoriesListTester : GenericTester() {
    internal fun doOpenMenu() {
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext())
    }

    internal fun doOpenSortDialog() {
        doClick(getString(R.string.github_repositories_list_sort_label))
    }

    internal fun isSortByDialogShown() {
        isDisplayedWithText(getString(R.string.github_repositories_list_sort_label))
        isDisplayedWithText("Stars")
        isDisplayedWithText("Forks")
        isDisplayedWithText("Help-wanted issues")
        isDisplayedWithText("Updated")
    }

    internal fun isKotlinMenuButtonAvailable() {
        isDisplayedWithText(getString(R.string.github_repositories_list_filter_kotlin))
    }

    internal fun isSwiftMenuButtonAvailable() {
        isDisplayedWithText(getString(R.string.github_repositories_list_filter_swift))
    }

    internal fun isJavascriptMenuButtonAvailable() {
        isDisplayedWithText(getString(R.string.github_repositories_list_filter_javascript))
    }

    internal fun isGoMenuButtonAvailable() {
        isDisplayedWithText(getString(R.string.github_repositories_list_filter_go))
    }

    internal fun doClickSwiftOption() {
        doClick(getString(R.string.github_repositories_list_filter_swift))
    }

    internal fun doClickKotlinOption() {
        doClick(getString(R.string.github_repositories_list_filter_kotlin))
    }

    internal fun isLoadingGone() {
        isGone(R.id.prbRepositories)
    }

    internal fun isRepositoryListVisible() {
        isVisible(R.id.rcvRepositories)
    }

    internal fun isRepositoryListSizeCorrect(itemsCount: Int) {
        recyclerViewContainsItems(R.id.rcvRepositories, itemsCount)
    }

    internal fun recyclerViewItemContainsText(itemPosition: Int, repositoryName: String) {
        recyclerViewItemHasText<RepositoriesAdapter.ViewHolder>(
            R.id.rcvRepositories, R.id.txvRepositoryName, itemPosition, repositoryName
        )
    }

    internal fun recyclerViewItemClick(itemPosition: Int) {
        recyclerViewItemClick<RepositoriesAdapter.ViewHolder>(
            R.id.rcvRepositories, itemPosition
        )
    }

    internal fun isNetworkErrorShown() {
        isDisplayedWithText(getString(R.string.network_error_title))
        isDisplayedWithText(getString(R.string.network_error_message))
        isDisplayedWithText(getString(android.R.string.ok))
    }

    internal fun isServerErrorShown() {
        isDisplayedWithText(getString(R.string.server_error_title))
        isDisplayedWithText(getString(R.string.server_error_message))
        isDisplayedWithText(getString(R.string.button_exit))
        isDisplayedWithText(getString(R.string.button_retry))
    }

    internal fun isParsingErrorShown() {
        isDisplayedWithText(getString(R.string.parsing_error_title))
        isDisplayedWithText(getString(R.string.parsing_error_message))
        isDisplayedWithText(getString(R.string.button_exit))
        isDisplayedWithText(getString(R.string.button_retry))
    }

    internal fun isEmptyRepositoriesErrorShown() {
        isDisplayedWithText(getString(R.string.repositories_list_empty_error_title))
        isDisplayedWithText(getString(R.string.repositories_list_empty_error_message))
        isDisplayedWithText(getString(R.string.button_exit))
        isDisplayedWithText(getString(R.string.button_retry))
    }

    internal fun isUnknownErrorShown() {
        isDisplayedWithText(getString(R.string.unknown_error_title))
        containsText(android.R.id.message, getString(R.string.unknown_error_message))
        isDisplayedWithText(getString(R.string.button_exit))
    }
}