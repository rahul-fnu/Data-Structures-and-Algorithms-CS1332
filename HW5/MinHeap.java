import java.util.ArrayList;

/**
 * Your implementation of a MinHeap.
 *
 * @author Rahul .
 * @version 1.0
 * @userid nrahul3 (i.e. gburdell3)
 * @GTID 903533392 (i.e. 900000000)
 *
 * Collaborators: I did this assignment alone.
 *
 * Resources: I used the resources provided by the instructors.
 */
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("data is null");
        } else {
            backingArray = (T[]) new Comparable[2 * data.size() + 1];
            for (int i = 1; i <= data.size(); i++) {
                if (data.get(i - 1) == null) {
                    throw new java.lang.IllegalArgumentException("data is null");
                } else {
                    backingArray[i] = data.get(i - 1);
                    size++;
                }

            }
            for (int i = size / 2; i > 0; i--) {
                downHeap(i);
            }
        }

    }
    /**
     * Helper method in order to fix the order
     * of the array.
     *
     * @param index at which the order of the heap is be fixed.
     */
    private void downHeap(int index) {
        int downHeapIndex = index;
        while (downHeapIndex <= size / 2) {
            T childOne = backingArray[downHeapIndex * 2];
            T childTwo = backingArray[(downHeapIndex * 2) + 1];
            T compared = childOne;
            int next = downHeapIndex * 2;
            if (childTwo != null && childOne.compareTo(childTwo) > 0) {
                compared = childTwo;
                next++;
            }
            if (compared != null && backingArray[downHeapIndex].compareTo(compared) > 0) {
                backingArray[next] = backingArray[downHeapIndex];
                backingArray[downHeapIndex] = compared;
                downHeapIndex = next;
            } else {
                break;
            }
        }
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Null cannot be added to the heap");
        }
        if (size + 1 == backingArray.length) {
            T[] newArray = (T[]) new Comparable[2 * backingArray.length];
            for (int i = 1; i < backingArray.length; i++) {
                newArray[i] = backingArray[i];
            }
            backingArray = newArray;
        }
        size++;
        backingArray[size] = data;
        for (int i = size; i > 1; i /= 2) {
            if (data.compareTo(backingArray[i / 2]) < 0) {
                T compare = backingArray[i / 2];
                backingArray[i / 2] = data;
                backingArray[i] = compare;
            } else {
                break;
            }
        }
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("The heap is empty");
        }
        T out = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        downHeap(1);
        return out;
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Cannot remove from an Empty heap");
        }
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
