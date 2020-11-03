package cat.jorcollmar.githubportal.di

import cat.jorcollmar.data.api.GithubApiFactory
import cat.jorcollmar.githubportal.BuildConfig
import cat.jorcollmar.githubportal.ui.githubrepositories.view.GithubRepositoriesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { androidContext().resources }
}

val appViewModelModule = module {
    viewModel {
        GithubRepositoriesViewModel(get(), get())
    }
}

val appApiModule = module {
    single {
        GithubApiFactory.buildGithubApi(
            BuildConfig.DEBUG,
            BuildConfig.GITHUB_API_BASE_URL
        )
    }
}