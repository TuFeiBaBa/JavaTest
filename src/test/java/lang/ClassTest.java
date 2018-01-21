package lang;

import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;

import static org.junit.Assert.assertEquals;
import static util.Print.print;

/**
 * @author tufei
 * @date 2018/1/20.
 */

public final class ClassTest {

    static class Example {

        public Example() {
        }

        public Example(String name) {
        }

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
        assertEquals(arrayListInterfaces.length, 4);
        assertEquals(arrayListInterfaces[0], List.class);
        assertEquals(arrayListInterfaces[1], RandomAccess.class);

        Class<?>[] listInterfaces = List.class.getInterfaces();
        assertEquals(listInterfaces.length, 1);
        assertEquals(listInterfaces[0], Collection.class);

        Class<?>[] objectInterfaces = Object.class.getInterfaces();
        assertEquals(objectInterfaces.length, 0);
        Class<?>[] mapInterfaces = Map.class.getInterfaces();
        assertEquals(mapInterfaces.length, 0);

        Class<?>[] integerInterfaces = Integer.class.getInterfaces();
        assertEquals(integerInterfaces.length, 1);
        assertEquals(integerInterfaces[0], Comparable.class);

        Class<?>[] intInterfaces = int.class.getInterfaces();
        assertEquals(intInterfaces.length, 0);

        //或者void.class，似乎没区别
        Class<?>[] voidInterfacees = Void.class.getInterfaces();
        assertEquals(voidInterfacees.length, 0);

        Class<?>[] arrayIntInterfaces = int[].class.getInterfaces();
        assertEquals(arrayIntInterfaces.length, 2);
        assertEquals(arrayIntInterfaces[0], Cloneable.class);
        assertEquals(arrayIntInterfaces[1], Serializable.class);

        Class<?>[] objectIntInterfaces = Object[].class.getInterfaces();
        assertEquals(objectIntInterfaces.length, 2);
        assertEquals(objectIntInterfaces[0], Cloneable.class);
        assertEquals(objectIntInterfaces[1], Serializable.class);
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
        Class.forName("lang.ClassTest$Example");
    }

    /**
     * Method[] getDeclaredMethods()
     * <p>
     * 1.会返回类/接口的非构造函数的方法，包括private方法、静态方法
     * 2.如果此Class对象没有实现任何方法的类/接口，会返回一个空数组
     * 3.如果此Class对象是一个数组，基本类型或者void，会返回一个空数组
     * 4.注意：返回数组里的元素排序不固定，每次调用获得的排序结果可能都不一样
     */
    @Test
    public void testGetDeclaredMethods() {
        Method[] methods = Example.class.getDeclaredMethods();
        assertEquals(methods.length, 0);
    }
}
