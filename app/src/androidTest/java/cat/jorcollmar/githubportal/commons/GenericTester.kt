package cat.jorcollmar.githubportal.commons

import android.content.res.Resources
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import cat.jorcollmar.githubportal.commons.RecyclerViewTestUtils.withRecyclerView
import org.hamcrest.Matchers

open class GenericTester {
    protected fun getString(@StringRes id: Int): String {
        val res: Resources = getInstrumentation().targetContext.resources
        return res.getString(id)
    }

    protected fun doClick(text: String) {
        onView(withText(text)).perform(click())
    }

    protected fun isDisplayedWithText(text: String) {
        onView(withText(text)).check(matches(isDisplayed()))
    }

    protected fun containsText(id: Int, text: String) {
        onView(withId(id))
            .check(matches(withText(Matchers.containsString(text))))
    }

    protected fun isVisible(id: Int) {
        onView(withId(id)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    protected fun isGone(id: Int) {
        onView(withId(id)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    protected fun recyclerViewContainsItems(recyclerId: Int, count: Int) {
        onView(withId(recyclerId)).check(RecyclerViewItemCountAssertion(count))
    }

    protected fun <VH : RecyclerView.ViewHolder> recyclerViewItemHasText(
        recyclerId: Int, viewId: Int, item: Int, text: String
    ) {
        onView(withId(recyclerId)).perform(scrollToPosition<VH>(item))
        onView(withRecyclerView(recyclerId).atPositionOnView(item, viewId)).check(
            matches(withText(text))
        )
    }

    protected fun <VH : RecyclerView.ViewHolder> recyclerViewItemClick(recyclerId: Int, item: Int) {
        onView(withId(recyclerId)).perform(scrollToPosition<VH>(item))
        onView(withRecyclerView(recyclerId).atPosition(item)).perform(click())
    }
}