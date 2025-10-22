package org.example

import kotlin.system.measureTimeMillis

fun main() {
    println("--- Matrix Multiplication Benchmark ---")
    benchmarkMatrixMultiplication()

    println("\n--- Sequence Alignment ---")
    alignDnaSequences()
}

fun benchmarkMatrixMultiplication() {
    val sizes = listOf(32, 64, 128, 256, 512) // Powers of 2 for Strassen's

    println("Size | Standard Time (ms) | Pure Strassen Time (ms) | Hybrid Strassen Time (ms)")
    println("-----|----------------------|-------------------------|---------------------------")

    for (size in sizes) {
        val matrixA = Matrix(size).apply {
            for (i in 0 until size) for (j in 0 until size) this[i, j] = (i + j).toDouble()
        }
        val matrixB = Matrix(size).apply {
            for (i in 0 until size) for (j in 0 until size) this[i, j] = (i - j).toDouble()
        }

        val standardTime = measureTimeMillis {
            matrixA.multiply(matrixB)
        }

        val pureStrassenTime = measureTimeMillis {
            matrixA.strassenMultiply(matrixB)
        }

        val hybridStrassenTime = measureTimeMillis {
            matrixA.hybridStrassenMultiply(matrixB)
        }

        println("%4d | %20d | %23d | %25d".format(size, standardTime, pureStrassenTime, hybridStrassenTime))
    }
}

fun alignDnaSequences() {
    println("Aligning targetGenome against testAgainst using Needleman-Wunsch:")
    val (score, alignment) = SequenceAlignment.needlemanWunsch(Dna.targetGenome, Dna.testAgainst)
    val (alignedTarget, alignedTest) = alignment

    println("Score: $score")
    println("Alignment:")
    println("Target: $alignedTarget")
    println("Test:   $alignedTest")
}
