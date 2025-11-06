/**
 * A single LZ78 output pair:
 * prefixIndex = index into dictionary (0 means empty string)
 * nextChar = the next literal character
 */
data class EncodedPair(
    val prefixIndex: Int,
    val nextChar: Char
)