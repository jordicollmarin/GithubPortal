package cat.jorcollmar.githubportal.commons

object RecyclerViewTestUtils {
    fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
        return RecyclerViewMatcher(recyclerViewId)
    }
}