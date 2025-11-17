import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals
import kotlin.random.Random

class BruteForceTest {

    /**
     * Tests that the brute force nearest neighbor search returns the correct neighbor.
     */
    @Test
    fun testBruteForceCorrect() {
        val pts = listOf(
            doubleArrayOf(0.0, 0.0),
            doubleArrayOf(2.0, 0.0),
            doubleArrayOf(5.0, 5.0)
        )

        val q = doubleArrayOf(1.0, 0.0)
        val nn = bruteForceNearest(pts, q)

        assertContentEquals(doubleArrayOf(0.0, 0.0), nn)
    }

    /**
     * Tests that the brute force nearest neighbor search is consistent for random points.
     */
    @Test
    fun testRandomConsistency() {
        val pts = List(20) { doubleArrayOf(Random.nextDouble(), Random.nextDouble()) }

        repeat(10) {
            val q = doubleArrayOf(Random.nextDouble(), Random.nextDouble())
            val nn = bruteForceNearest(pts, q)

            // Ensure returned element exists in list
            assert(pts.contains(nn))
        }
    }
}