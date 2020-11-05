package cat.jorcollmar.githubportal.ui.githubrepositories.view

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import cat.jorcollmar.domain.repositories.repository.GithubRepository
import cat.jorcollmar.githubportal.R
import cat.jorcollmar.githubportal.commons.FragmentTest
import cat.jorcollmar.githubportal.ui.githubrepositories.view.mocker.GithubRepositoriesMocker
import cat.jorcollmar.githubportal.ui.githubrepositories.view.tester.GithubRepositoryDetailTester
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.module.Module
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class GithubRepositoryDetailFragmentTest :
    FragmentTest<GithubRepositoryDetailFragment, GithubRepositoriesMocker, GithubRepositoryDetailTester>(
        GithubRepositoryDetailFragment::class.java
    ) {

    @Test
    fun test_repository_information_available() {
        mock {}
        test {
            areRepositoryDetailsAvailable()
        }
    }

    override fun launchFragmentToTest() {
        launchFragmentInContainer<GithubRepositoryDetailFragment>(themeResId = R.style.Theme_GithubPortal)
    }

    override fun createMockingClass(): GithubRepositoriesMocker = GithubRepositoriesMocker(this)

    override fun createTestingClass(): GithubRepositoryDetailTester = GithubRepositoryDetailTester()

    override fun overrideModules(): List<Module> = listOf(
        module(override = true) {
            single<GithubRepository> { mockk() }
        }

    )
}