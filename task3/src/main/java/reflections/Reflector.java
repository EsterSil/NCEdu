package reflections;

public interface Reflector {
    /**
     * method uses standard reflection function
     * @return string representation of package name
     */
    String getPackage();
    /**
     * method uses standard reflection function
     * @return string representation of Super Class name
     */
    String getSuperClass();
    /**
     * method uses standard reflection function
     * @return string representation of modifiers names
     */
    String getClassModifiers();
    /**
     * method uses standard reflection function
     * @return string representation of Class name
     */
    String getClassName();
    /**
     * method uses standard reflection function
     * @return string representation of interfaces names
     */
    String getImplementedInterfaces();
    String getFields();
    String getMethods();
    String getConstructors();
    void setClass(Class clazz);

    void printClass();
}
