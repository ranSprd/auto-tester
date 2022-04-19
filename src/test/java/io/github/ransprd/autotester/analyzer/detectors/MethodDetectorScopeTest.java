/*
 * Copyright 2022 ranSprd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.ransprd.autotester.analyzer.detectors;

import java.lang.reflect.Method;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ranSprd
 */
public class MethodDetectorScopeTest {
    
    @Test
    public void testInvalidConstructorParameters() throws NoSuchMethodException {
        assertThrows(NullPointerException.class, () -> new MethodDetectorScope(ClassUnderTest.class, null));
        assertThrows(NullPointerException.class, () -> new MethodDetectorScope(null, null));
        
        Method method1 = ClassUnderTest.class.getDeclaredMethod("method1");
        assertNotNull("Test class should contain a method called 'method1' otherwise our test will fail", method1);
        assertThrows(NullPointerException.class, () -> new MethodDetectorScope(null, method1));
    }
    
    @Test
    public void testHelpersForMethod() throws NoSuchMethodException {
        Method method1 = ClassUnderTest.class.getDeclaredMethod("method1");
        assertNotNull("Test class should contain a method called 'method1' otherwise our test will fail", method1);
        
        MethodDetectorScope scope = new MethodDetectorScope(ClassUnderTest.class, method1);
        assertEquals(method1, scope.getMethod());
        assertEquals("method1", scope.getMethodName());
    }
    
    @Test 
    public void testFindField() throws NoSuchMethodException {
        Method method1 = ClassUnderTest.class.getDeclaredMethod("method1");
        assertNotNull("Test class should contain a method called 'method1' otherwise our test will fail", method1);
        
        MethodDetectorScope scope = new MethodDetectorScope(ClassUnderTest.class, method1);
        
        assertTrue(scope.findField(null).isEmpty());
        assertTrue(scope.findField("").isEmpty());
        
        assertFalse(scope.findField("intField").isEmpty());
        assertFalse(scope.findField("intfield").isEmpty());
        assertFalse(scope.findField("iNtfield").isEmpty());
    }
    
    @Test 
    public void testFindFieldOfParents() throws NoSuchMethodException {
        Method method1 = ParentClassUnderTest.class.getDeclaredMethod("getHiddenField");
        assertNotNull("Test class should contain a method called 'getHiddenField' otherwise our test will fail", method1);
        
        MethodDetectorScope scope = new MethodDetectorScope(ClassUnderTest.class, method1);
        
        assertTrue("intField is part of a child class and shouldn't be visible in parents scope",scope.findField("intField").isEmpty());
        assertFalse(scope.findField("hiddenField").isEmpty());
    }


    private class ClassUnderTest {
        
        private int intField;
    
        public void method1() {}
    }
    
    private class ParentClassUnderTest {
        private String hiddenField;

        public String getHiddenField() {
            return hiddenField;
        }
    }
}
