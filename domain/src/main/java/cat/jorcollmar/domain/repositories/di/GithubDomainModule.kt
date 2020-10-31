package cat.jorcollmar.domain.repositories.di

import cat.jorcollmar.domain.repositories.repository.GithubRepository
import org.koin.dsl.module

val githubDomainModule = module {
    single {
        GithubRepository(get())
    }
}