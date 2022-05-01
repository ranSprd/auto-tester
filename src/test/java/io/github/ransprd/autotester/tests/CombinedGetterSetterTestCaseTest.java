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
package io.github.ransprd.autotester.tests;

import io.github.ransprd.autotester.analyzer.ClassAnalyzer;
import io.github.ransprd.autotester.analyzer.MetaDataForClass;
import io.github.ransprd.autotester.analyzer.MetaDataForField;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ranSprd
 */
public class CombinedGetterSetterTestCaseTest {
    
//    @Test 
    public void testIsTestable() {
        CombinedGetterSetterTestCase testCase = new CombinedGetterSetterTestCase();
        
        MethodTestCaseContext methodContext = null;
        assertFalse(testCase.isTestable( methodContext));
        
        FieldTestCaseContext fieldContext = new FieldTestCaseContext(new MetaDataForClass(Object.class), new MetaDataForField());
        assertFalse(testCase.isTestable( fieldContext));
    }
    
    @Test
    public void testWorkingExample() {
        
        MetaDataForClass clazzData = ClassAnalyzer.analyze(ClassUnderTest.class);
        MetaDataForField fieldData = clazzData.findField("field").get();
        
        FieldTestCaseContext testContext = new FieldTestCaseContext(clazzData, fieldData);
        
        CombinedGetterSetterTestCase testCase = new CombinedGetterSetterTestCase();
        List<TestCaseFailureResult> result = testCase.executeTestCase(testContext);
    }
    
    
    public static class ClassUnderTest {
        private String field;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field +"A";
        }
    }
    
}
