package cat.jorcollmar.data.repositories.di

import cat.jorcollmar.data.repositories.datasource.RemoteGithubDataSourceImpl
import cat.jorcollmar.domain.repositories.datasource.RemoteGithubDataSource
import org.koin.dsl.module

val githubDataModule = module {
    single<RemoteGithubDataSource> {
        RemoteGithubDataSourceImpl(get())
    }
}