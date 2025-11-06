private data class Case(val label: String, val input: String)

fun main() {
    val encoder = LZ78Encoder()
    val decoder = LZ78Decoder()

    val cases = listOf(
        Case("Short repetitive", "ABABABA"),
        Case("Highly repetitive", "AAAAAA"),
        Case("Non-repetitive", "ABCDEF"),
        Case("Mixed longer", "ABAABABAABBBBBBBBBBB")
    )

    println("Lempel–Ziv (LZ78) Compression Tests")

    for ((idx, c) in cases.withIndex()) {
        println("Test ${idx + 1}: ${c.label}")
        println("Original string: ${c.input}")

        val encoded = encoder.encode(c.input)
        println("\nEncoded pairs:")
        if (encoded.isEmpty()) {
            println("  (none)")
        } else {
            encoded.forEachIndexed { i, pair ->
                val ch = if (pair.nextChar == '\u0000') "\\u0000" else pair.nextChar.toString()
                println("  ${i + 1}. prefixIndex=${pair.prefixIndex}, nextChar='${ch}'")
            }
        }

        val decoded = decoder.decode(encoded)
        println("\nDecoded string: $decoded")

        val ok = c.input == decoded
        println("Match: $ok")

        val originalChars = c.input.length
        val pairCount = encoded.size
        val charPerPair = if (pairCount == 0) "n/a" else String.format("%.3f", originalChars.toDouble() / pairCount)

        println("\nMetrics:")
        println("  Original length (chars): $originalChars")
        println("  Encoded length (pairs):  $pairCount")
        println("  Chars per pair (rough):  $charPerPair")

        println("\n" + "-".repeat(60) + "\n")
    }

    val demo = "ABABABA"
    val demoEncoded = encoder.encode(demo)
    val demoDecoded = decoder.decode(demoEncoded)
    println("\n")
    println("Original string: $demo\n")
    println("Encoded pairs:")
    if (demoEncoded.isEmpty()) {
        println("  (none)")
    } else {
        demoEncoded.forEachIndexed { i, pair ->
            val ch = if (pair.nextChar == '\u0000') "\\u0000" else pair.nextChar.toString()
            println("  ${i + 1}. prefixIndex=${pair.prefixIndex}, nextChar='${ch}'")
        }
    }
    println("\nDecoded string: $demoDecoded\n")
    println("Original and decoded strings match: ${demo == demoDecoded}")
}