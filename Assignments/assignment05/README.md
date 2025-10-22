# Assignment 5 — Divide & Conquer & Dynamic Programming

## Matrix Multiplication

### Benchmark (ms)

| Size | Standard | Pure Strassen | Hybrid Strassen |
| ---- | -------- | ------------- | --------------- |
| 32   | 2        | 11            | 2               |
| 64   | 2        | 24            | 1               |
| 128  | 14       | 120           | 8               |
| 256  | 14       | 563           | 11              |
| 512  | 155      | 2996          | 70              |

**Analysis:**
Strassen’s algorithm is asymptotically faster (O(n^{2.81})) but incurs high recursion overhead. The **hybrid version** switches to standard multiplication for small matrices, achieving the best performance. Optimal threshold ≈ **256–512**.

---

## Sequence Alignment (Needleman–Wunsch)

**Score:** 89

**Alignment:**

```
Target: ATGGTGCATCTGACTCCTGAGGAGAAGTCTGCCGTTACTGCCCTGTGGGGCAAGGTGAACGTGGATGAAGTTGGTGGTGAGGCCCTGGGCAGG
Test:   ATGGTGCATCTGACTCCTGTTGAGAAGTCTGCCGTTACTGCCCTGTGGGGCAAGGTGAACGTGGATGAAGTTGGTGGTGAGGCCCTGGGCAGG
```

The alignment shows a single mismatch region, confirming the algorithm’s correct global alignment behavior.
