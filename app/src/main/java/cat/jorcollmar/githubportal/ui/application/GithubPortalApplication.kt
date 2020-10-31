package cat.jorcollmar.githubportal.ui.application

import android.app.Application
import cat.jorcollmar.data.repositories.di.githubDataModule
import cat.jorcollmar.domain.repositories.di.githubDomainModule
import cat.jorcollmar.githubportal.di.appApiModule
import cat.jorcollmar.githubportal.di.appModule
import cat.jorcollmar.githubportal.di.appViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GithubPortalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@GithubPortalApplication)
            androidFileProperties()
            koin.loadModules(
                listOf(
                    appModule,
                    appViewModelModule,
                    appApiModule,
                    githubDataModule,
                    githubDomainModule
                )
            )
            koin.createRootScope()
        }
    }
}