package org.example

/**
 * A min-priority queue where elements with lower priority values are removed first.
 * @param T The type of elements in the queue.
 */
interface MinPriorityQueue<T> {
    /**
     * @return True if the queue is empty, false otherwise.
     */
    fun isEmpty(): Boolean

    /**
     * Adds an element to the queue with a specified priority.
     * @param elem The element to add.
     * @param priority The priority of the element. Lower values are higher priority.
     */
    fun addWithPriority(elem: T, priority: Double)

    /**
     * Retrieves and removes the element with the highest priority (lowest value).
     * @return The highest-priority element, or null if the queue is empty.
     */
    fun next(): T?

    /**
     * Adjusts the priority of an existing element in the queue.
     * If the element does not exist, it is added.
     * @param elem The element whose priority is to be adjusted.
     * @param newPriority The new priority for the element.
     */
    fun adjustPriority(elem: T, newPriority: Double)
}

/**
 * A binary min-heap implementation of a [MinPriorityQueue].
 * The heap is backed by a [MutableList].
 */
private data class Node<T>(var priority: Double, val value: T)

class MinHeap<T> : MinPriorityQueue<T> {
    private val a = mutableListOf<Node<T>>()  // array-backed heap

    override fun isEmpty(): Boolean = a.isEmpty()

    override fun addWithPriority(elem: T, priority: Double) {
        a.add(Node(priority, elem))
        siftUp(a.lastIndex)
    }

    override fun next(): T? {
        if (a.isEmpty()) return null
        swap(0, a.lastIndex)
        val res = a.removeAt(a.lastIndex).value
        siftDown(0)
        return res
    }

    override fun adjustPriority(elem: T, newPriority: Double) {
        // Finds element by scanning, then fix heap
        val i = a.indexOfFirst { it.value == elem }
        if (i == -1) {
            addWithPriority(elem, newPriority)
            return
        }
        val old = a[i].priority
        a[i].priority = newPriority
        if (newPriority < old) siftUp(i) else siftDown(i)
    }

    // heap helpers
    private fun parent(i: Int) = (i - 1) / 2
    private fun left(i: Int) = 2 * i + 1
    private fun right(i: Int) = 2 * i + 2

    private fun siftUp(i0: Int) {
        var i = i0
        while (i > 0) {
            val p = parent(i)
            if (a[i].priority < a[p].priority) { swap(i, p); i = p } else break
        }
    }

    private fun siftDown(i0: Int) {
        var i = i0
        while (true) {
            val l = left(i)
            val r = right(i)
            var smallest = i
            if (l < a.size && a[l].priority < a[smallest].priority) smallest = l
            if (r < a.size && a[r].priority < a[smallest].priority) smallest = r
            if (smallest == i) break
            swap(i, smallest)
            i = smallest
        }
    }

    private fun swap(i: Int, j: Int) {
        val tmp = a[i]
        a[i] = a[j]
        a[j] = tmp
    }
}

/**
 * Finds the shortest path between two vertices in a graph using Dijkstra's algorithm.
 *
 * @param V The type of the vertices.
 * @param graph The graph to search.
 * @param start The starting vertex.
 * @param goal The destination vertex.
 * @return A list of vertices representing the shortest path from [start] to [goal],
 * or `null` if no path exists.
 */
fun <V> dijkstra(graph: Graph<V>, start: V, goal: V): List<V>? {
    if (start == goal) return listOf(start)

    val dist = mutableMapOf<V, Double>()
    val prev = mutableMapOf<V, V?>()
    val visited = mutableSetOf<V>()
    val pq: MinPriorityQueue<V> = MinHeap()

    dist[start] = 0.0
    prev[start] = null
    pq.addWithPriority(start, 0.0)

    while (!pq.isEmpty()) {
        val u = pq.next() ?: break
        if (!visited.add(u)) continue
        if (u == goal) break

        val du = dist[u] ?: continue
        for ((v, w) in graph.getEdges(u)) {
            val alt = du + w
            if (alt < (dist[v] ?: Double.POSITIVE_INFINITY)) {
                dist[v] = alt
                prev[v] = u
                pq.adjustPriority(v, alt)
            }
        }
    }

    if (!dist.containsKey(goal)) return null

    // Reconstructs path from goal back to start
    val path = ArrayDeque<V>()
    var cur: V? = goal
    while (cur != null) {
        path.addFirst(cur)
        cur = prev[cur]
    }
    return path.toList()
}
