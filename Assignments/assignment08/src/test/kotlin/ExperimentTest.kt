import org.junit.jupiter.api.Test
import kotlin.test.assertTrue
import kotlin.time.Duration

class ExperimentTest {

    /**
     * Tests that the experiment runs without errors for a small number of points.
     */
    @Test
    fun testSmallExperiment() {
        val (build, kd, brute) = runExperiment(k = 3, numPoints = 50)

        assertTrue(build is Duration)
        assertTrue(kd is Duration)
        assertTrue(brute is Duration)
    }
}