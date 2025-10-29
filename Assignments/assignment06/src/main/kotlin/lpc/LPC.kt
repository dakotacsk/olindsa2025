package lpc

import kotlin.math.floor
import kotlin.math.sqrt
import kotlin.random.Random

object Lpc {

    data class LpcModel(val A: Array<DoubleArray>, val G: DoubleArray)

    fun lpc_encode(x: DoubleArray, p: Int, w: DoubleArray): LpcModel {
        val B = create_overlapping_blocks(x, w)
        val nb = B.size
        val nw = B[0].size

        val A = Array(p) { DoubleArray(nb) }
        val G = DoubleArray(nb)

        for (i in 0 until nb) {
            val (a, g) = solve_lpc(B[i], p)
            for (j in 0 until p) {
                A[j][i] = a[j]
            }
            G[i] = g
        }

        return LpcModel(A, G)
    }

    fun lpc_decode(A: Array<DoubleArray>, G: DoubleArray, w: DoubleArray): DoubleArray {
        val p = A.size
        val nb = G.size
        val nw = w.size

        val B_hat = Array(nb) { DoubleArray(nw) }

        for (i in 0 until nb) {
            val a = DoubleArray(p)
            for (j in 0 until p) {
                a[j] = A[j][i]
            }
            B_hat[i] = run_source_filter(a, G[i], nw)
        }

        return add_overlapping_blocks(B_hat, w)
    }

    private fun create_overlapping_blocks(x: DoubleArray, w: DoubleArray, R: Double = 0.5): Array<DoubleArray> {
        val n = x.size
        val nw = w.size
        val step = floor(nw * (1 - R)).toInt()
        val nb = floor((n - nw).toDouble() / step).toInt() + 1

        val B = Array(nb) { DoubleArray(nw) }

        for (i in 0 until nb) {
            val offset = i * step
            for (j in 0 until nw) {
                B[i][j] = w[j] * x[offset + j]
            }
        }

        return B
    }

    private fun add_overlapping_blocks(B: Array<DoubleArray>, w: DoubleArray, R: Double = 0.5): DoubleArray {
        val nb = B.size
        val nw = B[0].size
        val step = floor(nw * (1 - R)).toInt()

        val n = (nb - 1) * step + nw

        val x = DoubleArray(n)

        for (i in 0 until nb) {
            val offset = i * step
            for (j in 0 until nw) {
                x[offset + j] += B[i][j]
            }
        }

        return x
    }

    private fun solve_lpc(x: DoubleArray, p: Int): Pair<DoubleArray, Double> {
        val n = x.size
        val b = x.sliceArray(1 until n)

        val X = make_matrix_X(x, p)

        val a = Dsp.LinAlg.lstsq(X, b)

        val e = DoubleArray(n - 1)
        for (i in 0 until n - 1) {
            var pred = 0.0
            for (j in 0 until p) {
                pred += X[i][j] * a[j]
            }
            e[i] = b[i] - pred
        }
        val g = e.map { it * it }.average()

        return Pair(a, g)
    }

    private fun make_matrix_X(x: DoubleArray, p: Int): Array<DoubleArray> {
        val n = x.size
        val xz = x.reversed().toDoubleArray().plus(DoubleArray(p))

        val X = Array(n - 1) { DoubleArray(p) }
        for (i in 0 until n - 1) {
            val offset = n - 1 - i
            X[i] = xz.slice(offset until offset + p).toDoubleArray()
        }
        return X
    }

    private fun run_source_filter(a: DoubleArray, g: Double, block_size: Int): DoubleArray {
        val src = Dsp.Random.randn(block_size).map { it * sqrt(g) }.toDoubleArray()

        val b_filter = doubleArrayOf(1.0)
        val a_filter = doubleArrayOf(1.0).plus(a.map { -it }.toDoubleArray())

        return Dsp.Filters.lfilter(b_filter, a_filter, src)
    }
}
