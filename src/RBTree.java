public class RBTree<V> {

    protected Node<V> root;
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
    public V search(int key) {
        Node<V> temp = searchHelper(key, root);
        if (temp == null) {
            return null;
        }
        return temp.value;
    }

    private Node<V> searchHelper(int key, Node<V> current) {
        if (current == null) {
            return null;
        } else if (key < current.key) {
            return searchHelper(key, current.left);
        } else if (key > current.key) {
            return searchHelper(key, current.right);
        } else {
            return current;
        }
    }

    /**
     * get the minimum key
     *
     * @return the minimum key (-1 if the tree is empty)
     */
    public int min() {
        Node<V> temp = minHelper(root);
        if (temp == null) {
            return -1;
        }
        return minHelper(root).key;
    }

    private Node<V> minHelper(Node<V> current) {
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
    public int max() {
        Node<V> temp = maxHelper(root);
        if (temp == null) {
            return -1;
        }
        return maxHelper(root).key;
    }

    private Node<V> maxHelper(Node<V> current) {
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
    public void insert(int key, V value) {
        //if the tree is empty;
        if (root == null) {
            root = new Node<>(null, null, null, key, value);
            root.color = Node.COLOR.BLACK;
            size++;
            System.out.println("Successfully inserted the tuple with key: " + key);
        } else {
            Node<V> current = root;
            while (true) {
                if (key < current.key) {
                    if (current.left == null) { // insert
                        current.left = new Node<>(current, null, null, key, value);
                        size++;
                        balance(current.left);
                        System.out.println("Successfully inserted the tuple with key: " + key);
                        break;
                    } else {
                        current = current.left;
                    }
                } else if (key > current.key) {
                    if (current.right == null) { // insert
                        current.right = new Node<>(current, null, null, key, value);
                        size++;
                        balance(current.right);
                        System.out.println("Successfully inserted the tuple with key: " + key);
                        break;
                    } else {
                        current = current.right;
                    }
                } else { //when key is exactly the same, update the value.
                    current.value = value;
                    System.out.println("Successfully updated the tuple with key: " + key);
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
    public void delete(int key) {
        Node<V> search = searchHelper(key, root);
        if (search == null) {
            System.out.println("The key does not exist: " + key);
        } else {
            deleteHelper(search);
            System.out.println("Successfully deleted the tuple with key: " + key);
            size--;
        }
    }

    private void deleteHelper(Node<V> search) {
        //TODO: fix the bug if search is root
        if ((search.left == null) && (search.right == null)) { // the deleted node has no child
            if ((search.parent.left != null) && (search.parent.left.key == search.key)) {
                search.parent.left = null;
            } else {
                search.parent.right = null;
            }
        } else if ((search.left != null) && (search.right == null)) { // the deleted node has only left child
            search.key = search.left.key;
            search.value = search.left.value;
            search.left = null;
        } else if (search.left == null) { // the deleted node has only right child
            search.key = search.right.key;
            search.value = search.right.value;
            search.right = null;
        } else { // the deleted node has two child
            Node<V> sub = minHelper(search.right);
            if (sub.color == Node.COLOR.RED) {
                search.key = sub.key;
                search.value = sub.value;
                deleteHelper(sub);
            } else {
                if ((sub.parent.left != null) && (sub.parent.left.key == sub.key)) { // if sub is on the left
                    if (sub.parent.right.color == Node.COLOR.RED) { // if the sibling of sub is red
                        sub.parent.right.color = Node.COLOR.BLACK;
                        sub.parent.color = Node.COLOR.RED;
                        rotateLeft(sub.parent);
                        deleteHelper(sub);
                    } else {
                        //TODO: make sure parent.right has two children
                        if(sub.parent.right.right.color == Node.COLOR.RED){
                            Node.COLOR temp = sub.parent.color;
                            sub.parent.color = sub.parent.right.color;
                            sub.parent.right.color = temp;
                            sub.parent.right.right.color = Node.COLOR.BLACK;
                            rotateLeft(sub.parent);
                            deleteHelper(sub);
                        }
                        else if ((sub.parent.right.right.color == Node.COLOR.BLACK) && (sub.parent.right.left.color == Node.COLOR.RED)) {
                            sub.parent.right.color = Node.COLOR.RED;
                            sub.parent.right.left.color = Node.COLOR.BLACK;
                            rotateRight(sub.parent.right);
                            deleteHelper(sub);
                        }
                        else {
                            sub.parent.right.color = Node.COLOR.RED;
                            sub = sub.parent;
                            deleteHelper(sub);
                        }
                    }
                }
                else {
                    assert sub.parent.left != null;
                    if (sub.parent.left.color == Node.COLOR.RED) { // if the sibling of sub is red
                        sub.parent.left.color = Node.COLOR.BLACK;
                        sub.parent.color = Node.COLOR.RED;
                        rotateRight(sub.parent);
                        deleteHelper(sub);
                    } else {
                        if(sub.parent.left.left.color == Node.COLOR.RED){
                            Node.COLOR temp = sub.parent.color;
                            sub.parent.color = sub.parent.right.color;
                            sub.parent.left.color = temp;
                            sub.parent.left.left.color = Node.COLOR.BLACK;
                            rotateRight(sub.parent);
                            deleteHelper(sub);
                        }
                        else if ((sub.parent.right.right.color == Node.COLOR.BLACK) && (sub.parent.right.left.color == Node.COLOR.RED)) {
                            sub.parent.left.color = Node.COLOR.RED;
                            sub.parent.left.right.color = Node.COLOR.BLACK;
                            rotateLeft(sub.parent.left);
                            deleteHelper(sub);
                        }
                        else {
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
    public void balance(Node<V> current) {
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

    public void rotateLeft(Node<V> peak) {
        if (peak.right == null) {
            System.out.println("CANNOT rotateLeft (" + peak.key + "    " + peak.value.toString() + ")");
            return;
        }
        System.out.println("rotateLeft (" + peak.key + "    " + peak.value.toString() + ")");
        Node<V> newPeak = peak.right;
        if (peak.parent != null) {
            if ((peak.parent.left != null) && (peak.parent.left.key == peak.key)) {
                peak.parent.left = newPeak;
            } else {
                peak.parent.right = newPeak;
            }
        } else {
            root = newPeak;
        }

        Node<V> tempPeakParent = peak.parent;
        peak.right = newPeak.left;
        if (peak.right != null) {
            peak.right.parent = peak;
        }
        peak.parent = newPeak;
        newPeak.left = peak;
        newPeak.parent = tempPeakParent;
    }

    public void rotateRight(Node<V> peak) {
        if (peak.left == null) {
            System.out.println("CANNOT rotateRight (" + peak.key + "    " + peak.value.toString() + ")");
            return;
        }
        System.out.println("rotateRight (" + peak.key + "    " + peak.value.toString() + ")");
        Node<V> newPeak = peak.left;
        if (peak.parent != null) {
            if ((peak.parent.left != null) && (peak.parent.left.key == peak.key)) {
                peak.parent.left = newPeak;
            } else {
                peak.parent.right = newPeak;
            }
        } else {
            root = newPeak;
        }

        Node<V> tempPeakParent = peak.parent;
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

    private void preOrderPrint(Node<V> current, int indent) {
        if (current != null) {
            for (int i = 0; i < indent; i++) {
                System.out.print("    ");
            }
            System.out.println(current.key + "    " + current.color + "    value: " + current.value.toString());

            if (current.left != null)
                preOrderPrint(current.left, (indent + 1));
            if (current.right != null)
                preOrderPrint(current.right, (indent + 1));
        }
    }


}
