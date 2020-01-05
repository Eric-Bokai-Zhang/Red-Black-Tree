package test;

public class Key implements Comparable<Key> {
    int number;

    public Key(int number) {
        this.number = number;
    }

    @Override
    public int compareTo(Key o) {
        return this.number - o.number;
    }

    @Override
    public String toString() {
        return "Key{number=" + number + "}";
    }
}
