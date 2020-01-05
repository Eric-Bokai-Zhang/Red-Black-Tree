package test;

public class Value {
    String str;

    public Value(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return "Value{str=\"" + str + "\"}";
    }
}
