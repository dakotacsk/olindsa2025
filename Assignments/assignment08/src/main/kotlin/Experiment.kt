import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.measureTime

/**
 * Runs an experiment to compare the performance of the KD-Tree and brute force nearest neighbor search.
 * @param k The dimension of the points.
 * @param numPoints The number of points to generate.
 * @return A triple containing the build time, KD-Tree search time, and brute force search time.
 */
fun runExperiment(k: Int, numPoints: Int): Triple<Duration, Duration, Duration> {

    /**
     * Generates a random point in k-dimensional space.
     * @return A random point.
     */
    fun randPoint() = DoubleArray(k) { Random.nextDouble() }

    val train = List(numPoints) { randPoint() }
    val test = List(1000) { randPoint() }

    val tBuild = measureTime {
        KDTree(train)
    }

    val tree = KDTree(train)

    val tKD = measureTime {
        for (q in test) { tree.nearest(q) }
    }

    val tBrute = measureTime {
        for (q in test) bruteForceNearest(train, q)
    }

    return Triple(tBuild, tKD, tBrute)
}