public class RBTree<V> {

    protected Node root;
    protected int size;

    public RBTree() {
        root = null;
        size = 0;
    }

    /**
     * search a tuple(pair) given the key
     * @param key key of the tuple
     * @return value of the tuple. If not found, return null.
     */
    public V search(int key) {
        return searchHelper(key, root);
    }

    @SuppressWarnings("unchecked cast")
    private V searchHelper(int key, Node current) {
        if (current == null) {
            System.out.println("Cannot found value with key: " + key);
            return null;
        } else if (key < current.key) {
            return searchHelper(key, current.left);
        } else if (key > current.key) {
            return searchHelper(key, current.right);
        } else {
            return (V) current.value;
        }
    }

    /**
     * insert a tuple(pair) of key and value into the tree (replace value if the key exists)
     * @param key key of the tuple
     * @param value value of the tuple
     */
    @SuppressWarnings("unchecked cast")
    public void insert(int key, V value) {
        //if the tree is empty;
        if (root == null) {
            root = new Node(null, null, null, key, value);
            root.color = Node.COLOR.BLACK;
            size++;
        } else {
            Node current = root;
            while (true) {
                if (key < current.key) {
                    if(current.left == null){ // insert
                        current.left = new Node(current, null, null, key, value);
                        size++;
                        balance(current.left);
                        break;
                    }
                    else {
                        current = current.left;
                    }
                } else if (key > current.key) {
                    if(current.right == null){ // insert
                        current.right = new Node(current, null, null, key, value);
                        size++;
                        balance(current.right);
                        break;
                    }
                    else {
                        current = current.right;
                    }
                } else { //when key is exactly the same, update the value.
                    current.value = value;
                    break;
                }
            }
        }
    }

    /**
     * delete the tuple(pair) given the key
     * @param key key of the tuple
     */
    public void delete(int key) {
        //TODO
    }

    /**
     * Balance the red-black-tree.
     * @param current the inserted node from bottom to the top
     */
    public void balance(Node current) {
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

    public void rotateLeft(Node peak) {
        if (peak.right == null) {
            System.out.println("CANNOT rotateLeft (" + peak.key + "    " + peak.value.toString() + ")");
            return;
        }
        System.out.println("rotateLeft (" + peak.key + "    " + peak.value.toString() + ")");
        Node newPeak = peak.right;
        if (peak.parent != null) {
            if ((peak.parent.left != null) && (peak.parent.left.key == peak.key)) {
                peak.parent.left = newPeak;
            } else {
                peak.parent.right = newPeak;
            }
        } else {
            root = newPeak;
        }

        Node tempPeakParent = peak.parent;
        peak.right = newPeak.left;
        if (peak.right != null) {
            peak.right.parent = peak;
        }
        peak.parent = newPeak;
        newPeak.left = peak;
        newPeak.parent = tempPeakParent;
    }

    public void rotateRight(Node peak) {
        if (peak.left == null) {
            System.out.println("CANNOT rotateRight (" + peak.key + "    " + peak.value.toString() + ")");
            return;
        }
        System.out.println("rotateRight (" + peak.key + "    " + peak.value.toString() + ")");
        Node newPeak = peak.left;
        if (peak.parent != null) {
            if ((peak.parent.left != null) && (peak.parent.left.key == peak.key)) {
                peak.parent.left = newPeak;
            } else {
                peak.parent.right = newPeak;
            }
        } else {
            root = newPeak;
        }

        Node tempPeakParent = peak.parent;
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

    private void preOrderPrint(Node current, int indent) {
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
