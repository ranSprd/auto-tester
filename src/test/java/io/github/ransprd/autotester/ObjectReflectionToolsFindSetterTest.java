package io.github.ransprd.autotester;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;

public class ObjectReflectionToolsFindSetterTest {

    @Test
    public void testFindSetterHappyCases() throws NoSuchFieldException {
        expectMethodExist(ClassUnderTest.class, "strParam1");
        expectMethodExist(ClassUnderTest.class, "boolParam1");
        
        expectMethodExist(ClassUnderTest.class, "paramWithNonCompliantSetterNaming");
        expectMethodExist(ClassUnderTest.class, "paramWithPrivateSetter");
        
        expectMethodExist(ClassUnderTest.class, "paramWithFluentSetter");
    }
    
    @Test
    public void testSetterMustReturnSameType() throws NoSuchFieldException {
        expectMethodNotExist(ClassUnderTest.class, "paramWithoutSetter");
        expectMethodNotExist(ClassUnderTest.class, "paramWithoutSetterButSameNameMethodExists");
    }
    
    @Test
    public void testSetterOfParentClass() throws NoSuchFieldException {
        Field parentField = ParentClassUnderTest.class.getDeclaredField("parentDoubleParam");

        Method expectMethodExists = ObjectReflectionTools.findSetter(ClassUnderTest.class, parentField, true);
        assertNotNull(expectMethodExists);
        
        Method nonExistingExpected = ObjectReflectionTools.findSetter(ClassUnderTest.class, parentField, false);
        assertNull(nonExistingExpected);
    }
    

    
    
    
    private void expectMethodExist(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        expectMethodExist(clazz, fieldName, true);
    }
    
    private void expectMethodExist(Class<?> clazz, String fieldName, boolean includeParentClasses) throws NoSuchFieldException {
        Field strParam1field = clazz.getDeclaredField(fieldName);
        Method method = ObjectReflectionTools.findSetter(clazz, strParam1field, includeParentClasses);
        
        assertNotNull(method);
    }
    
    private void expectMethodNotExist(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        Field strParam1field = clazz.getDeclaredField(fieldName);
        Method method = ObjectReflectionTools.findSetter(clazz, strParam1field, true);
        
        assertNull(method);
    }
    

    
    
    
    public static class ClassUnderTest extends ParentClassUnderTest {
        private String strParam1;
        private Boolean boolParam1;
        
        private byte paramWithNonCompliantSetterNaming;
        
        private String paramWithoutSetter;
        private String paramWithPrivateSetter;
        private String paramWithFluentSetter;
        private String paramWithoutSetterButSameNameMethodExists;

        public void setStrParam1(String strParam1) {
            this.strParam1 = strParam1;
        }

        public void setBoolParam1(Boolean boolParam1) {
            this.boolParam1 = boolParam1;
        }

        public void setparamwithnoncompliantsetternaming(byte paramWithNonCompliantSetterNaming) {
            this.paramWithNonCompliantSetterNaming = paramWithNonCompliantSetterNaming;
        }

        private void setParamWithPrivateSetter(String paramWithPrivateSetter) {
            this.paramWithPrivateSetter = paramWithPrivateSetter;
        }

        public ClassUnderTest setParamWithFluentSetter(String paramWithFluentSetter) {
            this.paramWithFluentSetter = paramWithFluentSetter;
            return this;
        }
        
        public void setParamWithoutSetterButSameNameMethodExists() {
        }
    }
    
    private static class ParentClassUnderTest {
        private double parentDoubleParam;

        public void setParentDoubleParam(double parentDoubleParam) {
            this.parentDoubleParam = parentDoubleParam;
        }
    }
}

