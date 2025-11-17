## Results Table

### k = 2
| n | Build Time | KD Query | Brute Query |
|---|------------|----------|-------------|
| 10 | 2.134584 ms | 994.334 µs | 1.090750 ms |
| 100 | 332.958 µs | 388.625 µs | 2.192958 ms |
| 1000 | 1.337792 ms | 368.709 µs | 5.219916 ms |
| 10000 | 8.406333 ms | 691.208 µs | 30.726959 ms |

### k = 4
| n | Build Time | KD Query | Brute Query |
|---|------------|----------|-------------|
| 10 | 34.667 µs | 220.416 µs | 108.125 µs |
| 100 | 26.833 µs | 800.166 µs | 510.25 µs |
| 1000 | 309.708 µs | 1.369291 ms | 4.508791 ms |
| 10000 | 2.792708 ms | 2.008333 ms | 44.926875 ms |

### k = 8
| n | Build Time | KD Query | Brute Query |
|---|------------|----------|-------------|
| 10 | 7 µs | 303.125 µs | 140.875 µs |
| 100 | 17.084 µs | 2.426459 ms | 839.25 µs |
| 1000 | 224.167 µs | 12.798459 ms | 7.746792 ms |
| 10000 | 2.565958 ms | 24.524042 ms | 80.651334 ms |

### k = 16
| n | Build Time | KD Query | Brute Query |
|---|------------|----------|-------------|
| 10 | 9.834 µs | 514.917 µs | 246.416 µs |
| 100 | 28.166 µs | 4.724458 ms | 1.351666 ms |
| 1000 | 312.75 µs | 51.931541 ms | 13.848667 ms |
| 10000 | 3.015583 ms | 433.114208 ms | 172.745917 ms |

### Interpretation
- k = 2 runs exactly how you expect. KD-tree is consistently faster than brute force, and the gap gets bigger as n grows.
- k = 4 still favors the KD-tree for larger datasets, but the advantage starts to shrink.
- k = 8 is where things flip. KD-tree pruning stops being effective and brute force often wins for small and mid-sized n.
- k = 16 fully hits the curse of dimensionality. KD-tree becomes much slower than brute force across the board.

Overall, KD-trees work well in low dimensions, start breaking down around k = 8, and become inefficient by k = 16.