package org.example

/**
 * Contains functions for performing sequence alignment using dynamic programming.
 */
object SequenceAlignment {

    /**
     * Aligns two sequences using the Needleman-Wunsch algorithm.
     *
     * @param seq1 The first sequence.
     * @param seq2 The second sequence.
     * @param matchScore The score for a match.
     * @param mismatchScore The score for a mismatch.
     * @param gapPenalty The penalty for a gap.
     * @return A pair containing the alignment score and the aligned sequences.
     */
    fun needlemanWunsch(
        seq1: String,
        seq2: String,
        matchScore: Int = 1,
        mismatchScore: Int = -1,
        gapPenalty: Int = -2
    ): Pair<Int, Pair<String, String>> {
        val n = seq1.length
        val m = seq2.length

        val score = Array(n + 1) { IntArray(m + 1) }

        // Initialize score matrix
        for (i in 0..n) score[i][0] = gapPenalty * i
        for (j in 0..m) score[0][j] = gapPenalty * j

        // Fill score matrix
        for (i in 1..n) {
            for (j in 1..m) {
                val match = score[i - 1][j - 1] + if (seq1[i - 1] == seq2[j - 1]) matchScore else mismatchScore
                val delete = score[i - 1][j] + gapPenalty
                val insert = score[i][j - 1] + gapPenalty
                score[i][j] = maxOf(match, delete, insert)
            }
        }

        // Backtrace to find alignment
        var align1 = ""
        var align2 = ""
        var i = n
        var j = m

        while (i > 0 || j > 0) {
            if (i > 0 && j > 0 && score[i][j] == score[i - 1][j - 1] + if (seq1[i - 1] == seq2[j - 1]) matchScore else mismatchScore) {
                align1 = seq1[i - 1] + align1
                align2 = seq2[j - 1] + align2
                i--
                j--
            } else if (i > 0 && score[i][j] == score[i - 1][j] + gapPenalty) {
                align1 = seq1[i - 1] + align1
                align2 = "-" + align2
                i--
            } else {
                align1 = "-" + align1
                align2 = seq2[j - 1] + align2
                j--
            }
        }

        return Pair(score[n][m], Pair(align1, align2))
    }
}
