package cat.jorcollmar.data.api

import cat.jorcollmar.data.api.model.GithubRepositoriesApiResult
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

object GithubApiFactory {
    private const val ACCEPT_HEADER_NAME = "accept"
    private const val ACCEPT_HEADER_VALUE = "application/vnd.github.v3+json"

    fun buildGithubApi(isDebug: Boolean, baseUrl: String): GithubApi {
        return buildGithubApi(
            baseUrl, buildOkHttpClient(
                buildTokenInterceptor(),
                buildLoggingInterceptor(isDebug)
            ), buildConverterFactory()
        )
    }

    private fun buildOkHttpClient(
        vararg interceptors: Interceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
        interceptors.forEach {
            builder.addInterceptor(it)
        }
        return builder.build()
    }

    private fun buildTokenInterceptor(): Interceptor {
        return Interceptor { chain ->
            chain.proceed(
                chain.request()
                    .newBuilder()
                    .addHeader(ACCEPT_HEADER_NAME, ACCEPT_HEADER_VALUE)
                    .build()
            )
        }
    }

    private fun buildLoggingInterceptor(isDebug: Boolean): Interceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }

    private fun buildConverterFactory(): Converter.Factory =
        MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build())

    private fun buildGithubApi(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory
    ): GithubApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
        return retrofit.create(GithubApi::class.java)
    }

    interface GithubApi {
        @GET("search/repositories")
        suspend fun searchRepositories(
            @Query("q") query: String = "",
            @Query("sort") sort: String = "stars",
            @Query("per_page") perPage: Int = 10,
            @Query("page") page: Int = 1
        ): Response<GithubRepositoriesApiResult>
    }
}