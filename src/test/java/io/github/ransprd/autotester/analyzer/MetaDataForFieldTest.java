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
package io.github.ransprd.autotester.analyzer;

import java.lang.reflect.Field;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ranSprd
 */
public class MetaDataForFieldTest {
    
    @Test
    public void testDelegatedMethods() throws NoSuchFieldException {
        Field field = ClassUnderTest.class.getDeclaredField("field");
        MetaDataForField testInstance = new MetaDataForField();
        testInstance.setField( field);
        
        assertEquals( field.getName(), testInstance.getName());
        assertEquals( field.getModifiers(), testInstance.getModifiers());
        assertEquals( field.getType(), testInstance.getType());
    }
    
    
    @Test
    public void testAddMethod() throws NoSuchFieldException {
        Field field = ClassUnderTest.class.getDeclaredField("field");
        MetaDataForField testInstance = new MetaDataForField();
        testInstance.setField( field);
        assertFalse(testInstance.addMethod(null));
        
        MetaDataForMethod e = new MetaDataForMethod( null, null);
        assertFalse(testInstance.addMethod(e));
    }
    
    @Test
    public void testGetUsedMethods() {
        MetaDataForField testInstance = new MetaDataForField();
        assertNotNull(testInstance.getUsedByMethods());
    }
    
    
    private class ClassUnderTest {
        private String field;
    }
}