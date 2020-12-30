import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Your implementation of an AVL.
 *
 * @author Rahul
 * @version 1.0
 * @userid nrahul3 (i.e. gburdell3)
 * @GTID 903533392 (i.e. 900000000)
 *
 * Collaborators: I did this homework alone
 *
 * Resources: I used the resources provided by the instructors
 */
public class AVL<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data is null");
        }
        for (T item : data) {
            add(item);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data is null");
        } else {
            root = addH(root, data);
        }
    }
    /**
     * Helper method to add data to the tree.
     *
     * @param data the data to add
     * @param root the root of the tree in which data is added.
     * @return the balance node after the data is added and node is balanced
     */
    private AVLNode<T> addH(AVLNode<T> root, T data) {
        if (root == null) {
            root = new AVLNode<>(data);
            root.setHeight(0);
            root.setBalanceFactor(0);
            size++;
            return root;
        } else if (root.getData().compareTo(data) > 0) {
            root.setLeft(addH(root.getLeft(), data));
        } else if (root.getData().compareTo(data) < 0) {
            root.setRight(addH(root.getRight(), data));
        }
        root = updateBFandHeight(root);

        return balanceNodes(root);
    }


    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     *    simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     *    replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     *    replace the data, NOT successor. As a reminder, rotations can occur
     *    after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data is null.");
        }
        AVLNode<T> dummy = new AVLNode<>(null);
        root = removeH(root, data, dummy);
        return dummy.getData();
    }
    /**
     * Helper method to remove data from the tree.
     *
     *
     * @param data the data to remove.
     * @param curr the current node being inspected.
     * @param dummy the node in which data is saved
     * @return curr the current node.
     * @throws java.util.NoSuchElementException if data doesnt exist in the BST
     */
    private AVLNode<T> removeH(AVLNode<T> curr, T data, AVLNode<T> dummy) {
        if (curr == null) {
            throw new java.util.NoSuchElementException("There is no such element equal to data in the AVL.");
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(removeH(curr.getRight(), data, dummy));
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(removeH(curr.getLeft(), data, dummy));
        } else {
            size--;
            dummy.setData(curr.getData());
            if (curr.getRight() == null && curr.getLeft() == null) {
                return null;
            } else if (curr.getRight() == null) {
                return curr.getLeft();
            } else if (curr.getLeft() == null) {
                return curr.getRight();
            } else {
                AVLNode<T> dummyT = new AVLNode<>(null);
                curr.setLeft(getPredecessor(curr.getLeft(), dummyT));
                curr.setData(dummyT.getData());
            }
        }
        curr = updateBFandHeight(curr);
        return balanceNodes(curr);
    }
    /**
     * method to get predecessor of the AVLTree
     *
     * @param curr the node the right of the node being removed.
     * @param dummy the node in which data is being saved.
     * @return the successor for the AVLTree.
     */
    private AVLNode<T> getPredecessor(AVLNode<T> curr, AVLNode<T> dummy) {
        if (curr.getRight() == null) {
            dummy.setData(curr.getData());
            return curr.getLeft();
        } else {
            curr.setRight(getPredecessor(curr.getRight(), dummy));
            curr = updateBFandHeight(curr);
            return balanceNodes(curr);
        }
    }
    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data is null");
        }
        T out = getHelper(root, data);
        if (out == null) {
            throw new java.util.NoSuchElementException("There no element equal to data in AVL");
        }
        return out;
    }
    /**
     * Returns the data from the tree matching the given parameter.
     *
     * @param data the data to search for
     * @param root the root of the AVLTree
     * @return the data in the tree equal to the parameter
     */
    private T getHelper(AVLNode<T> root, T data) {
        T out = null;
        if (root != null) {
            if (root.getData().equals(data)) {
                out = root.getData();
            } else {
                if (data.compareTo(root.getData()) < 0) {
                    out = getHelper(root.getLeft(), data);
                } else {
                    out = getHelper(root.getRight(), data);
                }
            }
        }
        return out;
    }
    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("There no element equal to data in the BST");
        }
        T out = getContains(root, data);
        return !(out == null);
    }
    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     *
     * @param data the data to search for
     * @param root the root of the tree.
     * @return the data in the tree equal to the parameter.
     */
    private T getContains(AVLNode<T> root, T data) {
        T out = null;
        if (root != null && root.getData().compareTo(data) == 0) {
            out = root.getData();
        } else if (root != null) {
            if (root.getData().compareTo(data) > 0) {
                out = getContains(root.getLeft(), data);
            } else {
                out = getContains(root.getRight(), data);
            }
        }
        return out;
    }


    /**
     * Returns the height of the root of the tree. Do NOT compute the height
     * recursively. This method should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        }
        return root.getHeight();
    }
    /**
     * Updates the height and the balancing factor of the node passed into the methon
     *
     * @param root orignal root with orignal height and balancing factor
     * @return the node of the tree with updated height and balancing factor
     */
    private AVLNode<T> updateBFandHeight(AVLNode<T> root) {
        int left;
        int right;
        if (root.getLeft() == null) {
            left = -1;
        } else {
            left = root.getLeft().getHeight();
        }
        if (root.getRight() == null) {
            right = -1;
        } else {
            right = root.getRight().getHeight();
        }
        root.setHeight(Math.max(left, right) + 1);
        root.setBalanceFactor(left - right);
        return root;
    }

    /**
     * Does left rotation on the node passed in
     *
     * @param root orignal unbalanced node
     * @return new balnced node;
     */
    private AVLNode<T> leftRotate(AVLNode<T> root) {
        AVLNode<T> rightChild = root.getRight();
        root.setRight(rightChild.getLeft());
        rightChild.setLeft(root);
        root = updateBFandHeight(root);
        rightChild = updateBFandHeight(rightChild);
        return rightChild;
    }
    /**
     * Does right rotation on the node passed in
     *
     * @param root orignal unbalanced node
     * @return new balnced node;
     */
    private AVLNode<T> rightRotate(AVLNode<T> root) {
        AVLNode<T> leftChild = root.getLeft();
        root.setLeft(leftChild.getRight());
        leftChild.setRight(root);
        root = updateBFandHeight(root);
        leftChild = updateBFandHeight(leftChild);
        return leftChild;
    }
    /**
     * Does left-right rotation on the node passed in
     *
     * @param root orignal unbalanced node
     * @return new balnced node;
     */
    private AVLNode<T> leftRightRotate(AVLNode<T> root) {
        root.setLeft(leftRotate(root.getLeft()));
        return rightRotate(root);
    }
    /**
     * Does right-left rotation on the node passed in
     *
     * @param root orignal unbalanced node
     * @return new balnced node;
     */
    private AVLNode<T> rightLeftRotate(AVLNode<T> root) {
        root.setRight(rightRotate(root.getRight()));
        return leftRotate(root);
    }
    /**
     * Balanced the node that is passed into the methon
     *
     * @param root orignal unbalanced node
     * @return new balnced node;
     */
    private AVLNode<T> balanceNodes(AVLNode<T> root) {
        if (root.getBalanceFactor() < -1) {
            if (root.getRight().getBalanceFactor() <= 0) {
                return leftRotate(root);
            } else {
                return rightLeftRotate(root);
            }
        } else if (root.getBalanceFactor() > 1) {
            if (root.getLeft().getBalanceFactor() >= 0) {
                return rightRotate(root);
            } else {
                return leftRightRotate(root);
            }
        }
        return root;
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root.setBalanceFactor(0);
        root.setHeight(0);
        root = null;
        size = 0;
    }

    /**
     * Find a path of letters in the tree that spell out a particular word,
     * if the path exists.
     *
     * Ex: Given the following AVL
     *
     *                   g
     *                 /   \
     *                e     i
     *               / \   / \
     *              b   f h   n
     *             / \         \
     *            a   c         u
     *
     * wordSearch([b, e, g, i, n]) returns the list [b, e, g, i, n],
     * where each letter in the returned list is from the tree and not from
     * the word array.
     *
     * wordSearch([h, i]) returns the list [h, i], where each letter in the
     * returned list is from the tree and not from the word array.
     *
     * wordSearch([a]) returns the list [a].
     *
     * wordSearch([]) returns an empty list [].
     *
     * wordSearch([h, u, g, e]) throws NoSuchElementException. Although all
     * 4 letters exist in the tree, there is no path that spells 'huge'.
     * The closest we can get is 'hige'.
     *
     * To do this, you must first find the deepest common ancestor of the
     * first and last letter in the word. Then traverse to the first letter
     * while adding letters on the path to the list while preserving the order
     * they appear in the word (consider adding to the front of the list).
     * Finally, traverse to the last letter while adding its ancestor letters to
     * the back of the list. Please note that there is no relationship between
     * the first and last letters, in that they may not belong to the same
     * branch. You will most likely have to split off to traverse the tree when
     * searching for the first and last letters.
     *
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use since you may have to add to the front and
     * back of the list.
     *
     * You will only need to traverse to the deepest common ancestor once.
     * From that node, go to the first and last letter of the word in one
     * traversal each. Failure to do so will result in a efficiency penalty.
     * Validating the path against the word array efficiently after traversing
     * the tree will NOT result in an efficiency penalty.
     *
     * If there exists a path between the first and last letters of the word,
     * there will only be 1 valid path.
     *
     * You may assume that the word will not contain duplicate letters.
     *
     * WARNING: Do not return letters from the passed-in word array!
     * If a path exists, the letters should be retrieved from the tree.
     * Returning any letter from the word array will result in a penalty!
     *
     * @param word array of T, where each element represents a letter in the
     * word (in order).
     * @return list containing the path of letters in the tree that spell out
     * the word, if such a path exists. Order matters! The ordering of the
     * letters in the returned list should match that of the word array.
     * @throws java.lang.IllegalArgumentException if the word array is null
     * @throws java.util.NoSuchElementException if the path is not in the tree
     */
    public List<T> wordSearch(T[] word) {
        if (word == null) {
            throw new java.lang.IllegalArgumentException("word array cannot be null");
        }
        List<T> out = new LinkedList<>();
        if (word.length == 0) {
            return out;
        }
        out = wordSearchHelper(word, root, out);
        if (out.size() != word.length) {
            throw new java.util.NoSuchElementException();
        } else {
            for (int i = 0; i < word.length; i++) {
                if (out.get(i).compareTo(word[i]) != 0) {
                    throw new java.util.NoSuchElementException("No path in the tree is equal to the word");
                }
            }
        }
        return out;

    }
    /**
     * Finds the deepest common ancessotor the tree for the first and last
     * item in word array and adds all the items in their path in a LinkedList
     *
     * @param root the root of the tree in which word is to be found
     * @param word the word to be found
     * @param out the list in which elements found inside the tree are to be added
     * @return the list of items in the path of the first and last letter of the word array
     */
    private List<T> wordSearchHelper(T[] word, AVLNode<T> root, List<T> out) {
        if (root == null) {
            throw new java.util.NoSuchElementException("No path in the tree is equal to the word");
        }
        if (word[0].compareTo(root.getData()) < 0 && word[word.length - 1].compareTo(root.getData()) < 0) {
            return wordSearchHelper(word, root.getLeft(), out);
        } else if (word[0].compareTo(root.getData()) > 0 && word[word.length - 1].compareTo(root.getData()) > 0) {
            return wordSearchHelper(word, root.getRight(), out);
        } else {
            helperTwo(word, root, out);
            out.remove(out.size() - 1);
            helperThree(word, root, out);
        }
        return out;
    }
    /**
     * Adds item at the start of the list from the left of the root
     * to the start of the list
     *
     * @param root the root of the tree in which word is to be found
     * @param word the word to be found
     * @param out the list in which elements found inside the tree are to be added
     */
    private void helperTwo(T[] word, AVLNode<T> root, List<T> out) {
        if (root == null) {
            throw new java.util.NoSuchElementException("No path in the tree is equal to the word");
        }
        out.add(0, root.getData());
        if (root.getData().compareTo(word[0]) > 0) {
            helperTwo(word, root.getLeft(), out);
        } else if (root.getData().compareTo(word[0]) < 0) {
            helperTwo(word, root.getRight(), out);
        }
    }
    /**
     * Adds item at the end of the list from the root to right side
     * of the root till the end of the word.
     *
     * @param root the root of the tree in which word is to be found
     * @param word the word to be found
     * @param out the list in which elements found inside the tree are to be added
     */
    private void helperThree(T[] word, AVLNode<T> root, List<T> out) {
        if (root == null) {
            throw new java.util.NoSuchElementException("No path in the tree is equal to the word");
        }
        out.add(out.size(), root.getData());
        if (root.getData().compareTo(word[word.length - 1]) > 0) {
            helperThree(word, root.getLeft(), out);
        } else if (root.getData().compareTo(word[word.length - 1]) < 0) {
            helperThree(word, root.getRight(), out);
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}