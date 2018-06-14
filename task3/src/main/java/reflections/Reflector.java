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

    /**
     *  method uses standard reflection function
     * @return string representation of declared fields names with annotations, modifiers and types
     */
    String getFields();

    /**
     * method uses standard reflection function
     * @return string representation of declared methods names with annotations, modifiers, parameters and returned types
     */
    String getMethods();

    /**
     *  method uses standard reflection function
     * @return string representation of constructors with modifiers and parameters
     */
    String getConstructors();

    /**
     * method set class to class field
     * @param clazz
     */
    void setClass(Class clazz);

    /**
     * method prints all class features, like fields, methods and constructors ordered as common code
     */
    void printClass();
}
