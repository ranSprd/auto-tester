package io.github.ransprd.autotester;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;

public class ObjectReflectionToolsFindGetterTest {

    @Test
    public void testFindGetterHappyCases() throws NoSuchFieldException {
        expectMethodExist(ClassUnderTest1.class, "strParam1");
        expectMethodExist(ClassUnderTest1.class, "boolParam1");
        expectMethodExist(ClassUnderTest1.class, "boolParam2");
        expectMethodExist(ClassUnderTest1.class, "boolParam3");
        
        expectMethodExist(ClassUnderTest1.class, "paramWithNonCompliantGetterNaming");
        expectMethodExist(ClassUnderTest1.class, "paramWithPrivateGetter");
    }
    
    @Test
    public void testGetterMustReturnSameType() throws NoSuchFieldException {
        expectMethodNotExist(ClassUnderTest1.class, "paramWithoutGetter");
        expectMethodNotExist(ClassUnderTest1.class, "paramWithoutGetterButSameNameMethodExists");
    }
    
    @Test
    public void testGetterOfParentClass() throws NoSuchFieldException {
        Field strParam1field = ParentClassUnderTest.class.getDeclaredField("parentDoubleParam");

        Method method1 = ObjectReflectionTools.findGetter(ClassUnderTest1.class, strParam1field, true);
        assertNotNull(method1);
        
        Method method2 = ObjectReflectionTools.findGetter(ClassUnderTest1.class, strParam1field, false);
        assertNull(method2);
    
    }
    

    
    
    
    private void expectMethodExist(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        expectMethodExist(clazz, fieldName, true);
    }
    
    private void expectMethodExist(Class<?> clazz, String fieldName, boolean includeParentClasses) throws NoSuchFieldException {
        Field strParam1field = clazz.getDeclaredField(fieldName);
        Method method = ObjectReflectionTools.findGetter(clazz, strParam1field, includeParentClasses);
        
        assertNotNull(method);
    }
    
    private void expectMethodNotExist(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        Field strParam1field = clazz.getDeclaredField(fieldName);
        Method method = ObjectReflectionTools.findGetter(clazz, strParam1field, true);
        
        assertNull(method);
    }
    

    
    
    
    public static class ClassUnderTest1 extends ParentClassUnderTest {
        private String strParam1;
        private Boolean boolParam1;
        private Boolean boolParam2;
        private Boolean boolParam3;
        
        private byte paramWithNonCompliantGetterNaming;
        
        private String paramWithoutGetter;
        private String paramWithPrivateGetter;
        private String paramWithoutGetterButSameNameMethodExists;

        public String getStrParam1() {
            return strParam1;
        }

        public Boolean getBoolParam1() {
            return boolParam1;
        }

        public Boolean isBoolParam2() {
            return boolParam2;
        }

        public Boolean shouldBoolParam3() {
            return boolParam3;
        }

        public byte getparamwithnoncompliantgetternaming() {
            return paramWithNonCompliantGetterNaming;
        }

        private String getParamWithPrivateGetter() {
            return paramWithPrivateGetter;
        }
        
        public int getParamWithoutGetterButSameNameMethodExists() {
            return 0;
        }
    }
    
    private static class ParentClassUnderTest {
        private double parentDoubleParam;

        public double getParentDoubleParam() {
            return parentDoubleParam;
        }
    }
}

