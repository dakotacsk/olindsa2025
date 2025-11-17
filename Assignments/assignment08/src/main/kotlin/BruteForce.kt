/**
 * Finds the nearest neighbor to a query point from a list of points using brute force.
 * @param points The list of points to search.
 * @param query The query point.
 * @return The nearest point.
 */
fun bruteForceNearest(points: List<DoubleArray>, query: DoubleArray): DoubleArray {
    return points.minBy { p ->
        var sum = 0.0
        for (i in p.indices) sum += (p[i] - query[i]) * (p[i] - query[i])
        sum
    }
}