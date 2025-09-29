package org.example

/**
 * `Graph` represents a directed, weighted graph.
 * @param V The type representing a vertex in the graph.
 */
interface Graph<V> {
    /**
     * @return The set of all vertices in the graph.
     */
    fun getVertices(): Set<V>

    /**
     * Adds a directed edge from [from] to [to] with a given [cost].
     * @param from The starting vertex of the edge.
     * @param to The ending vertex of the edge.
     * @param cost The weight of the edge.
     */
    fun addEdge(from: V, to: V, cost: Double)

    /**
     * Retrieves all edges originating from a given vertex.
     * @param from The vertex to get the outgoing edges from.
     * @return A map where each key is a destination vertex and the value is the edge cost.
     */
    fun getEdges(from: V): Map<V, Double>

    /**
     * Removes all vertices and edges from the graph.
     */
    fun clear()
}

/**
 * An implementation of a [Graph] using an adjacency list.
 *
 * The graph is represented by a map where each key is a vertex
 * and the value is another map of its neighbors to their edge weights.
 *
 * @param V The type representing a vertex in the graph.
 */
class AdjacencyListGraph<V> : Graph<V> {
    // Map each vertex to a map of (neighbor -> edge weight)
    private val adj: MutableMap<V, MutableMap<V, Double>> = mutableMapOf()

    override fun getVertices(): Set<V> = adj.keys

    override fun addEdge(from: V, to: V, cost: Double) {
        val neighbors = adj.getOrPut(from) { mutableMapOf() }
        neighbors[to] = cost
        adj.getOrPut(to) { mutableMapOf() } // Ensure the destination vertex is in the graph
    }

    override fun getEdges(from: V): Map<V, Double> = adj[from]?.toMap() ?: emptyMap()

    override fun clear() { adj.clear() }
}
