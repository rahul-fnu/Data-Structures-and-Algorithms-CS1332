/**
 * Your implementation of an ArrayDeque.
 *
 * @author Rahul .
 * @version 1.0
 * @userid nrahul3
 * @GTID 903533392
 *
 * Collaborators: I worked on this assignment alone
 *
 * Resources: I used the resources provided by the instructors for this course.
 */
public class ArrayDeque<T> {

    /**
     * The initial capacity of the ArrayDeque.
     *
     * DO NOT MODIFY THIS VARIABLE.
     */
    public static final int INITIAL_CAPACITY = 11;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int front;
    private int size;

    /**
     * Constructs a new ArrayDeque.
     */
    public ArrayDeque() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Adds the element to the front of the deque.
     *
     * If sufficient space is not available in the backing array, resize it to
     * double the current capacity. When resizing, copy elements to the
     * beginning of the new array and reset front to 0. After the resize and add
     * operation, the new data should be at index 0 of the array.
     *
     * Must be amortized O(1).
     *
     * @param data the data to add to the front of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addFirst(T data) {
        if (backingArray != null) {
            if (data == null) {
                throw new java.lang.IllegalArgumentException("The argument 'data' is null.");
            }
            if (size == backingArray.length) {
                T[] temp = (T[]) new Object[2 * backingArray.length];
                int j = 1;
                temp[0] = data;
                for (int i = 0; i < backingArray.length; i++) {
                    if (i < front) {
                        temp[backingArray.length - front + 1 + i] = backingArray[i];
                    } else {
                        temp[j] = backingArray[i];
                        j++;
                    }
                }
                backingArray = temp;
                front = 0;
            } else {
                backingArray[mod(front - 1, backingArray.length)] = data;
                front = mod(front - 1, backingArray.length);
            }

            size++;
        }
    }

    /**
     * Adds the element to the back of the deque.
     *
     * If sufficient space is not available in the backing array, resize it to
     * double the current capacity. When resizing, copy elements to the
     * beginning of the new array and reset front to 0.
     *
     * Must be amortized O(1).
     *
     * @param data the data to add to the back of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addLast(T data) {
        if (backingArray != null) {
            if (data == null) {
                throw new java.lang.IllegalArgumentException("The argument 'data' is null.");
            }
            if (size == backingArray.length) {
                T[] temp =  (T[]) new Object[2 * backingArray.length];
                int j = 0;
                for (int i = 0; i < backingArray.length; i++) {
                    if (i < front) {
                        temp[backingArray.length - front + i] = backingArray[i];
                    } else {
                        temp[j++] = backingArray[i];
                    }
                }
                temp[size] = data;
                backingArray = temp;
                front = 0;
            } else {
                backingArray[mod(front + size, backingArray.length)] = data;
            }
            size++;

        }
    }

    /**
     * Removes and returns the first element of the deque.
     *
     * Do not grow or shrink the backing array.
     *
     * If the deque becomes empty as a result of this call, do not reset
     * front to 0. Rather, modify the front index as if the size did not become
     * empty as a result of this call.
     *
     * Replace any spots that you remove from with null. Failure to do so can
     * result in loss of points.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeFirst() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("The deque is empty so no element can be removed from it.");
        }
        T out = backingArray[front];
        backingArray[front] = null;
        //System.out.println(front);
        front = mod(front + 1, backingArray.length);
        size--;
        return out;
    }

    /**
     * Removes and returns the last element of the deque.
     *
     * Do not grow or shrink the backing array.
     *
     * If the deque becomes empty as a result of this call, do not reset
     * front to 0. Rather, modify the front index as if the size did not become
     * empty as a result of this call.
     *
     * Replace any spots that you remove from with null. Failure to do so can
     * result in loss of points.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeLast() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("The deque is empty so no element can be removed from it.");
        }
        T out;
        if (mod(front + size, backingArray.length) - 1 != -1) {
            out = backingArray[mod(front + size, backingArray.length) - 1];
            backingArray[mod(front + size, backingArray.length) - 1] = null;
        } else {
            out = backingArray[backingArray.length - 1];
            backingArray[backingArray.length - 1] = null;
        }
        size--;
        return out;

    }

    /**
     * Returns the first data of the deque without removing it.
     *
     * Must be O(1).
     *
     * @return the first data
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getFirst() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("The deque is empty so no element exist inside it.");
        }
        return backingArray[front];
    }

    /**
     * Returns the last data of the deque without removing it.
     *
     * Must be O(1).
     *
     * @return the last data
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getLast() {
        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }
        if (mod(front + size, backingArray.length) - 1 != -1) {
            return backingArray[mod(front + size, backingArray.length) - 1];
        } else {
            return backingArray[backingArray.length - 1];

        }
    }

    /**
     * Returns the backing array of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the deque
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the deque
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Returns the smallest non-negative remainder when dividing index by
     * modulo. So, for example, if modulo is 5, then this method will return
     * either 0, 1, 2, 3, or 4, depending on what the remainder is.
     *
     * This differs from using the % operator in that the % operator returns
     * the smallest answer with the same sign as the dividend. So, for example,
     * (-5) % 6 => -5, but with this method, mod(-5, 6) = 1.
     *
     * Examples:
     * mod(-3, 5) => 2
     * mod(11, 6) => 5
     * mod(-7, 7) => 0
     *
     * This helper method is here to make the math part of the circular
     * behavior easier to work with.
     *
     * @param index  the number to take the remainder of
     * @param modulo the divisor to divide by
     * @return the remainder in its smallest non-negative form
     * @throws java.lang.IllegalArgumentException if the modulo is non-positive
     */
    private static int mod(int index, int modulo) {
        // DO NOT MODIFY THIS METHOD!
        if (modulo <= 0) {
            throw new IllegalArgumentException("The modulo must be positive");
        }
        int newIndex = index % modulo;
        return newIndex >= 0 ? newIndex : newIndex + modulo;
    }
}
