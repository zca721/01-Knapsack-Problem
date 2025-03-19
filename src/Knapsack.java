/* TCSS 343 - Winter 2024 */

/**
 *
 * @author Zachary C Anderson
 * @version Winter 2024
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * A class containing the solving of the 01 Knapsack problem using
 * dynamic programming and brute force methods and allows for the
 * ability to run test cases for how long it takes to compute both
 * methods depending on n for brute force and n*W for dynamic programming.
 *
 * @author Zachary C Anderson
 * @version Winter 2024
 */
public class Knapsack {

    // Constructor
    public Knapsack() {
    }

    /**
     * Dynamic Programming solution to 01 Knapsack problem.
     * Sets up a 2D array with n being the vertical indices
     * and W being the horizontal indices, then fills the
     * 2D array with the highest values and once the 2D array
     * is full, back tracking is used to return the optimal set
     * with the max value of the input values and weights.
     *
     * @param item an array of item numbers 1...n
     * @param value an array of random values for each item
     * @param weight an array of random weights for each item
     * @param W an overall weight for Knapsack
     * @param n an overall amount of items
     * @return ArrayList of optimal set
     */
    static String knapsackBottomUpDp(int[] item, int[] value, int[] weight, int W, int n) {
        // ArrayList to store optimal set
        ArrayList<Integer> maxSet = new ArrayList<Integer>();

        // 2D array
        int[][] vTable = new int[n+1][W+1];

        // Pads index 0 vertically of j with zero's
        for (int i = 0; i <= n; i++) {
            vTable[i][0] = 0;
        }

        // Pads index 0 horizontally of i with zero's
        for (int j = 0; j <= W; j++) {
            vTable[0][j] = 0;
        }

        // Builds the 2D array with max values
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= W; j++){
                if (j - weight[i-1] >= 0) {
                    vTable[i][j] = Math.max(vTable[i-1][j], value[i-1] + vTable[i-1][j-weight[i-1]]);
                } else {
                    vTable[i][j] = vTable[i-1][j];
                }
            }
        }

        // Bug testers
//        System.out.println(vTable[n][W]);

        // Back tracking for optimal set
        while (vTable[n][W] > 0) {
            if (vTable[n][W] > vTable[n-1][W]) {
                maxSet.add(n);
                W = W - weight[n-1];
            }
            n = n - 1;
        }

        // Bug testers
//        System.out.println(Arrays.deepToString(vTable));
//        System.out.println(maxSet.toString());

        return maxSet.toString();
    }

    /**
     * Brute Force solution to 01 Knapsack problem.
     * Creates every possible subset of the original set of items
     * using bitwise manipulation and stores the highest value and
     * the subset attached to said value and then returns that
     * optimal set.
     *
     * @param item an array of item numbers 1...n
     * @param value an array of random values for each item
     * @param weight an array of random weights for each item
     * @param W an overall weight for Knapsack
     * @param n an overall amount of items
     * @return an Array of optimal set stored at index 0 of a List inside another List
     */
    static List<Integer> knapsackBruteForce(int[] item, int[] value, int[] weight, int W, int n) {
        // List of Lists to store optimal set of item Array
        List<List<Integer>> maxSet = new ArrayList<>();

        // Array of weights
        int[] weightSet = new int[n];

        // Array of values
        int[] valueSet = new int[n];

        // Integer to store max value of the weights in each subset
        int maxValue = 0;

        // Integer to store the sum of the weights in each subset
        int sumWeight;

        // Integer to store the sum of the values in each subset
        int sumValue;

        // Throws exception when values of n are greater than 30
        if (n > 30)
            throw new RuntimeException("Value of greater than 30");

        // Creates every subset of item, weight, and value and finds the optimal set with this information
        for (int i = 0; i < (1 << n); i++) {        // (Bit Manipulation <<) Shifts bit pattern to the left for all subsets
            List<Integer> sets = new ArrayList<>();
            sumWeight = 0;
            sumValue = 0;
            for (int j = 0; j < n; j++) {
                if((i & (1 << j)) != 0){            // Checks if the jth bit is in current subset
                    weightSet[j] = weight[j];
                    sumWeight = sumWeight + weightSet[j];
                    sets.add(item[j]);

                    // Bug testers
//                    System.out.print(weightSet[j] + " ");

                    if (sumWeight <= W) {
                        valueSet[j] = value[j];
                        sumValue = sumValue + valueSet[j];

                        // Bug testers
//                        System.out.print(valueSet[j] + " ");

                        if (sumValue > maxValue) {
                            maxSet.clear();
                            maxSet.add(sets);
                            maxValue = sumValue;

                            // Bug testers
//                            System.out.println(Arrays.toString(weightSet));
//                            System.out.println(Arrays.toString(valueSet));

                        }
                    }
                }
            }

            // Bug testers
//            System.out.print("weight total = " + sumWeight);
//            System.out.print(" value total = " + sumValue);
//            System.out.println();

        }

        // Bug testers
//        System.out.println(maxSet.get(0));
//        System.out.println(maxValue);

        return maxSet.get(0);
    }

    /**
     * Given an integer W, produce an array of W random integers.
     * The integers of the array are between 1 and W (inclusive) with
     * random uniform distribution.
     *
     * @param W the number of elements in the returned array
     * @return an Array of random integers
     */
    public static int[] getRandomArrayOfIntegers(int W) {
        int[] items = new int[W];
        for (int i = 0; i < W; i++) {
            items[i] = (int) (W * Math.random() + 1);
        }
        return items;
    }

    /**
     * Perform timing experiments.
     */
    private static void testTiming () {
        // timer variables
        long totalTime = 0;
        long startTime = 0;
        long finishTime = 0;
        int count = 1;

        // start the timer
        Date startDate = new Date();
        startTime = startDate.getTime();

        int n = 25;
        int W = 250000;
        int[] items = new int[n];
        for (int i = 0; i < n; i++) {
            items[i] = count;
            count++;
        }
        int[] values = getRandomArrayOfIntegers(W);
        int[] weights = getRandomArrayOfIntegers(W);

        // Bug testers
//        System.out.println(Arrays.toString(items));

//        knapsackBottomUpDp(items, values, weights, W, n);
        knapsackBruteForce(items, values, weights, W, n);

        // stop the timer
        Date finishDate = new Date();
        finishTime = finishDate.getTime();
        totalTime += (finishTime - startTime);

//        System.out.println("**** Results for Dynamic Programming ****");
        System.out.println("**** Results for Brute Force ****");
//        System.out.println("Dynamic Programming optimal set: " + knapsackBottomUpDp(items, values, weights, W, n));
        System.out.println("Brute Force optimal set: " + knapsackBruteForce(items, values, weights, W, n));
        System.out.println("     ****  " + "Time: " + totalTime + " ms.  ****");
    }

    /**
     * Testing of the dynamic programming and brute force methods
     *
     * @param args main
     */
    public static void main(String[] args) {
        // Call to testTiming method
        testTiming();

        // Initial setup for creating dynamic programming and brute force methods of 01 Knapsack
//        int[] items = new int[] {1, 2, 3, 4, 5};
//        int[] values = new int[] {14, 17, 3, 15, 12};
//        int[] weights = new int[] {4, 5, 1, 4, 3};
//        int W = 9;
//        int n = 5;
//        System.out.println("Dynamic Programming: " + knapsackBottomUpDp(items, values, weights, W, n));
//        System.out.println("Brute Force: " + knapsackBruteForce(items, values, weights, W, n));
    }
}