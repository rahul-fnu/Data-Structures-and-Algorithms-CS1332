import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Your implementation of a BST.
 *
 * @author Rahul
 * @version 1.0
 * @userid nrahul3 (i.e. gburdell3)
 * @GTID 903533392 (i.e. 900000000)
 *
 * Collaborators: I worked on this assignment alone.
 *
 * Resources: I used the resources provided by the professor and TA
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data is null");
        }
        for (T ab : data) {
            if (ab == null) {
                throw new java.lang.IllegalArgumentException("An element inside data is null");
            }
            add(ab);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data is null.");
        }
        if (size == 0) {
            root = new BSTNode<>(data);
            size++;
        } else {
            addH(data, root);
        }
    }

    /**
     * Helper method to add data to the tree.
     *
     * @param data the data to add
     * @param root the root of the tree in which data is added.
     */
    private void addH(T data, BSTNode<T> root) {
        if (data.compareTo(root.getData()) == 0) {
            return;
        } else if (data.compareTo(root.getData()) < 0) {
            if (root.getLeft() == null) {
                root.setLeft(new BSTNode<T>(data));
                size++;
            } else {
                addH(data, root.getLeft());
            }
        } else {
            if (root.getRight() == null) {
                root.setRight(new BSTNode<T>(data));
                size++;
            } else {
                addH(data, root.getRight());
            }
        }
    }


    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data is null.");
        }
        BSTNode<T> dummy = new BSTNode<>(null);
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
    private BSTNode<T> removeH(BSTNode<T> curr, T data, BSTNode<T> dummy) {
        if (curr == null) {
            throw new java.util.NoSuchElementException("There is no such element equal to data in the BST.");
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(removeH(curr.getRight(), data, dummy));
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(removeH(curr.getLeft(), data, dummy));
        } else {
            dummy.setData(curr.getData());
            size--;
            if (curr.getRight() == null && curr.getLeft() == null) {
                return null;
            } else if (curr.getRight() == null) {
                return curr.getLeft();
            } else if (curr.getLeft() == null) {
                return curr.getRight();
            } else {
                BSTNode<T> dummyT = new BSTNode<T>(null);
                curr.setRight(getSuccessor(curr.getRight(), dummyT));
                curr.setData(dummyT.getData());
            }
        }
        return curr;
    }
    /**
     * method to get successor of the BST
     *
     * @param curr the node the right of the node being removed.
     * @param dummy the node in which data is being saved.
     * @return the successor for the BST.
     */
    private BSTNode<T> getSuccessor(BSTNode<T> curr, BSTNode<T> dummy) {
        if (curr.getLeft() == null) {
            dummy.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(getSuccessor(curr.getLeft(), dummy));
            return curr;
        }
    }
    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data is null");
        }
        T out = get(root, data);
        if (out == null) {
            throw new java.util.NoSuchElementException("There no element equal to data in BST");
        }
        return out;
    }
    /**
     * Returns the data from the tree matching the given parameter.
     *
     * @param data the data to search for
     * @param root the root of the BST
     * @return the data in the tree equal to the parameter
     */
    private T get(BSTNode<T> root, T data) {
        T out = null;
        if (root != null) {
            if (root.getData().equals(data)) {
                out = root.getData();
            } else {
                if (data.compareTo(root.getData()) < 0) {
                    out = get(root.getLeft(), data);
                } else {
                    out = get(root.getRight(), data);
                }
            }
        }
        return out;
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
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
    private T getContains(BSTNode<T> root, T data) {
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
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> out = new ArrayList<>();
        preorderHelper(out, root);
        return out;
    }
    /**
     * helper method for a pre-order traversal of the tree.
     *
     * @param out the list in which data of the tree is being added.
     * @param root the root of the tree being traversed.
     */
    private void preorderHelper(List<T> out, BSTNode<T> root) {
        if (root != null) {
            out.add(root.getData());
            preorderHelper(out, root.getLeft());
            preorderHelper(out, root.getRight());
        }
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> out = new ArrayList<>();
        inorderHelper(out, root);
        return out;
    }
    /**
     * helper method for a in-order traversal of the tree.
     *
     * @param out the list in which data of the tree is being added.
     * @param root the root of the tree being traversed.
     */
    private void inorderHelper(List<T> out, BSTNode<T> root) {
        if (root != null) {
            inorderHelper(out, root.getLeft());
            out.add(root.getData());
            inorderHelper(out, root.getRight());
        }
    }
    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> out = new ArrayList<>();
        postorderHelper(out, root);
        return out;
    }
    /**
     * helper method for a post-order traversal of the tree.
     *
     * @param out the list in which data of the tree is being added.
     * @param root the root of the tree being traversed.
     */
    private void postorderHelper(List<T> out, BSTNode<T> root) {
        if (root != null) {
            postorderHelper(out, root.getLeft());
            postorderHelper(out, root.getRight());
            out.add(root.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> out = new ArrayList<>();
        Queue<BSTNode<T>> t = new LinkedList<>();
        if (root != null) {
            t.add(root);
        }
        while (!t.isEmpty()) {
            BSTNode<T> n = t.remove();
            out.add(n.getData());
            if (n.getLeft() != null) {
                t.add(n.getLeft());
            }
            if (n.getRight() != null) {
                t.add(n.getRight());
            }
        }
        return out;

    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        }
        return Math.max(heightH(root.getLeft()), heightH(root.getRight()));
    }
    /**
     * helper method for calculating the height of tree.
     *
     * @param root the root of the tree whose height is being calculated.
     * @return int the heigth of the tree.
     */
    private int heightH(BSTNode<T> root) {
        if (root == null) {
            return 0;
        } else {
            return 1 + Math.max(heightH(root.getRight()), heightH(root.getLeft()));
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     *
     * This must be done recursively.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     *
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k).
     *
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     * @throws java.lang.IllegalArgumentException if k > n, the number of data
     *                                            in the BST
     */
    public List<T> kLargest(int k) {
        if (k > size || k < 0) {
            throw new java.lang.IllegalArgumentException("k cannot be greater than size of BST or less than 0");
        } else if (k == size) {
            return inorder();
        } else {
            List<T> ab = new LinkedList<>();
            kHelper(ab, k, root, ab.size());
            return ab;
        }

    }
    /**
     * helper method to return k largest elements of the list
     *
     * @param node the root of the tree whose height is being calculated.
     * @param ab the List in which nodes are added too.
     * @param k the total number highest elemets;
     * @param curr total number of items added to List being returned
     * @return List with the items returned
     */
    private List<T> kHelper(List<T> ab, int k, BSTNode<T> node, int curr) {
        if (curr < k) {
            if (node.getRight() != null) {
                kHelper(ab, k, node.getRight(), ab.size());
            }
            if (ab.size() == k) {
                return ab;
            }
            ab.add(0, node.getData());
            if (node.getLeft() != null) {
                kHelper(ab, k, node.getLeft(), ab.size());
            }
        }
        return ab;
    }


    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
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
