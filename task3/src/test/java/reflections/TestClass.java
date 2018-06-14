package reflections;

import java.io.Serializable;
import java.util.List;

class TestClass implements Serializable, Cloneable {
    private int field;
    private int[] ff;
    protected String[] strings;
    private static final Long serialNumber = 500L;
    public List array;

    public TestClass() {

    }

    public TestClass(Integer field) {
        this.field = field;

    }

    @Deprecated
    protected static void method(String[] params) { }


    public void foo() {
        System.out.println("FOO");
    }

    @Override
    public String toString() {
        return "Test{" +
                "field=" + field +
                '}';
    }
}