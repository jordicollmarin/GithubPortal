package cat.jorcollmar.githubportal.commons

sealed class Resource<out R> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class KnownError(val errorWrapper: ErrorWrapper) : Resource<Nothing>()
    data class UnknownError(val throwable: Throwable) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}