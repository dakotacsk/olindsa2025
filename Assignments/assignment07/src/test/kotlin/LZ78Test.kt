import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class LZ78Test {

    @Test
    fun testEncodeDecodeSimple() {
        val encoder = LZ78Encoder()
        val decoder = LZ78Decoder()

        val original = "ABABABA"
        val encoded = encoder.encode(original)
        val decoded = decoder.decode(encoded)

        assertEquals(original, decoded)
    }

    @Test
    fun testEncodeDecodeWithRepeats() {
        val encoder = LZ78Encoder()
        val decoder = LZ78Decoder()

        val original = "AAAAAABBBBBBAAAAAA"
        val encoded = encoder.encode(original)
        val decoded = decoder.decode(encoded)

        assertEquals(original, decoded)
    }

    @Test
    fun testEmptyString() {
        val encoder = LZ78Encoder()
        val decoder = LZ78Decoder()

        val original = ""
        val encoded = encoder.encode(original)

        // empty should encode to empty list
        assertTrue(encoded.isEmpty())

        val decoded = decoder.decode(encoded)
        assertEquals(original, decoded)
    }
}