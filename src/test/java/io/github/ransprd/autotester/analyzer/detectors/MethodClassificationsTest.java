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

import io.github.ransprd.autotester.analyzer.detectors.MethodClassifications.MethodTypeData;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ranSprd
 */
public class MethodClassificationsTest {
    
    @Test
    public void testConstructors() {
        MethodClassifications c1 = new MethodClassifications();
        assertNotNull(c1);
        assertNotNull(c1.getClassifications());
        assertTrue(c1.getClassifications().isEmpty());
        
        MethodClassifications c2 = new MethodClassifications(null);
        assertNotNull(c2);
        assertNotNull(c2.getClassifications());
        assertTrue(c2.getClassifications().isEmpty());
        
        MethodClassifications c3 = new MethodClassifications( MethodType.GETTER, null);
        assertNotNull(c3);
        assertNotNull(c3.getClassifications());
        assertFalse(c3.getClassifications().isEmpty());
    }
    
    @Test
    public void testContainsSingle() {
        MethodClassifications c1 = new MethodClassifications( MethodType.GETTER, null);
        
        MethodType methodTypeNull = null;
        assertFalse(c1.contains(methodTypeNull));
        assertFalse(c1.contains(MethodType.SETTER));
        assertTrue(c1.contains(MethodType.GETTER));
    }
    
    @Test
    public void testContainsMultiple() {
        MethodClassifications c1 = new MethodClassifications( MethodType.GETTER, null);
        MethodType methodTypeNull = null;
        assertFalse(c1.contains(methodTypeNull, methodTypeNull));
        assertFalse(c1.contains(MethodType.SETTER, MethodType.GETTER));
        
        MethodClassifications c2 = new MethodClassifications( List.of(new MethodTypeData(MethodType.GETTER, null), new MethodTypeData(MethodType.SETTER, null) ));
        assertTrue(c2.contains(MethodType.GETTER, MethodType.SETTER));
        assertTrue(c2.contains(MethodType.SETTER));
        
        MethodClassifications c3 = new MethodClassifications();
        assertFalse(c3.contains(methodTypeNull));
        
        MethodClassifications c4 = new MethodClassifications( MethodType.GETTER, null);
        assertFalse(c4.contains(MethodType.GETTER, MethodType.SETTER));
    }
    
    @Test
    public void testUsedFields() {
        MethodClassifications c1 = new MethodClassifications( MethodType.GETTER, null);
        Collection<Field> fields = c1.getUsedFields();
        assertNotNull(fields);
        assertTrue(fields.isEmpty());
        
    }
    
}
