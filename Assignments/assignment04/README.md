# Assignment 4: Sorting Algorithms

This project implements and benchmarks four common sorting algorithms in Kotlin: Insertion Sort, Selection Sort, Merge Sort, and Quick Sort. It also includes Kotlin's built-in sort for comparison.

## Algorithms Implemented

-   **Insertion Sort:** stable, in-place, O(n^2) worst/avg, O(n) best
-   **Selection Sort:** unstable, in-place, O(n^2)
-   **Merge Sort:** stable, not in-place, O(n log n) time, O(n) extra space
-   **Quick Sort:** usually unstable, in-place (partitioning), avg O(n log n), worst O(n^2)
-   **Built-in Sort:** Kotlin's `sorted()` method, which is a highly optimized Timsort.

## Benchmark Method

The benchmarking process is as follows:

1.  **Data:** For each run, a list of random integers is generated. The values are in the range `[0, 1000000)`.
2.  **Sizes:** The algorithms are tested on lists of the following sizes: 10, 100, 1000, 10000, and 100000.
3.  **Repetitions:** Each algorithm is run 5 times for each list size to get a more stable average time.
4.  **Timing:** The execution time of each sort is measured using Kotlin's `measureTime` function. A new copy of the data is created for each run to ensure that the algorithms are not sorting already-sorted data. The benchmarking is single-threaded.

## Results

The following table summarizes the average execution time in seconds for each algorithm and list size.

| Algorithm     | 10       | 100      | 1,000    | 10,000   | 100,000  |
| :------------ | :------- | :------- | :------- | :------- | :------- |
| InsertionSort | 0.000045 | 0.000200 | 0.003245 | 0.057638 | 9.405549 |
| SelectionSort | 0.000041 | 0.000290 | 0.003050 | 0.068175 | 6.731301 |
| QuickSort     | 0.000038 | 0.000066 | 0.000257 | 0.001809 | 0.015386 |
| MergeSort     | 0.000096 | 0.000219 | 0.000454 | 0.002513 | 0.017689 |
| BuiltInSort   | 0.000180 | 0.000053 | 0.000428 | 0.001766 | 0.035397 |

## Discussion & Conclusions

-   **O(n^2) Algorithms (Insertion Sort, Selection Sort):** As expected, these algorithms are significantly slower on larger lists. For 100,000 elements, they take several seconds to run. Insertion sort is slightly faster than selection sort for smaller lists, but this difference diminishes as the list size increases.
-   **O(n log n) Algorithms (Quick Sort, Merge Sort):** These algorithms are much more efficient for large lists. Quick sort is the fastest of the implemented algorithms, outperforming merge sort and the built-in sort.
-   **Built-in Sort:** Kotlin's built-in sort is highly optimized and performs very well. It is interesting to note that for the largest list size, our simple Quick Sort implementation is faster. This could be due to the overhead of the Timsort algorithm for this specific data distribution (random integers).

Overall, this shows the importance of choosing the right algorithm for the job. For small lists, the difference in performance is negligible, but for large lists, an efficient algorithm like Quick Sort or Merge Sort is essential.

# Master Theorem
[Worksheet](master_theorem_pset.pdf)

# Write-up for new sorting algorithm 

[Paper](https://arxiv.org/pdf/2405.00807)

I looked at see-saw sorting, which is a “nearly optimal list labeling” system. Unfortunately, as I read, I realised it’s not exactly sorting, but I still found it fascinating. Essentially, this helps keep a sorted array up to date as we insert/delete elements. It takes advantage the tree structure, making the array a binary and recusrive tree of subproblems, like this:
```
Root -> entire array
|—- Left child -> left half of array
|__ Right child -> right half of array
```
Each node watches where new insertions go and “tilts” toward the side getting more, predicting where future insertions might go. This tilt changes how much space each side gets, so the algorithm can spread out elements before it gets crowded, the tilting is what gives the name see-saw. There’s also randomness built in, since nodes rebuild at random times so patterns can’t break it. The randomness stops an adversary (a worst-case sequence of insertions meant to slow it down) from predicting when rebalancing will happen.

Solving this problem means figuring out how to keep the array balanced and sorted while minimizing how many elements have to move each time. The see-saw algorithm does that by combining prediction (learning from past insertions) with randomness (making timing unpredictable). That makes it nearly optimal, cutting the cost from O(log²n) to about O(log n (log log n)³). I thought it was cool how it turns sorting into something dynamic and self-adjusting instead of a one-time process. 