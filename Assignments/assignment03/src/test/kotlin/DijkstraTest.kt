import org.example.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class DijkstraTest {

    @Test
    fun `finds shortest path when multiple routes exist`() {
        val g: Graph<String> = AdjacencyListGraph()
        g.addEdge("A", "B", 1.0)
        g.addEdge("B", "C", 2.0)   // A->B->C cost 3
        g.addEdge("A", "C", 10.0)  // direct path is longer

        val path = dijkstra(g, "A", "C")
        assertEquals(listOf("A", "B", "C"), path)
    }

    @Test
    fun `returns null when destination is unreachable`() {
        val g: Graph<String> = AdjacencyListGraph()
        g.addEdge("A", "B", 1.0)
        // No path to C
        val path = dijkstra(g, "A", "C")
        assertNull(path)
    }
}