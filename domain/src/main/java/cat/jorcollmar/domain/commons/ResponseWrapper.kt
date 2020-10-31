package cat.jorcollmar.domain.commons

sealed class ResponseWrapper<out R> {
    data class Success<out T>(val data: T) : ResponseWrapper<T>()
    object EmptyRepositoriesListError : ResponseWrapper<Nothing>()
    object NetworkError : ResponseWrapper<Nothing>()
    data class ServerError(val code: Int) : ResponseWrapper<Nothing>()
    object ParsingError : ResponseWrapper<Nothing>()
    data class UnknownError(val throwable: Throwable) : ResponseWrapper<Nothing>()
}