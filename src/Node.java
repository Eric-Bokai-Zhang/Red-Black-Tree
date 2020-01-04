
public class Node<V> {
    protected Node parent;
    protected Node left;
    protected Node right;
    protected COLOR color;
    protected int key;
    protected V value;

    public enum COLOR{
        RED,
        BLACK
    }

    public Node(Node parent, Node left, Node right, int key, V value){
        this.parent = parent;
        this.left = left;
        this.right = right;
        color = COLOR.RED;
        this.key = key;
        this.value = value;
    }



}
