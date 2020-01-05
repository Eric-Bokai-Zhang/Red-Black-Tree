package test;

import core.RBTree;

public class Demo {
    public static void main(String[] args) {
        //build and insert
        RBTree<Key, Value> tree = new RBTree<>();
        tree.insert(new Key(1), new Value("one"));
        tree.insert(new Key(2), new Value("two"));
        tree.insert(new Key(3), new Value("three"));
        tree.insert(new Key(4), new Value("four"));
        tree.insert(new Key(5), new Value("five"));

        //show
        System.out.println("\nThe tree: ");
        tree.show();

        //search
        System.out.println();
        System.out.println("Key: 1    " + tree.search(new Key(1)));
        System.out.println("Key: 4    " + tree.search(new Key(4)));

        //get minimum key
        System.out.println();
        System.out.println("Min: " + tree.min());

        //get maximum key
        System.out.println();
        System.out.println("Max: " + tree.max());

        //delete
        System.out.println();
        tree.delete(new Key(1));

        System.out.println("\nThe tree: ");
        tree.show();
    }
}
