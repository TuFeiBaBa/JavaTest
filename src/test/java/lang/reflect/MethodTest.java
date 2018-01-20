package lang.reflect;

import org.junit.Test;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static util.Print.print;

/**
 * @author tufei
 * @date 2018/1/20.
 */

public class MethodTest {

    /**
     * Class<?> getReturnType()与Type getGenericReturnType()
     * <p>
     * getGenericReturnType是基于getReturnType()方法的，
     * 它能获取到返回参数是泛型的方法的具体返回类型，平时用它就好
     */
    @Test
    public void testGetReturnTypeAndGenericReturnType() {
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

        Method[] methods = Example.class.getDeclaredMethods();
        for (Method method : methods) {
            print(method.getReturnType());
        }
        /*Output(输出结果其实是不固定的  我自己手动排序了)
        int
        interface java.util.List
        class java.lang.Object
        void
         */
        Method[] declaredMethods = Example.class.getDeclaredMethods();
        for (Method method : declaredMethods) {
            print(method.getGenericReturnType());
        }
        /*Output
        int
        java.util.List<java.lang.String>
        T
        void
        */
    }

    /**
     * Type的直接子接口
     * 1.ParameterizedType： 表示一种参数化的类型，比如Collection，即普通的泛型。
     * 2.TypeVariable：是各种类型变量的公共父接口，就是泛型里面的类似T、E。
     * 3.GenericArrayType：表示一种元素类型是参数化类型或者类型变量的数组类型，比如List<>[]，T[]这种。
     * 4.WildcardType：代表一种通配符类型表达式，类似? super T这样的通配符表达式。
     */
    @Test
    public void testTypeStyle() {
        class Example<T> {
            void test(int i, String s, T t, int[] ints, String[] strings, T[] vTypeArray,
                      List<String> list, List<String>[] pTypeArray, List<T> tList,
                      List<? extends Number> wList, List<? super String> sList, Map<List<String>, ? extends String> map) {
            }
        }

        //getDeclaredMethods获得的方法数组，元素顺序不固定
        Method method = Example.class.getDeclaredMethods()[0];
        //getGenericParameterTypes获得的方法参数数组，元素顺序是固定的，按原来的顺序排列
        Type[] types = method.getGenericParameterTypes();
        for (Type type : types) {
            //用于存放参数的所有type组成,集合的第一个元素，指的就是该参数的type
            List<String> actualTypes = new ArrayList<>();
            findTypes(type, actualTypes);
            print(actualTypes);
        }
         /*Output
         [Class<?>]
         [Class<?>]
         [TypeVariable]
         [Class<?>]
         [Class<?>]
         [GenericArrayType, TypeVariable]
         [ParameterizedType, Class<?>]
         [GenericArrayType, ParameterizedType, Class<?>]
         [ParameterizedType, TypeVariable]
         [ParameterizedType, WildcardType]
         [ParameterizedType, WildcardType]
         [ParameterizedType, ParameterizedType, Class<?>, WildcardType]
         */
    }


    private List<String> findTypes(Type type, List<String> actualTypes) {
        if (type instanceof Class<?>) {
            actualTypes.add("Class<?>");
            return actualTypes;
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            actualTypes.add("ParameterizedType");
            for (Type typeArgument : parameterizedType.getActualTypeArguments()) {
                findTypes(typeArgument, actualTypes);
            }
            return actualTypes;
        }
        if (type instanceof GenericArrayType) {
            actualTypes.add("GenericArrayType");
            findTypes(((GenericArrayType) type).getGenericComponentType(), actualTypes);
            return actualTypes;
        }
        if (type instanceof TypeVariable) {
            actualTypes.add("TypeVariable");
            return actualTypes;
        }
        if (type instanceof WildcardType) {
            actualTypes.add("WildcardType");
            return actualTypes;
        }
        return actualTypes;
    }
}
