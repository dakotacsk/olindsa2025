package lpc

import kotlin.comparisons.minOf

object Dsp {

    object LinAlg {
        fun lstsq(X: Array<DoubleArray>, y: DoubleArray): DoubleArray {
            // This is a very simple implementation of least squares and may not be numerically stable.
            // For a real application, a more robust library like JAMA or ND4J should be used.
            val Xt = transpose(X)
            val XtX = multiply(Xt, X)
            val Xty = multiply(Xt, y)
            val XtXinv = invert(XtX)
            return multiply(XtXinv, Xty)
        }

        private fun transpose(A: Array<DoubleArray>): Array<DoubleArray> {
            val rows = A.size
            val cols = A[0].size
            val At = Array(cols) { DoubleArray(rows) }
            for (i in 0 until rows) {
                for (j in 0 until cols) {
                    At[j][i] = A[i][j]
                }
            }
            return At
        }

        private fun multiply(A: Array<DoubleArray>, B: Array<DoubleArray>): Array<DoubleArray> {
            val aRows = A.size
            val aCols = A[0].size
            val bRows = B.size
            val bCols = B[0].size
            if (aCols != bRows) {
                throw IllegalArgumentException("Matrix dimensions are not compatible for multiplication")
            }
            val C = Array(aRows) { DoubleArray(bCols) }
            for (i in 0 until aRows) {
                for (j in 0 until bCols) {
                    for (k in 0 until aCols) {
                        C[i][j] += A[i][k] * B[k][j]
                    }
                }
            }
            return C
        }

        private fun multiply(A: Array<DoubleArray>, v: DoubleArray): DoubleArray {
            val rows = A.size
            val cols = A[0].size
            if (cols != v.size) {
                throw IllegalArgumentException("Matrix and vector dimensions are not compatible for multiplication")
            }
            val result = DoubleArray(rows)
            for (i in 0 until rows) {
                for (j in 0 until cols) {
                    result[i] += A[i][j] * v[j]
                }
            }
            return result
        }

        private fun invert(A: Array<DoubleArray>): Array<DoubleArray> {
            val n = A.size
            val augmented = Array(n) { DoubleArray(2 * n) }
            for (i in 0 until n) {
                for (j in 0 until n) {
                    augmented[i][j] = A[i][j]
                }
                augmented[i][i + n] = 1.0
            }

            for (i in 0 until n) {
                var maxRow = i
                for (k in i + 1 until n) {
                    if (kotlin.math.abs(augmented[k][i]) > kotlin.math.abs(augmented[maxRow][i])) {
                        maxRow = k
                    }
                }
                val temp = augmented[i]
                augmented[i] = augmented[maxRow]
                augmented[maxRow] = temp

                val pivot = augmented[i][i]
                if (pivot == 0.0) {
                    throw IllegalStateException("Matrix is singular and cannot be inverted")
                }
                for (j in i until 2 * n) {
                    augmented[i][j] /= pivot
                }

                for (k in 0 until n) {
                    if (k != i) {
                        val factor = augmented[k][i]
                        for (j in i until 2 * n) {
                            augmented[k][j] -= factor * augmented[i][j]
                        }
                    }
                }
            }

            val Ainv = Array(n) { DoubleArray(n) }
            for (i in 0 until n) {
                for (j in 0 until n) {
                    Ainv[i][j] = augmented[i][j + n]
                }
            }
            return Ainv
        }
    }

    object Random {
        fun randn(n: Int): DoubleArray {
            val result = DoubleArray(n)
            for (i in 0 until n step 2) {
                val u1 = kotlin.random.Random.nextDouble()
                val u2 = kotlin.random.Random.nextDouble()
                val r = kotlin.math.sqrt(-2.0 * kotlin.math.ln(u1))
                val theta = 2.0 * kotlin.math.PI * u2
                result[i] = r * kotlin.math.cos(theta)
                if (i + 1 < n) {
                    result[i + 1] = r * kotlin.math.sin(theta)
                }
            }
            return result
        }
    }

    object Filters {
        fun lfilter(b: DoubleArray, a: DoubleArray, x: DoubleArray): DoubleArray {
            val y = DoubleArray(x.size)
            for (n in x.indices) {
                var y_n = 0.0
                for (k in b.indices) {
                    if (n - k >= 0) {
                        y_n += b[k] * x[n - k]
                    }
                }
                for (k in 1 until a.size) {
                    if (n - k >= 0) {
                        y_n -= a[k] * y[n - k]
                    }
                }
                y[n] = y_n / a[0]
            }
            return y
        }
    }

    object Metrics {
        fun compare(orig: DoubleArray, recon: DoubleArray): Pair<Double, Double> {
            val n = minOf(orig.size, recon.size)
            var mse = 0.0
            var sig = 0.0
            for (i in 0 until n) {
                val d = orig[i] - recon[i]
                mse += d * d
                sig += orig[i] * orig[i]
            }
            mse /= kotlin.math.max(1, n)
            val snr = 10.0 * kotlin.math.log10((sig + 1e-12) / (mse + 1e-12))
            return Pair(mse, snr)
        }
    }
}