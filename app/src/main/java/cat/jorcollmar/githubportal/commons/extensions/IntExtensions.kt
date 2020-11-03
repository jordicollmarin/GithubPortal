package cat.jorcollmar.githubportal.commons.extensions

fun Int.kFormat(): String =
    when {
        this in 1_000..999_999 -> String.format("%.1fK", this / 1000.0)
        this > 999_999 -> String.format("%.1fM", this / 1000000.0)
        else -> this.toString()
    }