import kotlin.math.abs
import kotlin.math.sqrt

/**
 * A node in the KD-Tree.
 * @param point The point stored in the node.
 * @param axis The axis used for splitting.
 * @param left The left child of the node.
 * @param right The right child of the node.
 */
data class KDNode(
    val point: DoubleArray,
    val axis: Int,
    val left: KDNode? = null,
    val right: KDNode? = null
)

/**
 * A KD-Tree for fast nearest neighbor search.
 * @param points The points to build the tree from.
 */
class KDTree(private val points: List<DoubleArray>) {

    /**
     * The root of the KD-Tree.
     */
    val root: KDNode? = build(points, 0)

    /**
     * Builds the KD-Tree recursively.
     * @param pts The points to build the tree from.
     * @param depth The current depth of the tree.
     * @return The root of the tree.
     */
    private fun build(pts: List<DoubleArray>, depth: Int): KDNode? {
        if (pts.isEmpty()) return null

        val axis = depth % pts[0].size
        val mutable = pts.toMutableList()

        // Quickselect median
        val median = quickselectMedian(mutable, axis)
        val midIndex = mutable.indexOf(median)

        return KDNode(
            point = median,
            axis = axis,
            left = build(mutable.subList(0, midIndex), depth + 1),
            right = build(mutable.subList(midIndex + 1, mutable.size), depth + 1)
        )
    }

    /**
     * Finds the median of a list of points along a given axis using quickselect.
     * @param points The list of points.
     * @param axis The axis to consider.
     * @return The median point.
     */
    private fun quickselectMedian(points: MutableList<DoubleArray>, axis: Int): DoubleArray {
        val target = points.size / 2
        return quickselect(points, 0, points.size - 1, target, axis)
    }

    /**
     * Finds the k-th smallest element in a list of points along a given axis using quickselect.
     * @param pts The list of points.
     * @param left The left index of the sublist.
     * @param right The right index of the sublist.
     * @param k The index of the element to find.
     * @param axis The axis to consider.
     * @return The k-th smallest element.
     */
    private fun quickselect(
        pts: MutableList<DoubleArray>,
        left: Int,
        right: Int,
        k: Int,
        axis: Int
    ): DoubleArray {
        if (left == right) return pts[left]

        var l = left
        var r = right
        val pivotValue = pts[(l + r) / 2][axis]

        while (l <= r) {
            while (pts[l][axis] < pivotValue) l++
            while (pts[r][axis] > pivotValue) r--
            if (l <= r) {
                val tmp = pts[l]
                pts[l] = pts[r]
                pts[r] = tmp
                l++
                r--
            }
        }

        return when {
            k <= r -> quickselect(pts, left, r, k, axis)
            k >= l -> quickselect(pts, l, right, k, axis)
            else -> pts[k]
        }
    }

    /**
     * Finds the nearest neighbor to a query point.
     * @param query The query point.
     * @return The nearest point.
     */
    fun nearest(query: DoubleArray): DoubleArray {
        return nearestRecursive(root, query, root!!.point)
    }

    /**
     * Finds the nearest neighbor recursively.
     * @param node The current node.
     * @param query The query point.
     * @param best The best point found so far.
     * @return The nearest point.
     */
    private fun nearestRecursive(
        node: KDNode?,
        query: DoubleArray,
        best: DoubleArray
    ): DoubleArray {
        if (node == null) return best

        var currentBest = if (dist(query, node.point) < dist(query, best)) node.point else best
        val axis = node.axis

        val goLeft = query[axis] < node.point[axis]
        val first = if (goLeft) node.left else node.right
        val second = if (goLeft) node.right else node.left

        currentBest = nearestRecursive(first, query, currentBest)

        if (abs(query[axis] - node.point[axis]) < dist(query, currentBest)) {
            currentBest = nearestRecursive(second, query, currentBest)
        }

        return currentBest
    }

    /**
     * Prints the tree structure.
     */
    fun printTree() {
        printTreeRecursive(root, 0)
    }

    /**
     * Prints the tree structure recursively.
     * @param node The current node.
     * @param depth The current depth.
     */
    private fun printTreeRecursive(node: KDNode?, depth: Int) {
        if (node == null) return
        printTreeRecursive(node.right, depth + 1)
        println(" ".repeat(depth * 4) + node.point.joinToString(", "))
        printTreeRecursive(node.left, depth + 1)
    }

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
        return sqrt(sum)
    }
}