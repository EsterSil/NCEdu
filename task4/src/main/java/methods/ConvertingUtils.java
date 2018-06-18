package methods;

public interface ConvertingUtils<T> {

    /**
     * method converts targetObject, instance of targetClass, to byte stream and than store to the destinationDirectory
     * in memory in specified format in the destinationDirectory
     * @param targetClass the converted class
     * @param targetObject object, instance of targetClass
     * @param destinationDirectory
     * @return string representation of object
     */
    String serialize(Class<T> targetClass, T targetObject, String destinationDirectory);

    /**
     * method converts serialized object,  instance of targetClass, from destinationDirectory to Object
     * @param destinationDirectory
     * @param targetClass the class to convert
     */
    T deserialize(String destinationDirectory, Class<T> targetClass);
}
