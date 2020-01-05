package core;

public class RBTree<K extends Comparable<K>, V> {

    protected Node<K, V> root;
    protected int size;

    public RBTree() {
        root = null;
        size = 0;
    }

    /**
     * search a tuple(pair) given the key
     *
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
     * get the minimum key
     *
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
     * get the maximum key
     *
     * @return the maximum key (-1 if the tree is empty)
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
     * insert a tuple(pair) of key and value into the tree (replace value if the key exists)
     *
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
     * delete the tuple(pair) given the key
     *
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
        //TODO: fix the bug if delete the root
        if ((deleted.left == null) && (deleted.right == null)) { // the deleted node has no child
            if ((deleted.parent.left != null) && (deleted.parent.left.key == deleted.key)) {
                deleted.parent.left = null;
            } else {
                deleted.parent.right = null;
            }
        } else if ((deleted.left != null) && (deleted.right == null)) { // the deleted node has only left child
            deleted.key = deleted.left.key;
            deleted.value = deleted.left.value;
            deleted.left = null;
        } else if (deleted.left == null) { // the deleted node has only right child
            deleted.key = deleted.right.key;
            deleted.value = deleted.right.value;
            deleted.right = null;
        } else { // the deleted node has two child
            Node<K, V> sub = minHelper(deleted.right);
            if (sub.color == Node.COLOR.RED) {
                deleted.key = sub.key;
                deleted.value = sub.value;
                deleteHelper(sub);
            } else {
                if ((sub.parent.left != null) && (sub.parent.left.key == sub.key)) { // if sub is on the left
                    if (sub.parent.right.color == Node.COLOR.RED) { // if the sibling of sub is red
                        sub.parent.right.color = Node.COLOR.BLACK;
                        sub.parent.color = Node.COLOR.RED;
                        rotateLeft(sub.parent);
                        deleteHelper(sub);
                    } else {
                        if ((sub.parent.right.right != null) && (sub.parent.right.right.color == Node.COLOR.RED)) {
                            Node.COLOR temp = sub.parent.color;
                            sub.parent.color = sub.parent.right.color;
                            sub.parent.right.color = temp;
                            sub.parent.right.right.color = Node.COLOR.BLACK;
                            rotateLeft(sub.parent);
                            deleteHelper(sub);
                        } else if ((sub.parent.right.right != null) && (sub.parent.right.right.color == Node.COLOR.BLACK) && (sub.parent.right.left != null) && (sub.parent.right.left.color == Node.COLOR.RED)) {
                            sub.parent.right.color = Node.COLOR.RED;
                            sub.parent.right.left.color = Node.COLOR.BLACK;
                            rotateRight(sub.parent.right);
                            deleteHelper(sub);
                        } else {
                            sub.parent.right.color = Node.COLOR.RED;
                            sub = sub.parent;
                            deleteHelper(sub);
                        }
                    }
                } else {
                    assert sub.parent.left != null;
                    if (sub.parent.left.color == Node.COLOR.RED) { // if the sibling of sub is red
                        sub.parent.left.color = Node.COLOR.BLACK;
                        sub.parent.color = Node.COLOR.RED;
                        rotateRight(sub.parent);
                        deleteHelper(sub);
                    } else {
                        if ((sub.parent.left.left != null) && (sub.parent.left.left.color == Node.COLOR.RED)) {
                            Node.COLOR temp = sub.parent.color;
                            sub.parent.color = sub.parent.right.color;
                            sub.parent.left.color = temp;
                            sub.parent.left.left.color = Node.COLOR.BLACK;
                            rotateRight(sub.parent);
                            deleteHelper(sub);
                        } else if ((sub.parent.left.left != null) && (sub.parent.left.left.color == Node.COLOR.BLACK) && (sub.parent.left.right != null) && (sub.parent.left.right.color == Node.COLOR.RED)) {
                            sub.parent.left.color = Node.COLOR.RED;
                            sub.parent.left.right.color = Node.COLOR.BLACK;
                            rotateLeft(sub.parent.left);
                            deleteHelper(sub);
                        } else {
                            sub.parent.left.color = Node.COLOR.RED;
                            sub = sub.parent;
                            deleteHelper(sub);
                        }
                    }
                }
            }
        }
    }

    /**
     * Balance the red-black-tree.
     *
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
                    && (current.parent.parent.left.key == current.parent.key)
                    && (current.parent.parent.right.color == Node.COLOR.RED)) {
                current.parent.color = Node.COLOR.BLACK;
                current.parent.parent.right.color = Node.COLOR.BLACK;
                current.parent.parent.color = Node.COLOR.RED;
                balance(current.parent.parent);
            } else if ((current.parent.parent.left != null)
                    && (current.parent.parent.right != null)
                    && (current.parent.parent.right.key == current.parent.key)
                    && (current.parent.parent.left.color == Node.COLOR.RED)) {
                current.parent.color = Node.COLOR.BLACK;
                current.parent.parent.left.color = Node.COLOR.BLACK;
                current.parent.parent.color = Node.COLOR.RED;
                balance(current.parent.parent);
            }
            //The uncle node does not exist or its color is black
            //The parent node is on the left
            else if ((current.parent.parent.left != null) && (current.parent.parent.left.key == current.parent.key)) {
                if ((current.parent.left != null) && (current.parent.left.key == current.key)) { // the node is on the left
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
            else if ((current.parent.parent.right != null) && (current.parent.parent.right.key == current.parent.key)) {
                if ((current.parent.left != null) && (current.parent.left.key == current.key)) { // the node is on the left
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
        System.out.println("rotateLeft (" + peak.key + "    " + peak.value.toString() + ")");
        Node<K, V> newPeak = peak.right;
        if (peak.parent != null) {
            if ((peak.parent.left != null) && (peak.parent.left.key == peak.key)) {
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
        System.out.println("rotateRight (" + peak.key + "    " + peak.value.toString() + ")");
        Node<K, V> newPeak = peak.left;
        if (peak.parent != null) {
            if ((peak.parent.left != null) && (peak.parent.left.key == peak.key)) {
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
        System.out.println("size: " + size);
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
