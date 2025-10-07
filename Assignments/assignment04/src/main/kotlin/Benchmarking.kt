package main

import sort.mergeSort
import kotlin.random.Random
import kotlin.time.DurationUnit
import kotlin.time.measureTime
import java.io.File

/**
 * Benchmarks a list of sorting algorithms with various data sizes and repetition counts.
 * The results are printed to the console and saved to a CSV file.
 */
fun benchmarkSortingAlgorithms() {
    val sizes = listOf(10, 100, 1000, 10000, 100000)
    val repeats = 5

    val algorithms = listOf(
        "InsertionSort" to ::insertionSort,
        "SelectionSort" to ::selectionSort,
        "QuickSort" to ::quickSort,
        "MergeSort" to ::mergeSort,
        "BuiltInSort" to { l: MutableList<Int> -> l.sorted().toMutableList() }
    )

    val outputFile = File("results.csv")
    outputFile.writeText("algorithm,size,trial,seconds\n")

    for ((name, algo) in algorithms) {
        for (size in sizes) {
            repeat(repeats) { trial ->
                // used this instead of "val x = (1 until desiredSize).map { Random.nextInt(1000) }"
                // because I want to generate a mutable list immediately rather than immutable that
                // needs to be converted before sorting in place can happen.
                val data = MutableList(size) { Random.nextInt(1000000) }
                val copy = data.toMutableList()

                val elapsed = measureTime {
                    algo(copy)
                }.toDouble(DurationUnit.SECONDS)

                println("$name, $size, trial $trial → ${"%.6f".format(elapsed)} s")
                outputFile.appendText("$name,$size,$trial,$elapsed\n")
            }
        }
    }

    println("Benchmarking done. Results saved to results.csv")
}