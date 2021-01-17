package com.example.algorithms.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AlgorithmsController {
    public static String longestCommonSubsequence(char[] A, char[] B) {
        if (A == null || B == null) return null;

        int n = A.length;
        int m = B.length;

        if (n == 0 || m == 0) return null;

        int[][] dp = new int[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (A[i - 1] == B[j - 1]) dp[i][j] = dp[i - 1][j - 1] + 1;
                else dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }

        int lcsLen = dp[n][m];
        char[] lcs = new char[lcsLen];
        int index = 0;

        int i = n, j = m;
        while (i >= 1 && j >= 1) {

            int v = dp[i][j];

            while (i > 1 && dp[i - 1][j] == v) i--;
            while (j > 1 && dp[i][j - 1] == v) j--;

            if (v > 0) lcs[lcsLen - index++ - 1] = A[i - 1]; // or B[j-1];

            i--;
            j--;
        }

        return new String(lcs, 0, lcsLen);
    }
    public static String longestCommonSubsequence(String input){
        String[] splits = input.strip().split("\n", 2);
        char[] a = splits[0].toCharArray();
        char[] b = splits[1].toCharArray();
        String lcs = longestCommonSubsequence(a, b);
        int len = lcs.length();
        String res = "Length: " + len + "\n" + "LCS: " + lcs;
        return res;
    }

    public static int shortestCommonSuperset(String X, String Y) {
            int n = Y.length();
            int m = X.length();

            int[][] DP = new int[m + 1][n + 1];

            for (int i = 0; i <= m; i++) {
                for (int j = 0; j <= n; j++) {
                    if (i == 0) DP[i][j] = j;
                    else if (j == 0) DP[i][j] = i;
                    else if (X.charAt(i - 1) == Y.charAt(j - 1)) DP[i][j] = 1 + DP[i - 1][j - 1];
                    else DP[i][j] = 1 + Math.min(DP[i - 1][j], DP[i][j - 1]);
                }
            }
            return DP[m][n];
    }
    public static String shortestCommonSuperset(String input){
        String[] splits = input.strip().split("\n", 2);
        char[] a = splits[0].toCharArray();
        char[] b = splits[1].toCharArray();
        String X = new String(a);
        String Y = new String(b);

        return String.valueOf(shortestCommonSuperset(X,Y));

    }

    public static int editDistance(String a, String b) {
        int insertionCost = 1;
        int deletionCost = 1;
        int substitutionCost = 1;

        final int AL = a.length(), BL = b.length();
        int[][] arr = new int[AL + 1][BL + 1];

        for (int i = 0; i <= AL; i++) {
            for (int j = (i == 0 ? 1 : 0); j <= BL; j++) {
                int min = Integer.MAX_VALUE;

                if (i > 0 && j > 0) min = arr[i - 1][j - 1] + (a.charAt(i - 1) == b.charAt(j - 1) ? 0 : substitutionCost);
                if (i > 0) min = Math.min(min, arr[i - 1][j] + deletionCost);
                if (j > 0) min = Math.min(min, arr[i][j - 1] + insertionCost);
                arr[i][j] = min;
            }
        }

        return arr[AL][BL];
    }
    public static String editDistance(String input){
        String[] splits = input.strip().split("\n", 2);
        String a = splits[0];
        String b = splits[1];
        return String.valueOf(editDistance(a, b));
    }

    public static int longestIncreasingSubsequence(int[] ar) {

        if (ar == null || ar.length == 0) return 0;
        int n = ar.length, len = 0;

        int[] dp = new int[n];
        java.util.Arrays.fill(dp, 1);

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++)
                if (ar[i] < ar[j] && dp[j] < dp[i] + 1) dp[j] = dp[i] + 1;
            if (dp[i] > len) len = dp[i];
        }

        return len;
    }
    public static String longestIncreasingSubsequence(String input){
        Scanner reader = new Scanner(input);
        int len = reader.nextInt();
        int[] arr = new int[len];
        for(int i = 0; i<len; i++) {
            arr[i] = reader.nextInt();
        }
        return String.valueOf(longestIncreasingSubsequence(arr));
    }

    public static int matrixChainMul(int[] dims, int n) {
            int[][] m = new int[n][n];

            int i, j, k, L, q;

            for (i = 1; i < n; i++)
                m[i][i] = 0;

            for (L = 2; L < n; L++)
            {
                for (i = 1; i < n - L + 1; i++)
                {
                    j = i + L - 1;
                    if (j == n)
                        continue;
                    m[i][j] = Integer.MAX_VALUE;
                    for (k = i; k <= j - 1; k++)
                    {
                        q = m[i][k] + m[k + 1][j] + dims[i - 1] * dims[k] * dims[j];
                        if (q < m[i][j]) m[i][j] = q;
                    }
                }
            }
            return m[1][n - 1];
    }
    public static String matrixChainMul(String input){
        Scanner reader = new Scanner(input);
        int noOfMatrices = reader.nextInt();
        int[] arr = new int[noOfMatrices+1];
        for(int i = 0; i<noOfMatrices+1; i++) {
            arr[i] = reader.nextInt();
        }
        return String.valueOf(matrixChainMul(arr, noOfMatrices+1));
    }

    public static String knapsack01(int capacity, int[] W, int[] V) {
        if (W == null || V == null || W.length != V.length || capacity < 0)
            throw new IllegalArgumentException("Invalid input");

        final int N = W.length;

        int[][] DP = new int[N + 1][capacity + 1];

        for (int i = 1; i <= N; i++) {
            int w = W[i - 1], v = V[i - 1];
            for (int sz = 1; sz <= capacity; sz++) {
                DP[i][sz] = DP[i - 1][sz];
                if (sz >= w && DP[i - 1][sz - w] + v > DP[i][sz])  DP[i][sz] = DP[i - 1][sz - w] + v;
            }
        }

        int sz = capacity;
        List<Integer> itemsSelected = new ArrayList<>();

        for (int i = N; i > 0; i--) {
            if (DP[i][sz] != DP[i - 1][sz]) {
                int itemIndex = i - 1;
                itemsSelected.add(itemIndex);
                sz -= W[itemIndex];
            }
        }

        StringBuilder res = new StringBuilder(DP[N][capacity] + "\n");
        for (Integer item: itemsSelected){
            res.append(item).append(" ");
        }
        res.append("\n");

        return res.toString();
    }
    public static String knapsack01(String input){
        Scanner reader = new Scanner(input);
        int len = reader.nextInt();
        int capacity = reader.nextInt();
        int[] w = new int[len];
        int[] v = new int[len];
        for(int i = 0; i<len; i++) {
            w[i] = reader.nextInt();
            v[i] = reader.nextInt();
        }
        return knapsack01(capacity, w, v);
    }

    public static boolean partitionProblem(int[] inputArray, int n) {
        int sum = 0;
        int i, j;

        for (i = 0; i < n; i++) sum += inputArray[i];

        if (sum % 2 != 0) return false;

        boolean[][] part = new boolean[sum / 2 + 1][n + 1];

        for (i = 0; i <= n; i++) part[0][i] = true;

        for (i = 1; i <= sum / 2; i++) part[i][0] = false;

        for (i = 1; i <= sum / 2; i++) {
            for (j = 1; j <= n; j++) {
                part[i][j] = part[i][j - 1];
                if (i >= inputArray[j - 1]) part[i][j] = part[i][j] || part[i - inputArray[j - 1]][j - 1];
            }
        }

        return part[sum / 2][n];
    }
    public static String partitionProblem(String input){
        Scanner reader = new Scanner(input);
        int len = reader.nextInt();
        int[] arr = new int[len];
        for(int i = 0; i<len; i++) {
            arr[i] = reader.nextInt();
        }
        return partitionProblem(arr, len) ? "Partition not possible" : "Partition possible";
    }

    public static int rodCutting(int[] value, int length) {
            int[] solution = new int[length + 1];
            solution[0] = 0;

            for (int i = 1; i <= length; i++) {
                int max = -1;
                for (int j = 0; j < i; j++) {
                    max = Math.max(max, value[j] + solution[i - (j + 1)]);
                    solution[i] = max;
                }
            }
            return solution[length];
    }
    public static String rodCutting(String input){
        Scanner reader = new Scanner(input);
        int len = reader.nextInt();
        int[] w = new int[len];
        int[] v = new int[len];
        for(int i = 0; i<len; i++) {
            w[i] = reader.nextInt();
            v[i] = reader.nextInt();
        }
        return String.valueOf(rodCutting(v,len));
    }

    public static long coinChange(int[] S, int m, int n) {

            long[] DP =new long[n+1];
            DP[0] = 1;

            //Bottom up DP approach
            for(int i=0; i<m; i++)
                for(int j=S[i]; j<=n; j++)
                    DP[j] += DP[j-S[i]];

            return DP[n];
    }
    public static String coinChange(String input){
        Scanner reader = new Scanner(input);
        int value = reader.nextInt();
        int len = reader.nextInt();
        int[] arr = new int[len];
        for(int i = 0; i<len; i++) {
            arr[i] = reader.nextInt();
        }
        return String.valueOf(coinChange(arr, len, value));
    }

    public static boolean wordBreak(ArrayList<String> dict, String str) {
        if (str.length() == 0) {
            return true;
        }

        for (int i = 1; i <= str.length(); i++) {
            String pre = str.substring(0, i);

            if (dict.contains(pre) && wordBreak(dict, str.substring(i))) {
                return true;
            }
        }
        return false;
    }
    public static String wordBreak(String input){
        Scanner reader = new Scanner(input);
        String str = reader.next();
        ArrayList<String> list = new ArrayList<>();
        while(reader.hasNext()){
            list.add(reader.next());
        }

        return wordBreak(list, str)? "YES" : "NO";
    }
}