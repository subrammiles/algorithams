# Algorithms & interview practice

Algorithms, data structure exercises and a senior-level interview track in **Java, Python,
JavaScript and Go**. Every new example checks its own answers, so running a file tells you
whether a solution is right.

Two problem sets exist in all four languages:

- **Problem bank:** 42 topics, about 160 classic interview problems, grouped by technique. See below.
- **Interview track:** 19 patterns and 53 problems, plus design and concurrency rounds.

Companion repo for building the data structures themselves step by step:
**data_structures_tutorials**.

## Layout

Every language uses the same top-level groups:

```
<language>/
  fundamentals/     recursion (+ loops & patterns in Java)
  datastructures/   linked lists, stacks, queues, hashing, trees, heaps, graphs, disjoint set (Java)
  algorithms/       dynamic programming, greedy, graphs, trees, backtracking, searching, sorting, divide & conquer
  problems/         data structure problem sets (stack/queue, hashing, linked list, heap, matrix)
                    + graded arrays/strings sets (Java)
  interview/        senior interview track, organised by pattern (a_... to s_...)
```

| Language | Root | Most complete in |
|---|---|---|
| Java | `java/src/` (Eclipse/VS Code project `java_ds_daa`) | everything, plus the older single-topic programs |
| Python | `python/` | problem bank + interview track |
| JavaScript | `javascript/` (shared heap in `javascript/lib/heap.js`) | problem bank + interview track |
| Go | `go/` (module `algorithams`) | problem bank + interview track |

## Problem bank (all four languages)

The Java files have the longest explanations, including why each approach works, its complexity, and the
traps interviewers look for. File letters give the suggested order.

| Folder | Files | What you practise |
|---|---|---|
| `fundamentals/recursion` | A recursion basics, B classic recursion | base cases, memoisation, fast power, Hanoi, Josephus, phone letters, parentheses |
| `algorithms/divide_and_conquer` | A divide and conquer | inversion count, max subarray, majority element |
| `algorithms/dynamic_programming` ([guide](java/src/algorithms/dynamic_programming/README.md)) | A stairs/robber, B grid, C knapsack, D string, E subarray/subsequence, F **stock**, G interval/partition, H counting | the 8 DP families interviewers draw from |
| `algorithms/greedy` | A intervals, B Huffman, C array greedy, D job sequencing, E string greedy | when greedy is provably safe (and when it isn't) |
| `algorithms/graphs` | A traversal problems, B MST, C shortest-path variants, D multi-source BFS, E Euler path, F articulation points + Tarjan SCC, G weighted union-find, H **max flow + matching** | 0-1 BFS, A*, Hierholzer, low-link, Edmonds-Karp |
| `algorithms/trees` | A traversal variants, B construct trees, C path problems, D BST problems, E properties, F tree DP + binary lifting | zigzag/vertical/boundary, pre+in build, prefix sums on paths, LCA queries |
| `algorithms/backtracking` | A N-Queens, B grid/string, C combination variants | pruning, bitmasks, duplicate handling |
| `algorithms/searching` | A binary search variants | one template for lower/upper bound, peak, sqrt, ternary search |
| `algorithms/sorting` | A comparison sorts, B non-comparison sorts | all 8 comparison sorts + counting/radix/bucket |
| `problems/stack_queue` | A stack problems, B queue problems | RPN, calculator, decode string, asteroids, moving average |
| `problems/hashing` | A hashing problems | two sum, anagrams, longest consecutive, 4Sum II |
| `problems/linked_list` | A linked list problems | merge, remove nth, palindrome, add numbers, intersection, reorder, sort |
| `problems/heap` | A heap problems | k closest, kth largest stream, ugly numbers |
| `problems/matrix` | A matrix problems | set zeroes O(1), staircase search, Game of Life in place |

Names follow each language's style: `F_StockProblems.java` → `f_stock_problems.py` / `.js` / `.go` (+ `_test.go`).

## Interview track

19 patterns and 53 problems, implemented the same way in all four languages. The roadmap, with what each
pattern is for and what changes at senior level, is in
[java/src/interview/README.md](java/src/interview/README.md).

The same problem has the same folder in every language, with file names following each language's style:

| | Java | Python | JavaScript | Go |
|---|---|---|---|---|
| Path | `java/src/interview/d_heaps/C_MedianFromDataStream.java` | `python/interview/d_heaps/c_median_from_data_stream.py` | `javascript/interview/d_heaps/c_median_from_data_stream.js` | `go/interview/d_heaps/c_median_from_data_stream.go` (+ `_test.go`) |
| Checks | `Check.eq(...)` in `main` | `assert` in `__main__` | `node:assert` | `go test` |

| # | Pattern | # | Pattern |
|---|---|---|---|
| a | Two pointers / sliding window / prefix sums | k | Dynamic programming |
| b | Binary search (incl. on the answer) | l | Backtracking |
| c | Monotonic stack | m | Bit manipulation |
| d | Heaps / top-k / k-way merge | n | Range queries (Fenwick, lazy segment tree) |
| e | Intervals | o | String matching (KMP, Z) |
| f | Linked lists | p | **Design**: LRU, LFU, rate limiter, consistent hashing, ... |
| g | Trees | q | **Concurrency**: blocking queue, ordered threads, parallel sort |
| h | Graphs (topo sort, Dijkstra, Bellman-Ford, bridges, SCC) | r | Greedy |
| i | Union-find | s | Math, matrix, randomized |
| j | Tries | | |

Each language uses its own concurrency model for section `q`. Java uses locks/conditions and
Fork/Join. Python uses `threading.Condition`, plus processes for CPU-bound work because of the GIL.
JavaScript uses an async queue, async semaphores and `worker_threads`. Go uses goroutines,
channels and `sync.Cond`.

## Running

```bash
# Java: compile once, run any class
cd java && javac -d /tmp/dsa $(find src -name '*.java')
java -cp /tmp/dsa interview.k_dp.B_LongestIncreasingSubsequence

# Python / JavaScript: run any file directly
python3 python/interview/k_dp/b_longest_increasing_subsequence.py
node javascript/interview/k_dp/b_longest_increasing_subsequence.js

# Go: test a package, or everything
cd go && go test ./interview/k_dp/
cd go && go test ./...
```

In VS Code / Eclipse you can also open a Java file and use **Launch Current File**.

### Check everything

```bash
scripts/check_all.sh              # all languages
scripts/check_all.sh python go    # just some
```

This compiles the whole Java tree and runs every self-checking Java `main` (the ones that use
`common.Check`). It also runs every Python and JS file, and `go vet` + `go test` for Go. Older Java
programs are only compiled, because some of them read from the keyboard.

## Practising

1. Read the header comment: problem, approach, complexity, follow-ups.
2. Delete the function body and write it again yourself.
3. Run the file. It passes or tells you which case failed.
4. Say the complexity and one follow-up out loud, then try the same problem in another language.
