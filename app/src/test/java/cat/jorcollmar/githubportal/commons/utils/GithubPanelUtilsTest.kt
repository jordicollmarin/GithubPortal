package cat.jorcollmar.githubportal.commons.utils

import org.junit.Assert
import org.junit.Test

class GithubPanelUtilsTest {
    @Test
    fun `Given a getSortOption execution, When sortingOption Integer parameter is passed, Then the string value of the sorting option is returned`() {
        Assert.assertEquals("stars", GithubPanelUtils.getSortOption(0))
        Assert.assertEquals("forks", GithubPanelUtils.getSortOption(1))
        Assert.assertEquals("help-wanted-issues", GithubPanelUtils.getSortOption(2))
        Assert.assertEquals("updated", GithubPanelUtils.getSortOption(3))
    }

    @Test
    fun `Given a getSortOption execution, When sortingOption String parameter is passed, Then the Integer value of the sorting option is returned`() {
        Assert.assertEquals(0, GithubPanelUtils.getSortOption("stars"))
        Assert.assertEquals(1, GithubPanelUtils.getSortOption("forks"))
        Assert.assertEquals(2, GithubPanelUtils.getSortOption("help-wanted-issues"))
        Assert.assertEquals(3, GithubPanelUtils.getSortOption("updated"))
    }
}