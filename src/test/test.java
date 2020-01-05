package test;

import core.RBTree;

public class test {
    public static void main(String[] args) {
        RBTree<Key, Value> tree = new RBTree<>();
        tree.insert(new Key(1), new Value("1"));
        tree.insert(new Key(2), new Value("2"));
        tree.insert(new Key(3), new Value("3"));
        tree.insert(new Key(4), new Value("4"));
        tree.insert(new Key(5), new Value("5"));

        tree.show();

        System.out.println(tree.search(new Key(1)));
        System.out.println(tree.search(new Key(2)));
        System.out.println(tree.search(new Key(3)));

        tree.delete(new Key(1));
        tree.delete(new Key(3));
        tree.delete(new Key(5));

        tree.show();

    }
}
