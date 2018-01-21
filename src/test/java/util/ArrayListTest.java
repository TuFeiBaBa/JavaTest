package util;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * @author tufei
 * @date 2018/1/21.
 */

public class ArrayListTest {
    /**
     * int indexOf(Object o)
     * 1.如果参数为null，就返回集合中元素为null的最小索引值，如果不存在为null的元素，就返回-1
     * 2.如果不存在符合的元素，返回-1
     */
    @Test
    public void testIndexOf() {
        ArrayList<Integer> integers = new ArrayList<>();
        assertEquals(-1, integers.indexOf(null));
        integers.add(null);
        assertEquals(0, integers.indexOf(null));
    }
}
