# Data structures & algorithms practice

Implementations in Java (main), Python, JavaScript, C, Go and C#.

- **Senior interview track:** [java/src/interview](java/src/interview/README.md) — pattern-by-pattern problems,
  design and concurrency rounds, each file self-checking.
- Fundamentals: `java/src/a` (arrays, strings, recursion), `java/src/b` (trees, graphs),
  `java/src/c` (sorting, searching, greedy, DP, backtracking), `java/src/ds` (queues, stacks, hashing).

## Run a Java example
```
cd java
javac -d /tmp/dsa $(find src/interview -name '*.java')
java -cp /tmp/dsa interview.k_dp.B_LongestIncreasingSubsequence
```
Or open the file in VS Code / Eclipse and use "Launch Current File".

## C run command
```
gcc -o runme helloworld.c
./runme
```
