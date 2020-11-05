package cat.jorcollmar.githubportal.ui.githubrepositories.view

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.fragment.NavHostFragment
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import cat.jorcollmar.domain.repositories.repository.GithubRepository
import cat.jorcollmar.githubportal.R
import cat.jorcollmar.githubportal.commons.FragmentTest
import cat.jorcollmar.githubportal.ui.githubrepositories.GithubPortalActivity
import cat.jorcollmar.githubportal.ui.githubrepositories.view.mocker.GithubRepositoriesMocker
import cat.jorcollmar.githubportal.ui.githubrepositories.view.mocker.KOTLIN_REPOSITORY_NAME
import cat.jorcollmar.githubportal.ui.githubrepositories.view.mocker.KOTLIN_REPOSITORY_NAME_2
import cat.jorcollmar.githubportal.ui.githubrepositories.view.mocker.SWIFT_REPOSITORY_NAME
import cat.jorcollmar.githubportal.ui.githubrepositories.view.tester.GithubRepositoriesListTester
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.module.Module
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class GithubRepositoriesListFragmentTest :
    FragmentTest<GithubRepositoriesListFragment, GithubRepositoriesMocker, GithubRepositoriesListTester>(
        GithubRepositoriesListFragment::class.java
    ) {

    @Test
    fun test_check_menu_sorting_options_available() {
        mock {
            getKotlinTrendingRepositoriesSuccess()
        }
        test {
            doOpenMenu()
            doOpenSortDialog()
            isSortByDialogShown()
        }
    }

    @Test
    fun test_check_menu_options_available() {
        mock {
            getKotlinTrendingRepositoriesSuccess()
        }
        test {
            doOpenMenu()
            isKotlinMenuButtonAvailable()
            isSwiftMenuButtonAvailable()
            isJavascriptMenuButtonAvailable()
            isGoMenuButtonAvailable()
        }
    }

    @Test
    fun test_check_languages_filter_stars_sorting_success() {
        mock { getKotlinTrendingRepositoriesSuccess() }
        test {
            doOpenMenu()
            doClickKotlinOption()
            isLoadingGone()
            isRepositoryListVisible()
            isRepositoryListSizeCorrect(2)
            recyclerViewItemContainsText(0, KOTLIN_REPOSITORY_NAME)
            recyclerViewItemContainsText(1, KOTLIN_REPOSITORY_NAME_2)
        }
        mock {
            getSwiftTrendingRepositoriesSuccess()
        }
        test {
            isLoadingGone()
            isRepositoryListVisible()
            isRepositoryListSizeCorrect(1)
            recyclerViewItemContainsText(0, SWIFT_REPOSITORY_NAME)
        }
    }

    @Test
    fun test_repository_click_open_detail_screen() {
        mock { getKotlinTrendingRepositoriesSuccess() }
        test {
            recyclerViewItemClick(0)
            var isDestinationRepositoryDetail = false
            ActivityScenario.launch(GithubPortalActivity::class.java).onActivity {
                if (it is GithubPortalActivity) {
                    val navHostFragment =
                        it.supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                    isDestinationRepositoryDetail =
                        navHostFragment.navController.currentDestination?.label == it.getString(R.string.repository_detail_fragment_label)
                }
            }
            assert(isDestinationRepositoryDetail)
        }
    }

    @Test
    fun test_network_error() {
        mock { forceNetworkError() }
        test {
            isNetworkErrorShown()
        }
    }

    @Test
    fun test_server_error() {
        mock { forceServerError() }
        test {
            isServerErrorShown()
        }
    }

    @Test
    fun test_parsing_error() {
        mock { forceParsingError() }
        test {
            isParsingErrorShown()
        }
    }

    @Test
    fun test_empty_repositories_error() {
        mock { forceEmptyRepositoriesException() }
        test {
            isEmptyRepositoriesErrorShown()
        }
    }

    @Test
    fun test_unknown_error() {
        mock { forceUnknownError() }
        test {
            isUnknownErrorShown()
        }
    }

    override fun launchFragmentToTest() {
        launchFragmentInContainer<GithubRepositoriesListFragment>(themeResId = R.style.Theme_GithubPortal)
    }

    override fun createMockingClass(): GithubRepositoriesMocker = GithubRepositoriesMocker(this)

    override fun createTestingClass(): GithubRepositoriesListTester = GithubRepositoriesListTester()

    override fun overrideModules(): List<Module> = listOf(
        module(override = true) { single<GithubRepository> { mockk() } }
    )
}