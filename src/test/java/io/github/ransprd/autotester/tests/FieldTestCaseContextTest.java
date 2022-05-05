/*
 * Copyright 2022 ranSprd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain fieldA copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.ransprd.autotester.tests;

import io.github.ransprd.autotester.analyzer.MetaDataForClass;
import io.github.ransprd.autotester.analyzer.MetaDataForField;
import io.github.ransprd.autotester.analyzer.MetaDataForMethod;
import io.github.ransprd.autotester.analyzer.detectors.MethodClassifications;
import io.github.ransprd.autotester.analyzer.detectors.MethodType;
import java.lang.reflect.Field;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ranSprd
 */
public class FieldTestCaseContextTest {
    
    @Test
    public void testConstructors() {
        MetaDataForClass mdfc = new MetaDataForClass(String.class);
        assertThrows(NullPointerException.class, () -> new FieldTestCaseContext(mdfc, null));
    }
    
    @Test 
    public void testContainsMethods() throws NoSuchFieldException, NoSuchMethodException {
        
        Field field = ClassUnderTest.class.getDeclaredField("fieldA");
        
        
        FieldTestCaseContext testInstance = new FieldTestCaseContext(
                new MetaDataForClass(String.class), 
                new MetaDataForField(field));
        assertFalse(testInstance.containsMethods());
        MethodType methodType = null;
        assertFalse(testInstance.containsMethods(methodType));
        assertFalse(testInstance.containsMethods(MethodType.GETTER));
        
        MetaDataForMethod methodMetaData = new MetaDataForMethod(
                ClassUnderTest.class.getDeclaredMethod("getFieldA"), 
                new MethodClassifications(MethodType.GETTER, field));
        testInstance.getFieldData().addMethod(methodMetaData);
        
    }
    
    @Test 
    public void testGetMethodsClassifiedAs() throws NoSuchFieldException, NoSuchMethodException {
        
        Field field = ClassUnderTest.class.getDeclaredField("fieldA");
        
        
        FieldTestCaseContext testInstance = new FieldTestCaseContext(
                new MetaDataForClass(ClassUnderTest.class), 
                new MetaDataForField(field));

        MetaDataForMethod methodMetaData = new MetaDataForMethod(
                ClassUnderTest.class.getDeclaredMethod("getFieldA"), 
                new MethodClassifications(MethodType.GETTER, field));
        testInstance.getFieldData().addMethod(methodMetaData);

        
        List<MetaDataForMethod> emptyList = testInstance.getMethodsClassifiedAs(null);
        assertNotNull(emptyList);
        assertTrue(emptyList.isEmpty());
        
        assertFalse(testInstance.containsMethods(MethodType.SETTER));
        List<MetaDataForMethod> setterList = testInstance.getMethodsClassifiedAs(MethodType.SETTER);
        assertNotNull(setterList);
        assertTrue(setterList.isEmpty());
        
        assertTrue(testInstance.containsMethods(MethodType.GETTER));
        List<MetaDataForMethod> getterList = testInstance.getMethodsClassifiedAs(MethodType.GETTER);
        assertNotNull(getterList);
        assertEquals(1, getterList.size());
    }
    
    @Test
    public void testCreateTestableInstance() {
        FieldTestCaseContext testInstance = new FieldTestCaseContext(
                new MetaDataForClass(ClassUnderTest.class), 
                new MetaDataForField());
        
        assertNull( testInstance.createTestableClassInstance());
    }
    
    
    private class ClassUnderTest {
        private String fieldA;

        public ClassUnderTest(String fieldA) {
            this.fieldA = fieldA;
        }
        
        public String getFieldA() {
            return fieldA;
        }
        
    }
    
    
}
