package lang;

import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;

import static org.junit.Assert.assertEquals;

/**
 * @author tufei
 * @date 2018/1/20.
 */

public final class ClassTest {

    /**
     * Class<?>[] getInterfaces()
     * <p>
     * 如果此对象表示一个类/接口，则该数组包含对象代表类/接口（实现/扩展）的所有接口。
     * 数组中的接口对象的顺序对应于接口声明的顺序。
     * <p>
     * 如果此对象是类/接口，没有实现/扩展接口，如果此对象是基本类型或Void，就返回一个空数组（不是null，注意）。
     * <p>
     * 如果此对象代表一个数组类型，Cloneable和java.io.Serializable接口将会被返回。
     */
    @Test
    public void getInterfaces() {
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
}
