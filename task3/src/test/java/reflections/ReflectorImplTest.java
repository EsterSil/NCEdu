package reflections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

public class ReflectorImplTest {
    Reflector reflector;
    @Before
    public void impl(){
        reflector = new ReflectorImpl();
    }
    @Test
    public void getClassNameTest(){

        Class<?> zz = Integer.class;
        reflector.setClass(zz);
        String className  = reflector.getClassName();
        Assert.assertEquals("Integer", className);
    }
    @Test
    public void getSuperClassTest(){

        Class<?> zz = Integer.class;
        reflector.setClass(zz);
        String superclass = reflector.getSuperClass();
        Assert.assertEquals("Number", superclass);
    }
    @Test
    public void getPackageTest(){

        Class<?> zz = Integer.class;
        Class z = ReflectorImpl.class;
        reflector.setClass(zz);
        String packageName = reflector.getPackage();
        Assert.assertEquals("lang", packageName);
    }
    @Test
    public void getClassModifiersTest(){

        Class<?> zz = Integer.class;
        Class z = ReflectorImpl.class;
        reflector.setClass(zz);
        String modifiers = reflector.getClassModifiers();
        Assert.assertEquals("public final", modifiers);
    }
    @Test
    public void getImplementedInterfacesTest(){

        Class<?> zz = Integer.class;
        Class z = ReflectorImpl.class;
        reflector.setClass(zz);
        String interfaces = reflector.getImplementedInterfaces();
        Assert.assertEquals("Comparable ", interfaces);
    }
    @Test
    public void getMethodsTest(){

        Class<?> zz = Integer.class;
        Class z = ReflectorImpl.class;
        reflector.setClass(z);
        String methods = reflector.getMethods();
        System.out.print(methods);
    }
    @Test
    public void getFieldsTest(){

        Class<?> zz = Integer.class;
        Class z = ReflectorImpl.class;
        reflector.setClass(zz);
        String fields = reflector.getFields();

    }
    @Test
    public void getConstructorsTest(){

        Class<?> zz = Integer.class;
        Class z = ReflectorImpl.class;
        reflector.setClass(zz);
        String constructors = reflector.getConstructors();
        System.out.print(constructors);
    }
    @Test
    public void getPrintTest(){

        ReflectorImpl.print(TestClass.class);
    }
}
