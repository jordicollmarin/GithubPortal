package cat.jorcollmar.data.repositories.exceptions

class RepositoriesException(
    val errorCode: String?,
    val errorMessage: String?
) : Exception() {
    override fun toString(): String {
        return super.toString() + " [$errorCode:$errorMessage]"
    }
}