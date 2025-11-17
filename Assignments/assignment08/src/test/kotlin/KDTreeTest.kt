import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.random.Random

class KDTreeTest {

    /**
     * Calculates the Euclidean distance between two points.
     * @param a The first point.
     * @param b The second point.
     * @return The distance between the two points.
     */
    private fun dist(a: DoubleArray, b: DoubleArray): Double {
        var sum = 0.0
        for (i in a.indices) {
            val d = a[i] - b[i]
            sum += d * d
        }
        return kotlin.math.sqrt(sum)
    }

    /**
     * Tests that the KD-Tree returns the correct nearest neighbor for a simple 2D case.
     */
    @Test
    fun testSimple2DNearest() {
        val pts = listOf(
            doubleArrayOf(0.0, 0.0),
            doubleArrayOf(5.0, 5.0),
            doubleArrayOf(2.0, 1.0)
        )

        val tree = KDTree(pts)

        val query = doubleArrayOf(1.9, 1.0)
        val nn = tree.nearest(query)

        assertContentEquals(doubleArrayOf(2.0, 1.0), nn)
    }

    /**
     * Tests that the KD-Tree returns the same nearest neighbor as the brute force search.
     */
    @Test
    fun testKDTreeMatchesBruteForce() {
        val k = 5
        val pts = List(100) { DoubleArray(k) { Random.nextDouble() } }
        val tree = KDTree(pts)

        repeat(20) {
            val q = DoubleArray(k) { Random.nextDouble() }

            val brute = bruteForceNearest(pts, q)
            val kd = tree.nearest(q)

            // Distances should match (points may differ but same dist)
            val db = dist(brute, q)
            val dk = dist(kd, q)

            assertEquals(db, dk, 1e-9)
        }
    }

    /**
     * Tests that the KD-Tree works for high-dimensional data.
     */
    @Test
    fun testHighDimensional() {
        val k = 20
        val pts = List(200) { DoubleArray(k) { Random.nextDouble() } }
        val tree = KDTree(pts)

        val q = DoubleArray(k) { Random.nextDouble() }
        val nn = tree.nearest(q)

        // Should return some actual point from the list
        assert(pts.contains(nn))
    }
}