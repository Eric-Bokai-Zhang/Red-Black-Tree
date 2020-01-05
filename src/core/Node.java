package core;

public class Node<K extends Comparable<K>, V> {
    protected Node<K, V> parent;
    protected Node<K, V> left;
    protected Node<K, V> right;
    protected COLOR color;
    protected K key;
    protected V value;

    public enum COLOR {
        RED,
        BLACK
    }

    public Node(Node<K, V> parent, Node<K, V> left, Node<K, V> right, K key, V value) {
        this.parent = parent;
        this.left = left;
        this.right = right;
        color = COLOR.RED;
        this.key = key;
        this.value = value;
    }


}
