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
import io.github.ransprd.autotester.tests.TestCaseContext;
import io.github.ransprd.autotester.tests.TestCaseFailureResult;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        
        Optional<Field> fieldOpt = methodeData.getUsedFields().stream().findAny();
        if (fieldOpt.isEmpty()) {
            return List.of(fail("Possible Getter method <" 
                    +methodeData.getMethod().getName() +"> found in Class <" 
                    +testContext.getClassData().getNameOfClazzUnderTest() +"> has no assigned field."));
        }
        
        Field field = fieldOpt.get();
        try {
            field.setAccessible(true);
        } catch (Exception e) {
            return List.of(fail("Can not modify accessible level of field <" 
                    +field.getName() +"> found in Class <" 
                    +testContext.getClassData().getNameOfClazzUnderTest() +">"));
        }
        
        Object instanceUnderTest = testContext.createTestableClassInstance();
        if (instanceUnderTest == null) {
            return List.of(fail("Can not instantiate class <" +testContext.getClassData().getNameOfClazzUnderTest() +"> for " +NAME));
        }
        
        final List<TestCaseFailureResult> result = new ArrayList<>();
        try {
            // 1. check read the current content
            check(testContext, field, methodeData.getMethod(), instanceUnderTest, field.get(instanceUnderTest))
                    .ifPresent(failure -> result.add(failure));
            
            // 2. check - play with null
            if (!field.getType().isPrimitive()) {
                field.set(instanceUnderTest, null);
                check(testContext, field, methodeData.getMethod(), instanceUnderTest, null)
                        .ifPresent(failure -> result.add(failure));
            }
            
            // 3. check - set a random value to the field and read afterwards
            Object value = testContext.createValueForField(field);
            field.set(instanceUnderTest, value);
            
            check(testContext, field, methodeData.getMethod(), instanceUnderTest, value)
                    .ifPresent(failure -> result.add(failure));
            
            
        } catch (Throwable e) {
            errorLog("execution error", e);
            return List.of(fail("Can not perform Getter checks for field "
                    + field.getName()
                    + " of class " +testContext.getClassData().getNameOfClazzUnderTest(), e));
        }
        
        return result;
    }
    
    private Optional<TestCaseFailureResult> check(TestCaseContext testContext, Field field, Method getter, Object testInstance, Object expectedValue) {
        try {
            Object result = getter.invoke(testInstance);
            if (expectedValue == null) {
                if (result != null) {
                    return Optional.of(fail("Getter of field <"
                            + field.getName()
                            + "> in class <" +testContext.getClassData().getNameOfClazzUnderTest()+"> returns a different value then field value. Expected value was NULL" ));
                }
            } else if (!expectedValue.equals(result)) {
                return Optional.of(fail("Getter of field <"
                        + field.getName()
                        + "> in class <" +testContext.getClassData().getNameOfClazzUnderTest()+"> returns a different value then field value." ));
            }
            
        } catch (Exception e) {
            errorLog("execution error", e);
            return Optional.of(fail("Can not read field value / call getter "
                    + field.getName()
                    + " of class " +testContext.getClassData().getNameOfClazzUnderTest(), e));
        }
        return Optional.empty();
    }

}
