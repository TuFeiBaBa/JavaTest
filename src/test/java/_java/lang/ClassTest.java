package _java.lang;

import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static testutil.Print.print;

/**
 * @author tufei
 * @date 2018/1/20.
 */

public final class ClassTest {

    static class Example {
        public Example() {}
        public Example(String name) {}
        static {
            print("Initialization successful!");
        }
    }

    /**
     * native boolean isInterface()
     * <p>
     * native关键字：Native Method就是一个java调用非java代码的接口。在定义一个native method时，
     * 并不提供实现体（有些像定义一个java interface），因为其实现体是由非java语言在外面实现的。
     * native可以与所有其它的java标识符连用，但是abstract除外。这是合理的，因为native暗示这些
     * 方法是有实现体的，只不过这些实现体是非java的。
     */
    @Test
    public void testIsInterface() {
        assertEquals(false, Object.class.isInterface());

        assertEquals(true, List.class.isInterface());
    }

    /**
     * Class<?>[] getInterfaces()
     * <p>
     * 如果此对象表示一个类/接口，则该数组包含对象代表类/接口（实现/扩展）的所有接口。
     * 数组中的接口对象的顺序对应于接口声明的顺序。
     * <p>
     * 如果此对象代表类/接口，没有实现/扩展接口，如果此对象是基本类型或Void，就返回一个空数组（不是null，注意）。
     * <p>
     * 如果此对象代表一个数组类型，Cloneable和java.io.Serializable接口将会被返回。
     */
    @Test
    public void testGetInterfaces() {
        Class<?>[] arrayListInterfaces = ArrayList.class.getInterfaces();
        assertEquals(4, arrayListInterfaces.length);
        assertEquals(List.class, arrayListInterfaces[0]);
        assertEquals(RandomAccess.class, arrayListInterfaces[1]);

        Class<?>[] listInterfaces = List.class.getInterfaces();
        assertEquals(1, listInterfaces.length);
        assertEquals(Collection.class, listInterfaces[0]);

        Class<?>[] objectInterfaces = Object.class.getInterfaces();
        assertEquals(0, objectInterfaces.length);
        Class<?>[] mapInterfaces = Map.class.getInterfaces();
        assertEquals(0, mapInterfaces.length);

        Class<?>[] integerInterfaces = Integer.class.getInterfaces();
        assertEquals(1, integerInterfaces.length);
        assertEquals(Comparable.class, integerInterfaces[0]);

        Class<?>[] intInterfaces = int.class.getInterfaces();
        assertEquals(0, intInterfaces.length);

        //或者void.class，似乎没区别
        Class<?>[] voidInterfacees = Void.class.getInterfaces();
        assertEquals(0, voidInterfacees.length);

        Class<?>[] arrayIntInterfaces = int[].class.getInterfaces();
        assertEquals(2, arrayIntInterfaces.length);
        assertEquals(Cloneable.class, arrayIntInterfaces[0]);
        assertEquals(Serializable.class, arrayIntInterfaces[1]);

        Class<?>[] objectIntInterfaces = Object[].class.getInterfaces();
        assertEquals(2, objectIntInterfaces.length);
        assertEquals(Cloneable.class, objectIntInterfaces[0]);
        assertEquals(Serializable.class, objectIntInterfaces[1]);
    }

    /**
     * static Class<?> forName(String className)
     * 注意，参数必须是所需类的完全限定名称。
     * <p>
     * 主要功能：
     * 1.返回一个指定的类或接口的Class实例
     * 2.要求JVM查找并加载指定的类,执行静态初始化
     */
    @Test
    public void testForNameForInitialization() throws ClassNotFoundException {
        //如果是嵌套类或内部类的完全限定名称，要用到$
        Class.forName("_java.lang.ClassTest$Example");
    }

    /**
     * Method[] getDeclaredMethods()
     * <p>
     * 1.会返回类/接口的非构造函数的方法，包括private方法、静态方法
     * 2.如果此Class对象没有实现任何方法的类/接口，会返回一个空数组
     * 3.如果此Class对象是一个数组，基本类型或者void，会返回一个空数组
     *
     * 注意：返回数组里的元素排序不固定，每次调用获得的排序结果可能都不一样
     */
    @Test
    public void testGetDeclaredMethods() {
        Method[] methods = Example.class.getDeclaredMethods();
        assertEquals(0, methods.length);
    }

    /**
     * 和getDeclaredMethods()不同，getMethods返回某个类的所有public
     * 方法包括其继承类的public方法(包括其继承类的继承类的继承类...)，当然也包括它所实现接口的方法。
     * <p>
     * 注意：
     * 1.getMethods不会返回除public以外的方法
     * 2.getDeclaredMethods()不会返回其继承类的任何方法
     */
    @Test
    public void testGetDeclaredMethodsAndGetMethods() {
        class Dad {
            protected void protectedDadMethod() {}
            public void publicDadMethod() {}
        }

        class Example extends Dad {
            private void privateMethod() {}
            public void publicMethod() {}
        }

        Method[] methods = Example.class.getMethods();
        //虽然源码注释说，获取到的methods没有固定的排序，但打印结果似乎不是，排序固定
        for(Method method:methods){
            print(method);
        }
        print("---------------------------------------------------");
        Method[] declaredMethods = Example.class.getDeclaredMethods();
        for (Method method:declaredMethods){
            print(method);
        }

        //注意，getMethods获取到的methods,由于可能是其父类的方法，所以可能导致下面这种情况
        Method method0 = Example.class.getMethods()[0];
        assertEquals(Example.class.getSimpleName(),method0.getDeclaringClass().getSimpleName());
        Method method1 = Example.class.getMethods()[1];
        assertNotEquals(Example.class.getSimpleName(),method1.getDeclaringClass().getSimpleName());
        assertEquals(Dad.class.getSimpleName(),method1.getDeclaringClass().getSimpleName());
    }


    interface Base{}
    interface Derive extends Base{}
    class Dad implements Base{}
    class Son extends Dad implements Derive{}
    /**
     * boolean isAssignableFrom(Class<?> cls)
     * <p>
     * 如果该Class代表该类或接口，如果传入的参数class，是它们自身或者它们的
     * 子类(或者接口的实现类)、子接口，就返回true
     * 如果该Class代表基本类型，如果传入的参数class，是它们自身就返回true
     * 否则返回false
     * 如果传入的参数是null，抛空指针异常。
     */
    @Test
    public void testIsAssignableFrom(){
        assertTrue(Dad.class.isAssignableFrom(Dad.class));
        assertTrue(Dad.class.isAssignableFrom(Son.class));
        assertFalse(Dad.class.isAssignableFrom(Base.class));
        assertFalse(Son.class.isAssignableFrom(Dad.class));

        assertTrue(Base.class.isAssignableFrom(Derive.class));
        assertTrue(Base.class.isAssignableFrom(Dad.class));
        assertTrue(Base.class.isAssignableFrom(Son.class));
        assertTrue(Derive.class.isAssignableFrom(Son.class));
        assertFalse(Derive.class.isAssignableFrom(Base.class));

        assertTrue(int.class.isAssignableFrom(int.class));
        assertFalse(double.class.isAssignableFrom(int.class));

        try {
            assertTrue(Dad.class.isAssignableFrom(null));
        }catch (Exception e){
            assertEquals(NullPointerException.class,e.getClass());
        }
    }
}
