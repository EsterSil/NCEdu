public interface Reflector {

    String getPackage();
    String getSuper();

    String getClassName();

    void setClass(Class clazz);

    String getMethods();

    void printClass();
}
