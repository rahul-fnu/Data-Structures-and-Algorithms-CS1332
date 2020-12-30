/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
 *
 * @author Rahul .
 * @version 1.0
 * @userid nrahul3 (i.e. nrahul3)
 * @GTID 903533392 (i.e. 903533392)
 *
 * Collaborators: I did this assignment alone.
 *
 * Resources: I used the resources provided by the instructors.
 */
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new java.lang.IndexOutOfBoundsException();
        } else if (data == null) {
            throw new java.lang.IllegalArgumentException();
        } else {
            DoublyLinkedListNode<T> in = new DoublyLinkedListNode<>(data);
            if (size == 0) {
                head = in;
                tail = in;
            } else {
                if (index == size) {
                    tail.setNext(in);
                    in.setPrevious(tail);
                    tail = tail.getNext();
                } else if (index == 0) {
                    head.setPrevious(in);
                    in.setNext(head);
                    head = head.getPrevious();
                } else {
                    if (size / 2 > index) {
                        DoublyLinkedListNode<T> dummy = head;
                        for (int i = 0; i < index; i++) {
                            dummy = dummy.getNext();
                        }
                        in.setPrevious(dummy);
                        in.setNext(dummy.getNext());
                        dummy.getNext().setPrevious(in);
                        dummy.setNext(in);
                    } else {
                        DoublyLinkedListNode<T> dummy = tail;
                        for (int i = size - 1; i > index; i--) {
                            dummy = dummy.getPrevious();
                        }
                        in.setNext(dummy);
                        in.setPrevious(dummy.getPrevious());
                        dummy.getPrevious().setNext(in);
                        dummy.setPrevious(in);
                    }
                }
            }
            size++;
            
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException();
        }
        addAtIndex(0, data);
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException();
        }
        addAtIndex(size, data);
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new java.lang.IndexOutOfBoundsException();
        } else {
            T out;
            if (index == 0) {
                if (size == 1) {
                    out = head.getData();
                    clear();
                } else {
                    out = head.getData();
                    head = head.getNext();
                    head.setPrevious(null);
                    size--;
                }
            } else if (index == size - 1) {
                out = tail.getData();
                tail = tail.getPrevious();
                tail.setNext(null);
                size--;
            } else {
                int diff = size - index - 1;
                if (diff >= index) {
                    DoublyLinkedListNode<T> dummy = head;
                    int counter = 0;
                    while (counter < index - 1) {
                        dummy = dummy.getNext();
                        counter++;
                    }
                    out = dummy.getNext().getData();
                    DoublyLinkedListNode<T> ab = dummy.getNext().getNext();
                    dummy.setNext(ab);
                    ab.setPrevious(dummy);
                } else {
                    DoublyLinkedListNode<T> dummy = tail;
                    int counter = size - 1;
                    while (counter > index + 1) {
                        dummy = dummy.getPrevious();
                        counter--;
                    }
                    out = dummy.getPrevious().getData();
                    DoublyLinkedListNode<T> ab = dummy.getPrevious().getPrevious();
                    dummy.setPrevious(ab);
                    ab.setNext(dummy);
                }
                size--;
            }
            return out;
        }
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }
        return removeAtIndex(0);
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }
        return removeAtIndex(size - 1);
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new java.lang.IndexOutOfBoundsException();
        } else {
            int diff = size - index;
            if (index == 0) {
                return head.getData();
            } else if (index == size - 1) {
                return tail.getData();
            } else {
                if (diff >= index) {
                    DoublyLinkedListNode<T> dummy = head;
                    for (int i = 0; i < index; i++) {
                        dummy = dummy.getNext();
                    }
                    return dummy.getData();
                } else {
                    DoublyLinkedListNode<T> dummy = tail;
                    for (int i = size - 1; i > index; i--) {
                        dummy = dummy.getPrevious();
                    }
                    return dummy.getData();
                }
            }
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;

    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        size = 0;
        head = null;
        tail = null;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException();
        }
        DoublyLinkedListNode<T> dummy = tail;
        int index = -1;
        for (int i = size; i > 0; i--) {
            if (dummy.getData().equals(data)) {
                index = i - 1;
                break;
            }
            dummy = dummy.getPrevious();
        }
        if (index == -1) {
            throw new java.util.NoSuchElementException();
        }
        return removeAtIndex(index);
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        Object[] out = new Object[size];
        DoublyLinkedListNode<T> dummy = head;
        for (int i = 0; i < size; i++) {
            out[i] = dummy.getData();
            dummy = dummy.getNext();
        }
        return out;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
