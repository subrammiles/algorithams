# Algorithms & interview practice

Algorithms, data structure exercises and a senior-level interview track in **Java, Python,
JavaScript and Go**. Every interview example checks its own answers, so running a file tells you
whether a solution is right.

Companion repo for building the data structures themselves step by step:
**data_structures_tutorials**.

## Layout

Every language uses the same top-level groups:

```
<language>/
  fundamentals/     loops & patterns, recursion
  datastructures/   linked lists, stacks, queues, hashing, trees, heaps, graphs, disjoint set
  algorithms/       sorting, searching, greedy, dynamic programming, backtracking, graphs, ...
  problems/         graded practice sets (arrays level1-5, strings level1-4, classic algorithms)
  interview/        senior interview track, organised by pattern (a_... to s_...)
```

| Language | Root | Most complete in |
|---|---|---|
| Java | `java/src/` (Eclipse/VS Code project `java_ds_daa`) | everything: fundamentals, data structures, algorithms, problems, interview |
| Python | `python/` | interview track, sorting, searching |
| JavaScript | `javascript/` | interview track |
| Go | `go/` (module `algorithams`) | interview track |

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

This compiles the whole Java tree, runs every Java interview `main`, every Python and JS file, and
`go vet` + `go test` for Go. Older Java programs outside `interview/` are only compiled, because
some of them read from the keyboard.

## Practising

1. Read the header comment: problem, approach, complexity, follow-ups.
2. Delete the function body and write it again yourself.
3. Run the file. It passes or tells you which case failed.
4. Say the complexity and one follow-up out loud, then try the same problem in another language.
