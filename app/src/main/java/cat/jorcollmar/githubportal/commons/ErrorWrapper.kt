package cat.jorcollmar.githubportal.commons

data class ErrorWrapper(
    val title: String,
    val message: String,
    val retryCallback: (() -> Unit)? = null
)