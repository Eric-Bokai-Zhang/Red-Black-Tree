package core;

public class RBTree<K extends Comparable<K>, V> {

    protected Node<K, V> root;
    protected int size;

    public RBTree() {
        root = null;
        size = 0;
    }

    /**
     * Search a tuple given the key.
     * @param key key of the tuple
     * @return value of the tuple. If not found, return null.
     */
    public V search(K key) {
        Node<K, V> temp = searchHelper(key, root);
        if (temp == null) {
            return null;
        }
        return temp.value;
    }

    private Node<K, V> searchHelper(K key, Node<K, V> current) {
        if (current == null) {
            return null;
        } else if (key.compareTo(current.key) < 0) {
            return searchHelper(key, current.left);
        } else if (key.compareTo(current.key) > 0) {
            return searchHelper(key, current.right);
        } else {
            return current;
        }
    }

    /**
     * Get the minimum key.
     * @return the minimum key (null if the tree is empty)
     */
    public K min() {
        Node<K, V> temp = minHelper(root);
        if (temp == null) {
            return null;
        }
        return temp.key;
    }

    private Node<K, V> minHelper(Node<K, V> current) {
        if (current == null) {
            return null;
        } else if (current.left != null) {
            return minHelper(current.left);
        } else {
            return current;
        }
    }

    /**
     * Get the maximum key.
     * @return the maximum key (null if the tree is empty)
     */
    public K max() {
        Node<K, V> temp = maxHelper(root);
        if (temp == null) {
            return null;
        }
        return temp.key;
    }

    private Node<K, V> maxHelper(Node<K, V> current) {
        if (current == null) {
            return null;
        } else if (current.right != null) {
            return maxHelper(current.right);
        } else {
            return current;
        }
    }

    /**
     * Insert a tuple of key and value into the tree (replace value if the key already exists)
     * @param key   key of the tuple
     * @param value value of the tuple
     */
    public void insert(K key, V value) {
        String alert = "Successfully inserted the tuple: " + key.toString() + "    " + value.toString();
        //if the tree is empty;
        if (root == null) {
            root = new Node<>(null, null, null, key, value);
            root.color = Node.COLOR.BLACK;
            size++;
            System.out.println(alert);
        } else {
            Node<K, V> current = root;
            while (true) {
                if (key.compareTo(current.key) < 0) {
                    if (current.left == null) { // insert
                        current.left = new Node<>(current, null, null, key, value);
                        size++;
                        balance(current.left);
                        System.out.println(alert);
                        break;
                    } else {
                        current = current.left;
                    }
                } else if (key.compareTo(current.key) > 0) {
                    if (current.right == null) { // insert
                        current.right = new Node<>(current, null, null, key, value);
                        size++;
                        balance(current.right);
                        System.out.println(alert);
                        break;
                    } else {
                        current = current.right;
                    }
                } else { //when key is exactly the same, update the value.
                    current.value = value;
                    System.out.println("Successfully updated the tuple: " + key.toString() + "    " + value.toString());
                    break;
                }
            }
        }
    }

    /**
     * Delete the tuple given the key
     * @param key key of the tuple
     */
    public void delete(K key) {
        Node<K, V> search = searchHelper(key, root);
        if (search == null) {
            System.out.println("The key does not exist: " + key.toString());
        } else {
            deleteHelper(search);
            System.out.println("Successfully deleted the tuple with key: " + key.toString());
            size--;
        }
    }

