import java.util.Comparator;
import java.util.Random;
import java.util.LinkedList;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Rahul
 * @version 1.0
 * @userid nrahul3 (i.e. gburdell3)
 * @GTID 903533392 (i.e. 900000000)
 *
 * Collaborators: I did this assignment alone.
 *
 * Resources: I used geeks for geeks to understand some algorithms.
 */

public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new java.lang.IllegalArgumentException("Array or the comparator cannot be null");
        }
        for (int i = 0; i < arr.length; i++) {
            int j = i;
            while (j > 0 && comparator.compare(arr[j], arr[j - 1]) < 0) {
                swap(j, j - 1, arr);
                j--;
            }
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new java.lang.IllegalArgumentException("Array or the comparator cannot be null");
        }
        boolean swapped = true;
        int start = 0;
        int bc = 0;
        int end = arr.length;
        int ab = arr.length;
        while (swapped) {
            swapped = false;
            for (int i = start; i < end - 1; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    swap(i, i + 1, arr);
                    swapped = true;
                    ab = i;
                }
            }
            end = ab;
            if (!swapped) {
                break;
            }
            swapped = false;
            for (int i = end; i > start; i--) {
                if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                    swap(i - 1, i, arr);
                    swapped = true;
                    bc = i;
                }
            }
            start = bc;
            end = ab + 1;

        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new java.lang.IllegalArgumentException("Array or the comparator cannot be null");
        }
        if (arr.length > 1) {
            T[] left = (T[]) new Object[arr.length / 2];
            T[] right = (T[]) new Object[arr.length - arr.length / 2];
            for (int i = 0; i < arr.length / 2; i++) {
                left[i] = arr[i];
            }
            for (int i = arr.length / 2; i < arr.length; i++) {
                right[i - arr.length / 2] = arr[i];
            }
            mergeSort(left, comparator);
            mergeSort(right, comparator);
            merge(arr, left, right, comparator);
        }
    }
    /**
     * helper method for mergesort that merges to sorted arrays

     * @param <T>        data type to sort
     * @param array       the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @param leftArray the array containing the left part of the array
     * @param rightArray the array containing the right part of the array
     */
    private static <T> void merge(T[] array, T[] leftArray, T[] rightArray, Comparator<T> comparator) {
        int cur = 0;
        int rightIndex = 0;
        int leftIndex = 0;
        while (rightIndex < rightArray.length && leftIndex < leftArray.length) {
            if (comparator.compare(leftArray[leftIndex], rightArray[rightIndex]) <= 0) {
                array[cur++] = leftArray[leftIndex];
                leftIndex++;
            } else {
                array[cur++] = rightArray[rightIndex];
                rightIndex++;
            }
        }
        while (leftIndex < leftArray.length) {
            array[cur++] = leftArray[leftIndex++];
        }
        while (rightIndex < rightArray.length) {
            array[cur++] = rightArray[rightIndex++];
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new java.lang.IllegalArgumentException("Array cannot be null");
        }
        int ab = 0;
        for (int bc : arr) {
            if (Math.abs(bc) > ab) {
                ab = Math.abs(bc);
            }
            if (bc == Integer.MIN_VALUE) {
                ab = Integer.MAX_VALUE;
                break;
            }
        }
        int longest = 0;
        while (ab > 0) {
            ab /= 10;
            longest++;
        }
        int power = 1;
        LinkedList<Integer>[] buckets = (LinkedList<Integer>[]) new LinkedList[19];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new LinkedList<Integer>();
        }
        for (int i = 0; i < longest; i++) {
            for (int x : arr) {
                int lsd = (x % (power * 10)) / power;
                buckets[lsd + 9].add(x);
            }
            int ind = 0;
            for (LinkedList<Integer> bucket : buckets) {
                while (!bucket.isEmpty()) {
                    arr[ind++] = bucket.removeFirst();
                }
            }
            power *= 10;
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new java.lang.IllegalArgumentException("Array or the comparator or rand cannot be null");
        }
        quickSort(arr, comparator, rand, 0, arr.length);

    }

    /**
     * helper method for quicksort

     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @param start      the starting index of the array
     * @param end        the ending index of the array
     */
    private static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                      Random rand, int start, int end) {
        if (start + 1 >= end) {
            return;
        }
        int pivotIndex = rand.nextInt(end - start) + start;
        T piv = arr[pivotIndex];
        swap(pivotIndex, start, arr);
        int leftIndex = start + 1;
        int rightIndex = end - 1;
        while (rightIndex >= leftIndex) {
            while (rightIndex >= leftIndex &&  comparator.compare(arr[leftIndex], piv) <= 0) {
                leftIndex++;
            }
            while (rightIndex >= leftIndex && comparator.compare(arr[rightIndex], piv) >= 0) {
                rightIndex--;
            }
            if (rightIndex >= leftIndex) {
                swap(rightIndex, leftIndex, arr);
                rightIndex--;
                leftIndex++;
            }
        }
        swap(rightIndex, start, arr);
        quickSort(arr, comparator, rand, start, rightIndex);
        quickSort(arr, comparator, rand, rightIndex + 1, end);
    }

    /**
     * helper method to swap to elements

     * @param <T>        data type to sort
     * @param array      the array that contains the elements
     * @param i          the index of the first element to be swapped
     * @param j          the index of the second element to be swapped
     */
    private static <T> void swap(int i, int j, T[] array) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
