import org.example.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class GraphTest {

    @Test
    fun `graph stores directed weighted edges and exposes vertices`() {
        val g: Graph<String> = AdjacencyListGraph()
        g.addEdge("A", "B", 1.0)
        g.addEdge("A", "C", 2.5)
        g.addEdge("C", "A", 3.0) // verify directed behavior (A<-C)

        // vertices present even if no outgoing edges
        assertTrue(g.getVertices().containsAll(listOf("A", "B", "C")))

        // outgoing edges from A
        assertEquals(mapOf("B" to 1.0, "C" to 2.5), g.getEdges("A"))

        // B has no outgoing edges
        assertEquals(emptyMap<String, Double>(), g.getEdges("B"))

        // clear empties graph
        g.clear()
        assertTrue(g.getVertices().isEmpty())
        assertEquals(emptyMap<String, Double>(), g.getEdges("A"))
    }
}