package io.github.ransprd.autotester;

import io.github.ransprd.autotester.fixtures.ChildType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.stream.Stream;
import org.junit.Assert;
import org.junit.Test;

public class ObjectReflectionToolsTest {

    @Test
    public void testGetAllDeclaredFields_withoutSuperclass() {
        // when
        Field[] fields = ObjectReflectionTools.getAllDeclaredFields(ChildType.class, false).toArray(Field[]::new);
        // then
        String[] fieldNames = Stream.of(fields).map(Field::getName).toArray(String[]::new);
        Assert.assertArrayEquals(fieldNames, toArray("name", "integerValue", "intValue"));
    }

    @Test
    public void testGetAllDeclaredFields_withSuperclass() {
        // when
        Field[] fields = ObjectReflectionTools.getAllDeclaredFields(ChildType.class, true).toArray(Field[]::new);
        // then
        String[] fieldNames = Stream.of(fields).map(Field::getName).toArray(String[]::new);
        Assert.assertArrayEquals(fieldNames, toArray("name", "integerValue", "intValue", "parentName"));
    }

    @Test
    public void testFindMethod() {
        // when
        Method getIntegerValueMethod = ObjectReflectionTools.findMethod(ChildType.class, "getIntegerValue");
        // then
        Assert.assertNotNull(getIntegerValueMethod);
    }

    @Test
    public void testCheckAccessors() {
        // given
        Method getIntegerValueMethod = ObjectReflectionTools.findMethod(ChildType.class, "getIntegerValue");

        // then
        Assert.assertTrue(ObjectReflectionTools.checkAccessors(getIntegerValueMethod, Modifier::isPublic, m -> !Modifier.isNative(m)));
        Assert.assertFalse(ObjectReflectionTools.checkAccessors(getIntegerValueMethod, Modifier::isPrivate, Modifier::isFinal));
    }

    @Test
    public void testGetAllDeclaredMethods() {
        // when
        List<Method> allDeclaredMethods = ObjectReflectionTools.getAllDeclaredMethods(ChildType.class);

        // then
        String[] methodNames = allDeclaredMethods.stream().map(Method::getName).sorted().toArray(String[]::new);
        Assert.assertArrayEquals(methodNames, toArray("getIntValue", "getIntegerValue", "getName", "setIntValue", "setIntegerValue", "setName"));
    }

    @Test
    public void testInvokeMethod() {
        // given
        ChildType child = new ChildType();
        Method setNameMethod = ObjectReflectionTools.findMethod(ChildType.class, "setName", String.class);
        Method getNameMethod = ObjectReflectionTools.findMethod(ChildType.class, "getName");

        // when
        ObjectReflectionTools.invokeMethod(child, setNameMethod, "name");
        String name = (String) ObjectReflectionTools.invokeMethod(child, getNameMethod);

        // then
        Assert.assertEquals("name", name);
    }

    @Test
    public void testGetFieldValue() {
        // given
        ChildType child = new ChildType();
        child.setName("john doe");

        // when
        String name = (String) ObjectReflectionTools.getFieldValue(child, "name");

        // then
        Assert.assertEquals(name, child.getName());
    }
    
    @Test 
    public void testFindField() {
        Assert.assertTrue(ObjectReflectionTools.findField(ClassUnderTest.class, "parentDoubleParam").isPresent());
        Assert.assertTrue(ObjectReflectionTools.findField(ClassUnderTest.class, "strParam1").isPresent());
        Assert.assertTrue(ObjectReflectionTools.findField(ClassUnderTest.class, "strparam1").isPresent());
        
        Assert.assertFalse(ObjectReflectionTools.findField(ClassUnderTest.class, "notExistingField").isPresent());
    }
    
    // helpers
    private String[] toArray(String... strings) {
        return strings;
    }

    
    public class ClassUnderTest extends ParentClassUnderTest {
        private String strParam1;
        private Boolean boolParam1;
    }
    
    private class ParentClassUnderTest {
        private double parentDoubleParam;
    }
    
}

