package cat.jorcollmar.githubportal.commons.extensions

import org.junit.Assert
import org.junit.Test

class IntExtensionsKtTest {
    @Test
    fun `Given a kFormat execution, Then the Integer formatted form is returned`() {
        Assert.assertEquals("1,0K", 1000.kFormat())
        Assert.assertEquals("1,0M", 1000000.kFormat())
        Assert.assertEquals("99,5M", 99500000.kFormat())
        Assert.assertEquals("900", 900.kFormat())
        Assert.assertEquals("1", 1.kFormat())
    }
}