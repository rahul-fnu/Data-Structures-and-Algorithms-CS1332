/**
 * Your implementation of a LinkedDeque.
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
public class LinkedDeque<T> {

    // Do not add new instance variables or modify existing ones.
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the front of the deque.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addFirst(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("The argument 'data' is null.");
        }
        LinkedNode in = new LinkedNode(data);
        if (size == 0) {
            head = in;
            tail = in;
        } else {
            head.setPrevious(in);
            in.setNext(head);
            head = head.getPrevious();
        }
        size++;

    }

    /**
     * Adds the element to the back of the deque.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addLast(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("The argument 'data' is null.");
        }
        LinkedNode in = new LinkedNode(data);
        if (size == 0) {
            head = in;
            tail = in;
        } else {
            tail.setNext(in);
            in.setPrevious(tail);
            tail = tail.getNext();
        }
        size++;
    }

    /**
     * Removes and returns the first element of the deque.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeFirst() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("The deque is empty so no element exist inside it.");
        }
        T out = head.getData();
        head = head.getNext();
        size--;
        if (size !=  0) {
            head.setPrevious(null);
        } else {
            head = null;
            tail = null;
        }
        return out;
    }

    /**
     * Removes and returns the last element of the deque.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeLast() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("The deque is empty so no element exist inside it.");
        }
        T out = tail.getData();
        tail = tail.getPrevious();
        size--;
        if (size != 0) {
            tail.setNext(null);
        } else {
            head = null;
            tail = null;
        }
        return out;
    }

    /**
     * Returns the first data of the deque without removing it.
     *
     * Must be O(1).
     *
     * @return the data located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getFirst() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("The deque is empty so no element exist inside it.");
        }
        return head.getData();
    }

    /**
     * Returns the last data of the deque without removing it.
     *
     * Must be O(1).
     *
     * @return the data located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getLast() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("The deque is empty so no element exist inside it.");
        }
        return tail.getData();
    }

    /**
     * Returns the head node of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the deque
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY THIS METHOD!
        return head;
    }

    /**
     * Returns the tail node of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the deque
     */
    public LinkedNode<T> getTail() {
        // DO NOT MODIFY THIS METHOD!
        return tail;
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
}
