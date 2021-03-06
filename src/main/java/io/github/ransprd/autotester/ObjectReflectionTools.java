package io.github.ransprd.autotester;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Code partially copied from:
 * org.springframework.util.ReflectionUtils
 */
public final class ObjectReflectionTools {

    private ObjectReflectionTools() {
    }

    @SafeVarargs
    public static boolean checkAccessors(Member member, Function<Integer, Boolean>... matchers) {
        Objects.requireNonNull(member, "Member must not be null");
        boolean retVal = true;
        for (Function<Integer, Boolean> matcher : matchers) {
            retVal &= matcher.apply(member.getModifiers());
        }
        return retVal;
    }

    public static Field findField(Class<?> clazz, String fieldName) {
        Objects.requireNonNull(clazz, "Class must not be null");
        Objects.requireNonNull(fieldName, "fieldName must not be null");
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static Field[] getAllDeclaredFields(Class<?> clazz, boolean useSuperclass) {
        Objects.requireNonNull(clazz, "Class must not be null");
        List<Field> fields = new LinkedList<>();
        Class<?> current = clazz;
        do {
            Collections.addAll(fields, current.getDeclaredFields());
            current = current.getSuperclass();
        } while (current != Object.class && useSuperclass);
        fields.removeIf(Field::isSynthetic);
        return fields.toArray(new Field[0]);
    }

    public static Object getFieldValue(Object target, String fieldName) {
        Objects.requireNonNull(target, "Target object must not be null");
        Objects.requireNonNull(fieldName, "fieldName must not be null");
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            if (!checkAccessors(field, Modifier::isPublic)) {
                field.setAccessible(true);
            }
            return field.get(target);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Find a method with name (exact match) and given parameters. 
     * @param clazz
     * @param name
     * @param paramTypes
     * @return 
     */
    public static Method findMethod(Class<?> clazz, String name, Class<?>... paramTypes) {
        Objects.requireNonNull(clazz, "Class must not be null");
        Objects.requireNonNull(name, "Method name must not be null");
        Class<?> searchType = clazz;
        while (searchType != null) {
            Method[] methods = (searchType.isInterface() ? searchType.getMethods() : getAllDeclaredMethods(searchType));
            for (Method method : methods) {
                if (name.equals(method.getName()) && (paramTypes == null || Arrays.equals(paramTypes, method.getParameterTypes()))) {
                    return method;
                }
            }
            searchType = searchType.getSuperclass();
        }
        return null;
    }
    
    /**
     * 
     * @param clazz the class of interest
     * @param field the field as target for the getter method
     * @param includeParentClasses if true, super/parent classes will be used also to find a getter method 
     * @return 
     */
    public static Method findGetter(Class<?> clazz,  Field field, boolean includeParentClasses) {
        Objects.requireNonNull(clazz, "Class must not be null");
        Objects.requireNonNull(field, "Field must not be null");

        String lowerCaseFieldName = field.getName().toLowerCase();
        
        Class<?> searchType = clazz;
        while (searchType != null) {
            Method[] methods = (searchType.isInterface() ? searchType.getMethods() : getAllDeclaredMethods(searchType));
            for (Method method : methods) {
                String lowerCaseMethodName = method.getName().toLowerCase();
                for (String prefix : Arrays.asList("get", "is", "has", "can", "should")) {
                    if (lowerCaseMethodName.equals(prefix + lowerCaseFieldName)) {
                        Class<?> resultType = method.getReturnType();
                        if (resultType != null && resultType == field.getType()) {
                            return method;
                        }
                    }
                }
            }
            if (includeParentClasses) {
                searchType = searchType.getSuperclass();
            } else {
                // break the loop, searching over parents is not allowed
                searchType = null;
            }
        }
        return null;
    }
    
    public static Method findSetter(Class<?> clazz,  Field field, boolean includeParentClasses) {
        Objects.requireNonNull(clazz, "Class must not be null");
        Objects.requireNonNull(field, "Field must not be null");

        String lowerCaseSetterName = "set" +field.getName().toLowerCase();
        
        Class<?> searchType = clazz;
        while (searchType != null) {
            Method[] methods = (searchType.isInterface() ? searchType.getMethods() : getAllDeclaredMethods(searchType));
            for (Method method : methods) {
                String lowerCaseMethodName = method.getName().toLowerCase();
                if (lowerCaseMethodName.equals(lowerCaseSetterName)) {
                    Class<?>[] params = method.getParameterTypes();
                    if (params != null && params.length == 1 && params[0].equals(field.getType())) {
                        return method;
                    }
                }
            }
            if (includeParentClasses) {
                searchType = searchType.getSuperclass();
            } else {
                // break the loop, searching over parents is not allowed
                searchType = null;
            }
        }
        return null;
    }

    public static Method[] getAllDeclaredMethods(Class<?> clazz) {
        Objects.requireNonNull(clazz, "Class must not be null");
        List<Method> methods = new LinkedList<>();
        Collections.addAll(methods, clazz.getDeclaredMethods());
        List<Method> defaultMethods = findConcreteMethodsOnInterfaces(clazz);
        if (defaultMethods != null) {
            methods.addAll(defaultMethods);
        }
        methods.removeIf(Method::isSynthetic);
        return methods.toArray(Method[]::new);
    }

    public static Object invokeMethod(Object target, Method method, Object... args) {
        Objects.requireNonNull(target, "Target object must not be null");
        Objects.requireNonNull(method, "Method must not be null");
        try {
            return method.invoke(target, args);
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    public static <T> T newInstance(Class<T> clazz) {
        Objects.requireNonNull(clazz, "Clazz must not be null");
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static List<Method> findConcreteMethodsOnInterfaces(Class<?> clazz) {
        List<Method> result = null;
        for (Class<?> ifc : clazz.getInterfaces()) {
            for (Method ifcMethod : ifc.getMethods()) {
                if (!Modifier.isAbstract(ifcMethod.getModifiers())) {
                    if (result == null) {
                        result = new LinkedList<>();
                    }
                    result.add(ifcMethod);
                }
            }
        }
        return result;
    }
}
