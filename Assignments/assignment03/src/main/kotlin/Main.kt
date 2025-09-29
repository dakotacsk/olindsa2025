package org.example

data class Cell(val row: Int, val col: Int)

/**
 * Solves a maze by modeling it as a graph and finding the shortest path using Dijkstra's algorithm.
 *
 * The maze is provided as a list of strings with the following legend:
 * - `'S'`: Start point
 * - `'E'`: End point
 * - `'#`': Wall (impassable)
 * - `' '`: Open space (passable, cost of 1)
 *
 * @param maze A list of strings representing the maze layout.
 * @return A list of [Cell] objects representing the shortest path from 'S' to 'E',
 * or `null` if no path is found.
 */
fun solveMaze(maze: List<String>): List<Cell>? {
    val graph: Graph<Cell> = AdjacencyListGraph()

    val rows = maze.size
    val cols = maze[0].length
    var start: Cell? = null
    var end: Cell? = null

    for (r in 0 until rows) {
        for (c in 0 until cols) {
            val ch = maze[r][c]
            if (ch != '#') {
                val cur = Cell(r, c)
                if (ch == 'S') start = cur
                if (ch == 'E') end = cur

                // 4-neighbors (up, down, left, right)
                val neighbors = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)
                for ((dr, dc) in neighbors) {
                    val nr = r + dr
                    val nc = c + dc
                    if (nr in 0 until rows && nc in 0 until cols && maze[nr][nc] != '#') {
                        graph.addEdge(cur, Cell(nr, nc), 1.0)
                    }
                }
            }
        }
    }

    val s = start ?: return null
    val e = end   ?: return null
    return dijkstra(graph, s, e) // uses the shared implementation from Dijkstra.kt
}

fun main() {
    val maze = listOf(
        "S   #",
        " #  #",
        " #   ",
        " #  #",
        "    E"
    )

    val path = solveMaze(maze)
    if (path != null) {
        println("Path found:")
        for (cell in path) {
            println("(${cell.row}, ${cell.col})")
        }
    } else {
        println("No path found.")
    }
}
