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
package io.github.ransprd.autotester.tests.cases;

import io.github.ransprd.autotester.analyzer.MetaDataForMethod;
import io.github.ransprd.autotester.analyzer.detectors.MethodType;
import io.github.ransprd.autotester.tests.FieldTestCaseContext;
import io.github.ransprd.autotester.tests.MethodTestCaseContext;
import io.github.ransprd.autotester.tests.TestCase;
import io.github.ransprd.autotester.tests.TestCaseFailureResult;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ranSprd
 */
public class GetterTestCase extends TestCase {
    private static final Logger log = LoggerFactory.getLogger(GetterTestCase.class);
    
    private static final String NAME = "Getter checks";
    

    @Override
    public boolean isTestable(MethodTestCaseContext testContext) {
        return testContext.contains(MethodType.GETTER);
    }

    @Override
    public boolean isTestable(FieldTestCaseContext testContext) {
        return false;
    }

    @Override
    public List<TestCaseFailureResult> executeTestCase(FieldTestCaseContext testContext) {
        return List.of();
    }
    
    @Override
    public List<TestCaseFailureResult> executeTestCase(MethodTestCaseContext testContext) {
        MetaDataForMethod methodeData = testContext.getMethodData();
        
        // extract the field
        if (methodeData.getUsedFields().size() > 1) {
            return List.of(fail("Possible Getter method <" 
                    +methodeData.getMethod().getName() +"> found in Class <" 
                    +testContext.getClassData().getNameOfClazzUnderTest() +"> is assigned to more than 1 field."));
        }
        
        Object value = null;
        Object received = null;
        Object instanceUnderTest = testContext.createTestableClassInstance();
        if (instanceUnderTest == null) {
            return List.of(fail("Can not instantiate class <" +testContext.getClassData().getNameOfClazzUnderTest() +"> for " +NAME));
        }
        
        try {
//            value = testContext.createFieldValue();
//            setter.setAccessible(true);
//            getter.setAccessible(true);
//            
//            setter.invoke(instanceUnderTest, value);
//            received = getter.invoke(instanceUnderTest);
        } catch (Throwable e) {
            return List.of(fail("Can not perform Getter checks for field "
                    + testContext.getMethodData().getUsedFields()
                    + " of class " +testContext.getClassData().getNameOfClazzUnderTest(), e));
        }
//        
        return List.of();
    }
    
//    private TestCaseFailureResult checkGetterWithNull() {
//        
//    }

}
