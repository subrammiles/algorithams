# Senior-level interview track

Pattern-first practice set. Every file is self-contained: problem statement, the approach,
complexity, **senior follow-ups** (what an interviewer asks next), and a `main()` that checks the
answers using [`Check`](../common/Check.java). Run a file — it prints `PASS …` lines, or throws on a wrong answer.

**How to practise:** read the problem comment, delete the method body, re-implement it, run `main()`.
Say the complexity and at least one follow-up out loud.

**Other languages:** the same problems live in the same folders under
[`python/interview`](../../../python/interview), [`javascript/interview`](../../../javascript/interview)
and [`go/interview`](../../../go/interview), with snake_case file names
(`C_MedianFromDataStream.java` → `c_median_from_data_stream.py` / `.js` / `.go`). The links below point
to the Java versions, which have the longest explanations.

## Coding patterns

| # | Pattern | Recognise it when… | Files |
|---|---|---|---|
| a | Two pointers / sliding window | contiguous subarray/substring, sorted pairs | [3Sum](a_twopointers_slidingwindow/A_ThreeSum.java), [Trapping Rain Water](a_twopointers_slidingwindow/B_TrappingRainWater.java), [Min Window Substring](a_twopointers_slidingwindow/C_MinimumWindowSubstring.java), [Sliding Window Max](a_twopointers_slidingwindow/D_SlidingWindowMaximum.java), [Subarray Sum = K](a_twopointers_slidingwindow/E_SubarraySumEqualsK.java), [Char Replacement](a_twopointers_slidingwindow/F_LongestSubstringWithAtMostKReplacements.java) |
| b | Binary search | sorted / rotated data, "minimise the maximum" | [Rotated Array](b_binarysearch/A_SearchInRotatedSortedArray.java), [Search on Answer](b_binarysearch/B_BinarySearchOnAnswer.java), [Median of 2 Arrays](b_binarysearch/C_MedianOfTwoSortedArrays.java) |
| c | Monotonic stack | next greater/smaller, spans, histograms | [Next Greater / Temps / Span](c_monotonicstack/A_NextGreaterAndDailyTemperatures.java), [Largest Rectangle](c_monotonicstack/B_LargestRectangleInHistogram.java) |
| d | Heaps | top-k, k-way merge, running median | [Top K / Quickselect](d_heaps/A_TopKFrequent.java), [Merge K Lists](d_heaps/B_MergeKSortedLists.java), [Median Stream](d_heaps/C_MedianFromDataStream.java) |
| e | Intervals | overlapping ranges, scheduling | [Merge/Insert](e_intervals/A_MergeAndInsertIntervals.java), [Meeting Rooms](e_intervals/B_MeetingRooms.java) |
| f | Linked list | in-place pointer surgery | [Reverse K Group](f_linkedlist/A_ReverseNodesInKGroup.java), [Floyd Cycle](f_linkedlist/B_FloydCycleDetection.java), [Copy Random List](f_linkedlist/C_CopyListWithRandomPointer.java) |
| g | Trees | recursion returning one value, recording another | [Serialize](g_trees/A_SerializeDeserializeBinaryTree.java), [LCA](g_trees/B_LowestCommonAncestor.java), [Max Path Sum](g_trees/C_BinaryTreeMaxPathSum.java), [Validate BST / Morris](g_trees/D_ValidateBSTAndIterativeTraversal.java) |
| h | Graphs | grids, dependencies, shortest paths | [Islands / Rotting](h_graphs/A_NumberOfIslands.java), [Topo Sort](h_graphs/B_TopologicalSortCourseSchedule.java), [Alien Dictionary](h_graphs/C_AlienDictionary.java), [Dijkstra / Bellman-Ford](h_graphs/D_ShortestPaths.java), [Word Ladder](h_graphs/E_WordLadder.java), [Bridges / SCC](h_graphs/F_CriticalConnectionsAndSCC.java) |
| i | Union-find | dynamic connectivity | [DSU problems](i_unionfind/A_UnionFindProblems.java) |
| j | Trie | prefixes, many-word search | [Trie / Autocomplete](j_trie/A_TrieAndAutocomplete.java), [Word Search II](j_trie/B_WordSearchII.java) |
| k | Dynamic programming | "count ways", "min/max", overlapping subproblems — see [DP guide](k_dp/README.md) | [1D DP](k_dp/A_OneDimensionalDP.java), [LIS](k_dp/B_LongestIncreasingSubsequence.java), [LCS / Regex](k_dp/C_TwoStringDP.java), [Knapsack / Interval / Bitmask](k_dp/D_KnapsackIntervalBitmaskDP.java) |
| l | Backtracking | "all combinations", constraint search | [Subsets / Perms / Combos](l_backtracking/A_SubsetsPermutationsCombinations.java), [Sudoku](l_backtracking/B_SudokuSolver.java) |
| m | Bit manipulation | XOR tricks, masks | [Bit tricks](m_bits/A_BitManipulation.java) |
| n | Range queries | many updates + range sums | [Fenwick](n_rangequeries/A_FenwickTreeCountSmaller.java), [Segment Tree (lazy)](n_rangequeries/B_SegmentTreeLazy.java) |
| o | String matching | pattern search in O(n + m) | [KMP / Z](o_strings/A_KMPAndZAlgorithm.java) |
| r | Greedy | provable local choice | [Jump / Gas / Scheduler / Candy](r_greedy/A_GreedyClassics.java) |
| s | Math / matrix / random | number theory, in-place matrix, sampling | [Math & Matrix](s_math/A_MathMatrixRandomized.java) |

## Senior-only rounds

| # | Topic | Files |
|---|---|---|
| p | Low-level / object design | [LRU](p_design/A_LRUCache.java), [LFU](p_design/B_LFUCache.java), [Rate Limiters](p_design/C_RateLimiters.java), [Consistent Hashing](p_design/D_ConsistentHashing.java), [O(1) Random Set](p_design/E_InsertDeleteGetRandom.java), [Time-based KV](p_design/F_TimeBasedKeyValueStore.java) |
| q | Concurrency | [Blocking Queue](q_concurrency/A_BoundedBlockingQueue.java), [Ordered Threads](q_concurrency/B_OrderedPrintingThreads.java), [Fork/Join Sort](q_concurrency/C_ParallelMergeSortForkJoin.java) |

## What changes at senior level

- **Clarify first**: input size, duplicates, negatives, empty input, memory limits, streaming or not.
- **Offer two solutions**: brute force → optimised, with complexity for each, then code the better one.
- **Test out loud**: walk through one normal case and the edge cases before saying "done".
- **Scale it**: what if the data doesn't fit in memory (external sort, sharding), many concurrent callers
  (locking, lock striping), or many machines (consistent hashing, map-reduce top-k)?
- **Trade-offs over tricks**: say why you picked a TreeMap over a heap, BFS over DFS, etc.

## Related material in this repo

- Fundamentals: `fundamentals/` (loops & patterns, recursion)
- Data structures: `datastructures/` (linked lists, stacks, queues, hashing, trees, heaps, graphs, disjoint set)
- Algorithms: `algorithms/` (sorting, searching, greedy, dynamic programming, backtracking, graphs, divide & conquer)
- Graded practice: `problems/arrays/level1..5`, `problems/strings/level1..4` and their `classic/` algorithms

See the [repo README](../../../README.md) for the full layout and how to run each language.
