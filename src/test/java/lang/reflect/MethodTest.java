package lang.reflect;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author tufei
 * @date 2018/1/20.
 */

public class MethodTest {
    class Example {
        public int generalMethod() {
            return 0;
        }

        public List<String> genericMethod(List<String> t) {
            return t;
        }

        public <T> T genericMethod(T t) {
            return t;
        }

        public void noReturnMethod() {
        }
    }

    /**
     * Class<?> getReturnType()与Type getGenericReturnType()
     * <p>
     * getGenericReturnType是基于getReturnType()方法的，
     * 它能获取到返回参数是泛型的方法的具体返回类型，平时用它就好
     */
    @Test
    public void testGetReturnTypeAndGenericReturnType() {
        Method[] methods = Example.class.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getReturnType());
        }
        /*Output(输出结果其实是不固定的  我自己手动排序了)
        int
        interface java.util.List
        class java.lang.Object
        void
         */
        Method[] declaredMethods = Example.class.getDeclaredMethods();
        for (Method method : declaredMethods) {
            System.out.println(method.getGenericReturnType());
        }
        /*Output
        int
        java.util.List<java.lang.String>
        T
        void
        */
    }
}
