# Dynamic programming problem bank

The method (state → transition → base case → answer → complexity) is explained in
[interview/k_dp/README.md](../../interview/k_dp/README.md). This folder holds the classic
problem families interviewers draw from; the interview track holds the "hard" representatives
(LIS, regex matching, burst balloons, bitmask TSP).

| File | Family | Problems |
|---|---|---|
| A_ClimbingStairsFamily | 1D, "last step" | climbing stairs, min cost stairs, house robber II (circular), tribonacci, delete and earn |
| B_GridDP | 2D grid paths | unique paths (+ obstacles), minimum path sum, maximal square |
| C_KnapsackFamily | knapsack | 0/1 knapsack (2D and 1D), unbounded knapsack, rod cutting, target sum, min subset difference |
| D_StringDP | two strings / one string | edit distance, longest palindromic subsequence/substring, word break, distinct subsequences, interleaving |
| E_SubarrayAndSubsequence | ending-at-i state | Kadane, max product subarray, longest common substring, max-sum increasing subsequence, longest bitonic |
| F_StockProblems | state machine DP | buy/sell once, unlimited, two transactions, k transactions, cooldown, fee |
| G_IntervalAndPartitionDP | interval / partition | matrix chain multiplication, palindrome partitioning min cuts, egg drop |
| H_CountingDP | counting | unique BSTs (Catalan), palindromic substrings, dice rolls to target |

Older single-topic programs (FibonacciTopDown/BottomUp, Knapsack*) are kept alongside.
