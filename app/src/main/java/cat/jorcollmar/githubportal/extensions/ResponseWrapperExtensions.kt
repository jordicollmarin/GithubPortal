package cat.jorcollmar.githubportal.extensions

import android.content.res.Resources
import cat.jorcollmar.domain.commons.ResponseWrapper
import cat.jorcollmar.githubportal.R
import cat.jorcollmar.githubportal.commons.ErrorWrapper
import cat.jorcollmar.githubportal.commons.Resource

fun <T> ResponseWrapper<T>.manageResponse(
    resources: Resources, retry: (() -> Unit)?
) = when (this) {
    is ResponseWrapper.Success -> Resource.Success(this.data)
    is ResponseWrapper.EmptyRepositoriesListError ->
        Resource.KnownError(wrapEmptyRepositoriesListError(resources, retry))
    is ResponseWrapper.NetworkError -> Resource.KnownError(wrapNetworkError(resources))
    is ResponseWrapper.ServerError -> Resource.KnownError(wrapServerError(resources, retry))
    is ResponseWrapper.ParsingError -> Resource.KnownError(wrapParsingError(resources, retry))
    is ResponseWrapper.UnknownError -> Resource.UnknownError(this.throwable)
}

private fun wrapEmptyRepositoriesListError(resources: Resources, retryCallback: (() -> Unit)?) =
    ErrorWrapper(
        resources.getString(R.string.repositories_list_empty_error_title),
        resources.getString(R.string.repositories_list_empty_error_message),
        retryCallback
    )

private fun wrapNetworkError(resources: Resources) =
    ErrorWrapper(
        resources.getString(R.string.network_error_title),
        resources.getString(R.string.network_error_message)
    )

private fun wrapServerError(resources: Resources, retryCallback: (() -> Unit)?) =
    ErrorWrapper(
        resources.getString(R.string.server_error_title),
        resources.getString(R.string.server_error_message),
        retryCallback
    )


private fun wrapParsingError(resources: Resources, retryCallback: (() -> Unit)?) =
    ErrorWrapper(
        resources.getString(R.string.parsing_error_title),
        resources.getString(R.string.parsing_error_message),
        retryCallback
    )

