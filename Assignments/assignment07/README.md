# Writeup: Associative Arrays and Lempel–Ziv Compression

## Overview
This project implements a custom **associative array** using a chained hash table and applies it to the **Lempel–Ziv (LZ78)** lossless compression algorithm.  
The associative array provides the dynamic dictionary that stores previously seen substrings during compression. Its O(1) average-time insert and lookup make LZ78 efficient even as the dictionary grows.

---

## Experimental Results

The program was executed on several test inputs using `Main.kt`.  
All decoded outputs exactly matched their originals, confirming correctness.

### Test 1 – Short Repetitive

Original string: ABABABA

Encoded pairs:
1.	(0,‘A’)
2. (0,‘B’)  
3. (1,‘B’) 
4. (3,‘A’)

Decoded string: ABABABA

Chars per pair (rough): 1.75

Repeating “AB” patterns allow moderate compression.

---

### Test 2 – Highly Repetitive

Original string: AAAAAA

Encoded pairs:
1.	(0,‘A’)  
2. (1,‘A’)  
3. (2,‘A’)

Decoded string: AAAAAA

Chars per pair (rough): 2.00

Uniform repetition is captured efficiently, achieving the best compression ratio among tests.

---

### Test 3 – Non-Repetitive

Original string: ABCDEF

Encoded pairs:
(0,‘A’), (0,‘B’), (0,‘C’), (0,‘D’), (0,‘E’), (0,‘F’)

Decoded string: ABCDEF

Chars per pair (rough): 1.00

With no repeated substrings, compression provides no benefit since each new character forms its own dictionary entry.

---

### Test 4 – Mixed Longer

Original string: ABAABABAABBBBBBBBBBB

Encoded pairs:
(0,‘A’), (0,‘B’), (1,‘A’), (2,‘A’), (4,‘A’),
(2,‘B’), (6,‘B’), (7,‘B’), (6,’\u0000’)

Decoded string: ABAABABAABBBBBBBBBBB

Chars per pair (rough): 2.22

This mixed input contains both recurring “ABA” and long “BBB…” segments, producing the strongest overall compression.

---

## Discussion
| Test | Input Type | Chars | Pairs | Chars / Pair | Notes |
|------|-------------|-------|-------|---------------|--------|
| 1 | Short repetitive | 7 | 4 | 1.75 | Moderate pattern reuse |
| 2 | Highly repetitive | 6 | 3 | 2.00 | High compression |
| 3 | Non-repetitive | 6 | 6 | 1.00 | No compression |
| 4 | Mixed longer | 20 | 9 | 2.22 | Strong compression on repeating blocks |

- **Associative array performance:** Constant-time dictionary access keeps compression and decompression fast.
- **Compression behavior:** The more internal repetition an input has, the fewer output pairs are produced.
- **End-of-input marker:** The `'\u0000'` character marks a final substring that ended exactly on a known phrase.
