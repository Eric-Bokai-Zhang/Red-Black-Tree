public class test {
    public static void main(String args[]){
        RBTree<String> tree = new RBTree<>();
        tree.insert(5, "five");
        tree.insert(3, "three");
        tree.insert(1, "one");
        tree.insert(2, "two");
        tree.insert(4, "four");
        tree.insert(6, "six");
        tree.insert(7, "seven");
        tree.insert(8, "eight");
        tree.insert(1, "ONE");

        tree.show();

        System.out.println(tree.search(1));
        System.out.println(tree.search(2));
        System.out.println(tree.search(10));

        tree.delete(7);
        tree.delete(8);
        tree.delete(1);

        tree.show();

    }
}
