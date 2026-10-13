# Dynamic programming – how to approach it in an interview

1. **State**: what do you need to know to answer a sub-problem? (`dp[i]`, `dp[i][j]`, `dp[mask][i]`)
2. **Transition**: how does a state depend on smaller states?
3. **Base cases** and the **answer** location.
4. Start with recursion + memo (top-down), then convert to a table (bottom-up), then compress space.
5. Say the complexity: (number of states) × (work per state).

| Family | Typical state | Examples here |
|---|---|---|
| 1D linear | `dp[i]` = best for prefix i | House Robber, Coin Change, Decode Ways |
| Subsequence | `dp[i]` or patience sorting | LIS |
| Two strings | `dp[i][j]` over prefixes | LCS, Edit Distance (a/strings/s4), Regex Matching |
| Knapsack | `dp[capacity]` | Partition Equal Subset Sum, Coin Change |
| Interval | `dp[l][r]`, fill by length | Burst Balloons |
| Bitmask | `dp[mask][last]` | TSP on ≤ 20 nodes |
| Grid | `dp[r][c]` | Unique paths, min path sum |
