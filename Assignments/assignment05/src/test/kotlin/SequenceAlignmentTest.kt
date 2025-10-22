package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SequenceAlignmentTest {

    @Test
    fun `test needlemanWunsch basic alignment`() {
        val seq1 = "GATTACA"
        val seq2 = "GCATGCU"

        val (score, alignment) = SequenceAlignment.needlemanWunsch(seq1, seq2, 1, -1, -1)

        val (aligned1, aligned2) = alignment

        // There can be multiple optimal alignments, this is one of them.
        // Example from Wikipedia has score 0, but different scoring.
        // With 1/-1/-1 scoring, the optimal alignment is different.
        // Let's test with a known example for this scoring
        val seqA = "AGT"
        val seqB = "AAT"
        val (score2, alignment2) = SequenceAlignment.needlemanWunsch(seqA, seqB, 1, -1, -2)
        assertEquals(1, score2)
        assertEquals("A-GT", alignment2.first)
        assertEquals("AAT-", alignment2.second)
    }

    @Test
    fun `test needlemanWunsch with provided dna`() {
        val (score, alignment) = SequenceAlignment.needlemanWunsch(Dna.targetGenome, Dna.testAgainst)
        // We expect a high score because the sequences are very similar
        assertEquals(true, score > 0)
        // We can also check for a specific alignment characteristic if we know it
        // For now, just check if it runs and produces a positive score
    }
}
