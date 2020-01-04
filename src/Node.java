public class Node<V> {
    protected Node<V> parent;
    protected Node<V> left;
    protected Node<V> right;
    protected COLOR color;
    protected int key;
    protected V value;

    public enum COLOR{
        RED,
        BLACK
    }

    public Node(Node<V> parent, Node<V> left, Node<V> right, int key, V value){
        this.parent = parent;
        this.left = left;
        this.right = right;
        color = COLOR.RED;
        this.key = key;
        this.value = value;
    }



}
