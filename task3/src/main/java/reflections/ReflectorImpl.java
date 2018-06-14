package reflections;


import java.lang.annotation.Annotation;
import java.lang.reflect.*;

public class ReflectorImpl implements Reflector {

    private Class clazz = null;


    private String getNameByDelimiter(String fullName) {
        String delimiter = "\\.";
        String[] parts = fullName.split(delimiter);
        return parts[parts.length - 1];
    }

    public String getPackage() {
        return clazz.getPackage().getName();
    }

    public String getSuperClass() {
        return getNameByDelimiter(clazz.getSuperclass().getName());
    }

    public String getClassModifiers() {
        return Modifier.toString(clazz.getModifiers());
    }


    static void print(Class clazz) {
        ReflectorImpl ri = new ReflectorImpl();
        ri.setClass(clazz);
        ri.printClass();
    }

    public String getClassName() {
        return getNameByDelimiter(clazz.getName());
    }

    public String getImplementedInterfaces() {
        Class<?>[] interfaces = clazz.getInterfaces();
        StringBuilder builder = new StringBuilder();
        for (Class i : interfaces) {
            builder.append(getNameByDelimiter(i.getName()));
            builder.append(", ");
        }
        builder.deleteCharAt(builder.length() - 2);
        return builder.toString();
    }

    private void addIfArray(StringBuilder builder, Type type) {

    }
    @Override
    public String getFields() {
        Field[] fields = clazz.getDeclaredFields();

        StringBuilder builder = new StringBuilder();
        for (Field f : fields) {
            Annotation[] annotations = f.getAnnotations();
            for (Annotation a : annotations) {
                builder.append("@");
                builder.append(getNameByDelimiter(a.toString()));
                builder.append("\n");
            }
            int modifiers = f.getModifiers();
            builder.append(Modifier.toString(modifiers));
            builder.append(" ");
            Type type = f.getType();
            if (f.getType().isArray()) {
                Class component = f.getType().getComponentType();
                builder.append(getNameByDelimiter(component.getName()));
                builder.append("[] ");
            } else {
                if (type.toString().startsWith("class")) {
                    builder.append(getNameByDelimiter(type.toString()));
                } else {
                    builder.append(getNameByDelimiter(type.toString()));
                }
                builder.append(" ");
            }
            builder.append(getNameByDelimiter(f.getName()));
            builder.append("; \n");

        }
        return builder.toString();
    }


    public void setClass(Class clazz) {
        this.clazz = clazz;
    }

    public String getMethods() {
        StringBuilder builder = new StringBuilder();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method m : methods) {

            Annotation[] annotations = m.getDeclaredAnnotations();
            for (Annotation a : annotations) {
                builder.append("@");
                builder.append(getNameByDelimiter(a.toString()));
                builder.append("\n");
            }
            int modifiers = m.getModifiers();
            builder.append(Modifier.toString(modifiers));
            builder.append(" ");
            Type type = m.getGenericReturnType();
            if (type.toString().startsWith("class")) {
                builder.append(getNameByDelimiter(type.toString()));
            } else {
                builder.append(type.toString());
            }
            builder.append(" ");
            builder.append(getNameByDelimiter(m.getName()));
            builder.append(" ( ");
            Type[] types = m.getGenericParameterTypes();
            if (types.length != 0) {
                for (Type t : types) {
                    if (t instanceof Class) {
                        Class<?> zz = (Class<?>) t;
                        if (zz.isArray()) {
                            Class component = zz.getComponentType();
                            builder.append(getNameByDelimiter(component.getName()));
                            builder.append("[] ");
                        } else {
                            if (t.toString().startsWith("class")) {
                                builder.append(getNameByDelimiter(t.toString()));
                            } else {
                                builder.append(t.toString());
                            }
                        }
                    }
                    builder.append(", ");
                }
                builder.deleteCharAt(builder.length() - 2);
            }
            builder.append(" ); \n");
        }
        return builder.toString();
    }

    @Override
    public String getConstructors() {
        StringBuilder builder = new StringBuilder();
        Constructor[] constructors = clazz.getConstructors();
        for (Constructor c : constructors) {
            builder.append(Modifier.toString(c.getModifiers()));
            builder.append(" ");
            builder.append(getNameByDelimiter(c.getName()));
            builder.append(" ( ");
            Type[] types = c.getGenericParameterTypes();
            if (types.length != 0) {
                for (Type t : types) {
                    if (t instanceof Class) {
                        Class<?> zz = (Class<?>) t;
                        if (zz.isArray()) {
                            Class component = zz.getComponentType();
                            builder.append(getNameByDelimiter(component.getName()));
                            builder.append("[] ");
                        } else {
                            if (t.toString().startsWith("class")) {
                                builder.append(getNameByDelimiter(t.toString()));
                            } else {
                                builder.append(t.toString());
                            }
                        }
                    }
                    builder.append(", ");
                }
                builder.deleteCharAt(builder.length() - 2);
            }
            builder.append(" );\n");
        }
        return builder.toString();
    }

    public void printClass() {
        StringBuilder stringBuilder = new StringBuilder();
        if (getPackage() != null) {
            stringBuilder.append("import ");
            stringBuilder.append(getPackage());
            stringBuilder.append(";\n");
        }
        if (getClassModifiers() != null) {
            stringBuilder.append(getClassModifiers());
        }
        if (getClassName() != null) {
            stringBuilder.append("class ");
            stringBuilder.append(getClassName());
        }
        if ((getSuperClass() != null) && (!getSuperClass().equals("Object"))) {
            stringBuilder.append(" extends ");
            stringBuilder.append(getSuperClass());
        }
        if (getImplementedInterfaces() != null) {
            stringBuilder.append(" implements ");
            stringBuilder.append(getImplementedInterfaces());
        }
        stringBuilder.append("{\n");
        stringBuilder.append(getFields());
        stringBuilder.append(getConstructors());
        stringBuilder.append(getMethods());
        stringBuilder.append("}\n");

        System.out.print(stringBuilder.toString());
    }
}