    private void deleteHelper(Node<K, V> deleted) {
        if (deleted.parent == null) { //if the node is root
            if (root.right != null) {
                Node<K, V> newRoot = minHelper(root.right);
                root.key = newRoot.key;
                root.value = newRoot.value;
                deleteHelper(newRoot);
            } else {
                root = null;
                System.out.println("The tree is empty.");
            }
        } else {
            if (deleted.color == Node.COLOR.RED) { // if the deleted node is red
                if ((deleted.left == null) && (deleted.right == null)) { // the deleted node has no child
                    if ((deleted.parent.left != null) && (deleted.parent.left.key.compareTo(deleted.key) == 0)) {
                        deleted.parent.left = null;
                    } else {
                        deleted.parent.right = null;
                    }
                } else { // the deleted node has two children
                    deleted.color = Node.COLOR.BLACK;
                    deleteHelper(minHelper(deleted.right));
                }
            } else { // if the deleted node is black
                if ((deleted.left != null) && (deleted.right == null)) { // the deleted node has only left child
                    deleted.key = deleted.left.key;
                    deleted.value = deleted.left.value;
                    deleted.left = null;
                } else if ((deleted.left == null) && (deleted.right != null)) { // the deleted node has only right child
                    deleted.key = deleted.right.key;
                    deleted.value = deleted.right.value;
                    deleted.right = null;
                } else { // the deleted node has two children or do not have children
                    if ((deleted.parent.left != null) && (deleted.parent.left.key.compareTo(deleted.key) == 0)) { // if is on the left
                        if(deleted.parent.right == null){
                            deleted.parent.color = Node.COLOR.BLACK;
                            deleted.parent.left = null;
                        }
                        else if (deleted.parent.right.color == Node.COLOR.RED) { // if the sibling of is red
                            if((deleted.parent.right.left == null) && (deleted.parent.right.right == null)){ // if the sibling has no child
                                deleted.parent.left = null;
                            }
                            else {
                                deleted.parent.right.color = Node.COLOR.BLACK;
                                deleted.parent.color = Node.COLOR.RED;
                                rotateLeft(deleted.parent);
                                deleteHelper(deleted);
                            }
                        } else { // if the sibling is black
                            if ((deleted.parent.right.right != null) && (deleted.parent.right.right.color == Node.COLOR.RED)) {
                                Node.COLOR temp = deleted.parent.color;
                                deleted.parent.color = deleted.parent.right.color;
                                deleted.parent.right.color = temp;
                                deleted.parent.right.right.color = Node.COLOR.BLACK;
                                rotateLeft(deleted.parent);
                                deleteHelper(deleted);
                            } else if ((deleted.parent.right.right != null) && (deleted.parent.right.right.color == Node.COLOR.BLACK)
                                    && (deleted.parent.right.left != null) && (deleted.parent.right.left.color == Node.COLOR.RED)) {
                                deleted.parent.right.color = Node.COLOR.RED;
                                deleted.parent.right.left.color = Node.COLOR.BLACK;
                                rotateRight(deleted.parent.right);
                                deleteHelper(deleted);
                            } else {
                                deleted.parent.right.color = Node.COLOR.RED;
                                deleted.key = deleted.parent.key;
                                deleted.value = deleted.parent.value;
                                deleted = deleted.parent;
                                deleteHelper(deleted);
                            }
                        }
                    } else { // if the is on the right
                        if(deleted.parent.left == null){
                            deleted.parent.color = Node.COLOR.BLACK;
                            deleted.parent.right = null;
                        }
                        else if (deleted.parent.left.color == Node.COLOR.RED) { // if the sibling of is red
                            if((deleted.parent.left.left == null) && (deleted.parent.left.right == null)){ // if the sibling has no child
                                deleted.parent.right = null;
                            }
                            else {
                                deleted.parent.left.color = Node.COLOR.BLACK;
                                deleted.parent.color = Node.COLOR.RED;
                                rotateRight(deleted.parent);
                                deleteHelper(deleted);
                            }
                        } else { // if the sibling is black
                            if ((deleted.parent.left.left != null) && (deleted.parent.left.left.color == Node.COLOR.RED)) {
                                Node.COLOR temp = deleted.parent.color;
                                deleted.parent.color = deleted.parent.right.color;
                                deleted.parent.left.color = temp;
                                deleted.parent.left.left.color = Node.COLOR.BLACK;
                                rotateRight(deleted.parent);
                                deleteHelper(deleted);
                            } else if ((deleted.parent.left.left != null) && (deleted.parent.left.left.color == Node.COLOR.BLACK)
                                    && (deleted.parent.left.right != null) && (deleted.parent.left.right.color == Node.COLOR.RED)) {
                                deleted.parent.left.color = Node.COLOR.RED;
                                deleted.parent.left.right.color = Node.COLOR.BLACK;
                                rotateLeft(deleted.parent.left);
                                deleteHelper(deleted);
                            } else {
                                deleted.parent.left.color = Node.COLOR.RED;
                                deleted.key = deleted.parent.key;
                                deleted.value = deleted.parent.value;
                                deleted = deleted.parent;
                                deleteHelper(deleted);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Balance the red-black-tree after insertion.
     * @param current the inserted node from bottom to the top
     */
    public void balance(Node<K, V> current) {
        if (current.parent == null) {
            current.color = Node.COLOR.BLACK;
        } else if (current.parent.color == Node.COLOR.BLACK) {
            //nothing to do
        } else { // Since root node is not red, it must have grandparent node.
            //The uncle node exists and its color is red
            if ((current.parent.parent.left != null)
                    && (current.parent.parent.right != null)
                    && (current.parent.parent.left.key.compareTo(current.parent.key) == 0)
                    && (current.parent.parent.right.color == Node.COLOR.RED)) {
                current.parent.color = Node.COLOR.BLACK;
                current.parent.parent.right.color = Node.COLOR.BLACK;
                current.parent.parent.color = Node.COLOR.RED;
                balance(current.parent.parent);
            } else if ((current.parent.parent.left != null)
                    && (current.parent.parent.right != null)
                    && (current.parent.parent.right.key.compareTo(current.parent.key) == 0)
                    && (current.parent.parent.left.color == Node.COLOR.RED)) {
                current.parent.color = Node.COLOR.BLACK;
                current.parent.parent.left.color = Node.COLOR.BLACK;
                current.parent.parent.color = Node.COLOR.RED;
                balance(current.parent.parent);
            }
            //The uncle node does not exist or its color is black
            //The parent node is on the left
            else if ((current.parent.parent.left != null) && (current.parent.parent.left.key.compareTo(current.parent.key)) == 0) {
                if ((current.parent.left != null) && (current.parent.left.key.compareTo(current.key)) == 0) { // the node is on the left
                    current.parent.color = Node.COLOR.BLACK;
                    current.parent.parent.color = Node.COLOR.RED;
                    rotateRight(current.parent.parent);
                } else {
                    rotateLeft(current.parent);
                    current.color = Node.COLOR.BLACK;
                    current.parent.color = Node.COLOR.RED;
                    rotateRight(current.parent);
                }
            }
            //The parent node is on the right
            else if ((current.parent.parent.right != null) && (current.parent.parent.right.key.compareTo(current.parent.key)) == 0) {
                if ((current.parent.left != null) && (current.parent.left.key.compareTo(current.key)) == 0) { // the node is on the left
                    rotateRight(current.parent);
                    current.color = Node.COLOR.BLACK;
                    current.parent.color = Node.COLOR.RED;
                    rotateLeft(current.parent);
                } else {
                    current.parent.color = Node.COLOR.BLACK;
                    current.parent.parent.color = Node.COLOR.RED;
                    rotateLeft(current.parent.parent);
                }
            }
        }
    }


    public void rotateLeft(Node<K, V> peak) {
        if (peak.right == null) {
            System.out.println("CANNOT rotateLeft (" + peak.key + "    " + peak.value.toString() + ")");
            return;
        }
        Node<K, V> newPeak = peak.right;
        if (peak.parent != null) {
            if ((peak.parent.left != null) && (peak.parent.left.key.compareTo(peak.key)) == 0) {
                peak.parent.left = newPeak;
            } else {
                peak.parent.right = newPeak;
            }
        } else {
            root = newPeak;
        }

        Node<K, V> tempPeakParent = peak.parent;
        peak.right = newPeak.left;
        if (peak.right != null) {
            peak.right.parent = peak;
        }
        peak.parent = newPeak;
        newPeak.left = peak;
        newPeak.parent = tempPeakParent;
    }

    public void rotateRight(Node<K, V> peak) {
        if (peak.left == null) {
            System.out.println("CANNOT rotateRight (" + peak.key + "    " + peak.value.toString() + ")");
            return;
        }
        Node<K, V> newPeak = peak.left;
        if (peak.parent != null) {
            if ((peak.parent.left != null) && (peak.parent.left.key.compareTo(peak.key)) == 0) {
                peak.parent.left = newPeak;
            } else {
                peak.parent.right = newPeak;
            }
        } else {
            root = newPeak;
        }

        Node<K, V> tempPeakParent = peak.parent;
        peak.left = newPeak.right;
        if (peak.left != null) {
            peak.left.parent = peak;
        }
        peak.parent = newPeak;
        newPeak.right = peak;
        newPeak.parent = tempPeakParent;
    }

    /**
     * Print the tree.
     */
    public void show() {
        System.out.println("Size of the tree: " + size);
        preOrderPrint(root, 0);
    }

    private void preOrderPrint(Node<K, V> current, int indent) {
        for (int i = 0; i < indent; i++) {
            System.out.print("    ");
        }
        if (current == null) {
            System.out.println("null");
        } else {
            System.out.println(current.key.toString() + "    " + current.color + "    " + current.value.toString());
            preOrderPrint(current.left, (indent + 1));
            preOrderPrint(current.right, (indent + 1));
        }
    }

}
