package cat.jorcollmar.githubportal.commons

import androidx.fragment.app.Fragment
import org.junit.After
import org.junit.Before
import org.koin.core.module.Module
import org.koin.test.KoinTest

abstract class FragmentTest<A : Fragment, M, T : GenericTester>(
    val fragmentClass: Class<A>
) :
    KoinTest {

    @Before
    fun before() {
        getKoin().loadModules(overrideModules())
    }

    @After
    fun after() {
        getKoin().unloadModules(overrideModules())
    }

    protected fun mock(initMocking: M.() -> Unit) {
        createMockingClass().initMocking()
        launchFragmentToTest()
    }

    protected fun test(testing: T.() -> Unit) {
        createTestingClass().testing()
    }


    protected abstract fun launchFragmentToTest()

    protected abstract fun createMockingClass(): M

    protected abstract fun createTestingClass(): T

    protected abstract fun overrideModules(): List<Module>
}