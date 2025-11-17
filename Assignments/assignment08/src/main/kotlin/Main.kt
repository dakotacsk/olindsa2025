/**
 * Runs a series of experiments to compare the performance of the KD-Tree and brute force nearest neighbor search.
 */
fun main() {
    println("KD-Tree Print")
    val demoPoints = listOf(
        doubleArrayOf(2.0, 3.0),
        doubleArrayOf(5.0, 4.0),
        doubleArrayOf(9.0, 6.0),
        doubleArrayOf(4.0, 7.0),
        doubleArrayOf(8.0, 1.0),
        doubleArrayOf(7.0, 2.0)
    )
    val demoTree = KDTree(demoPoints)
    demoTree.printTree()

    val ks = listOf(2, 4, 8, 16)
    val ns = listOf(10, 100, 1000, 10_000)

    for (k in ks) {
        for (n in ns) {
            val (build, kd, brute) = runExperiment(k, n)

            println("k=$k  n=$n")
            println("  Build: $build")
            println("  KD Query: $kd")
            println("  Brute Query: $brute")
            println()
        }
    }
}