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

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ranSprd
 */
public class MethodTypeClassificatorTest {
    
    @Test
    public void testClassification() throws NoSuchMethodException {
        MethodClassifications anyMethodTypes = MethodTypeClassificator.INSTANCE.classify(ClassUnderTest.class, ClassUnderTest.class.getMethod("anyMethod"));
        assertTrue(anyMethodTypes.isEmpty());
        
        MethodClassifications getStrFieldTypes = MethodTypeClassificator.INSTANCE.classify(ClassUnderTest.class, ClassUnderTest.class.getMethod("getStrField"));
        assertFalse(getStrFieldTypes.isEmpty());
        assertEquals("only a single method type is expected but classification result contains more", 1, getStrFieldTypes.size());
        assertTrue("expectation was a classification as Getter", getStrFieldTypes.contains(MethodType.GETTER));
        
        MethodClassifications setStrFieldTypes = MethodTypeClassificator.INSTANCE.classify(ClassUnderTest.class, ClassUnderTest.class.getMethod("setStrField", String.class));
        assertFalse(setStrFieldTypes.isEmpty());
        assertEquals("only a single method type is expected but classification result contains more", 1, setStrFieldTypes.size());
        assertTrue("expectation was a classification as Setter", setStrFieldTypes.contains(MethodType.SETTER));
    }
    
    private class ClassUnderTest {
        
        private String strField;
        private int intField;
        
        public void anyMethod() {}

        public String getStrField() {
            return strField;
        }

        public void setStrField(String strField) {
            this.strField = strField;
        }

        public int getIntField() {
            return intField;
        }

        public void setIntField(int intField) {
            this.intField = intField;
        }
    }
    
}
