public class RBTree<V> {

    protected Node root;
    protected int size;

    public RBTree(){
        root = null;
        size = 0;
    }

    public V search(int key){
        return searchHelper(key, root);
    }

    @SuppressWarnings("unchecked cast")
    private V searchHelper(int key, Node current){
        if(current == null){
            System.out.println("Cannot found value with key: " + key);
            return null;
        }
        else if(key < current.key){
            return searchHelper(key, current.left);
        }
        else if(key > current.key){
            return searchHelper(key, current.right);
        }
        else {
            return (V)current.value;
        }
    }

    @SuppressWarnings("unchecked cast")
    public void insert(int key, V value){
        //if the tree is empty;
        if(root == null){
            root = new Node(null, null, null, key, value);
            root.color = Node.COLOR.BLACK;
            size ++;
        }
        else{
            Node tempRoot = root;
            Node tempRootParent = null;
            while(tempRoot != null){
                tempRootParent = tempRoot;
                if(key < tempRoot.key){
                    tempRoot = tempRoot.left;
                }
                else if(key > tempRoot.key){
                    tempRoot = tempRoot.right;
                }
                else { //when key is exactly the same, update the value.
                    tempRoot.value = value;
                    break;
                }
            }

            tempRoot = new Node(tempRootParent, null, null, key, value);
            if(tempRoot.key < tempRootParent.key){
                tempRootParent.left = tempRoot;
            }
            else if(tempRoot.key > tempRootParent.key){
                tempRootParent.right = tempRoot;
            }
            size ++;
            balance(tempRoot);
        }
    }

    public void delete(int key){
        //TODO
    }

    public void balance(Node current){
        if(current.parent.color == Node.COLOR.BLACK){
            //nothing to do.
        }
        else {
            //TODO
        }
    }

    public void rotateLeft(Node pin){
        //TODO
    }

    public void rotateRight(Node pin){
        //TODO
    }

    /**
     * Pre-order print the tree.
     */
    public void show(){
        System.out.println("size: " + size);
        preOrderPrint(root, 0);
    }

    private void preOrderPrint(Node current, int indent){
        if(current != null){
            for(int i = 0; i < indent; i++){
                System.out.print("    ");
            }
            System.out.println(current.key + "    " + current.color +  "    value: " + current.value.toString());

            if(current.left != null)
                preOrderPrint(current.left, (indent+1));
            if(current.right != null)
                preOrderPrint(current.right, (indent+1));
        }
    }


}
