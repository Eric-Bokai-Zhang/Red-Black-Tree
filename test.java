public class test {
    public static void main(String args[]){
        RBTree<String> tree = new RBTree<>();
        tree.insert(5, "five");
        tree.insert(2, "two");
        tree.insert(3, "three");
        tree.insert(6, "six");
        tree.insert(1, "one");

        tree.show();

        System.out.println(tree.search(3));
        System.out.println(tree.search(5));
        System.out.println(tree.search(1));

    }
}
