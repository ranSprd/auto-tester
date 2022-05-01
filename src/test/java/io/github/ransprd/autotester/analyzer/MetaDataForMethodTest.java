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

import io.github.ransprd.autotester.analyzer.detectors.MethodClassifications;
import io.github.ransprd.autotester.analyzer.detectors.MethodType;
import java.util.List;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author ranSprd
 */
public class MetaDataForMethodTest {
    
    @Test
    public void testGetMethod() throws NoSuchMethodException {
        MetaDataForMethod testInstance = new MetaDataForMethod( Object.class.getMethod("toString"), new MethodClassifications(List.of()));
        assertNotNull(testInstance.getMethod());
    }
    
    @Test
    public void testContainsAll() throws NoSuchMethodException {
        MetaDataForMethod testInstance = new MetaDataForMethod( Object.class.getMethod("toString"), 
                                         new MethodClassifications( MethodType.Getter, null));
        
        assertFalse( testInstance.contains(MethodType.Setter));
        assertFalse( testInstance.contains(MethodType.Setter, MethodType.Getter));
        assertTrue( testInstance.contains(MethodType.Getter));
    }
    
    @Test
    public void testGetMethodClassifications() throws NoSuchMethodException {
        MetaDataForMethod testInstance1 = new MetaDataForMethod( Object.class.getMethod("toString"), 
                                         new MethodClassifications( MethodType.Getter, null));
        assertNotNull(testInstance1.getMethodClassifications());
        
        MetaDataForMethod testInstance2 = new MetaDataForMethod( Object.class.getMethod("toString"), null);
        assertNotNull(testInstance2.getMethodClassifications());
    }
    
    
}
