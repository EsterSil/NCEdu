public class ReflectorImpl implements Reflector {

    private Class clazz = null;

    static void print(Class clazz){
        ReflectorImpl ri = new ReflectorImpl();
        ri.setClass(clazz);
        ri.printClass();
    }

    public String getClassName() {
        return null;
    }


    public void setClass(Class clazz) {
        this.clazz = clazz;
    }

    public String getMethods() {
        return null;
    }

    public void printClass() {

    }
}
