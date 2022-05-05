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
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author ranSprd
 */
public class CombinedGetterSetterTestCase extends TestCase {
    
    private static final String NAME = "Getter/Setter cycle check";
    
    @Override
    public boolean isTestable(MethodTestCaseContext testContext) {
        return false;
    }
    
    @Override
    public boolean isTestable(FieldTestCaseContext testContext) {
        return testContext.containsMethods(MethodType.GETTER, MethodType.SETTER);
    }
    
    @Override
    public List<TestCaseFailureResult>  executeTestCase(MethodTestCaseContext testContext) {
        return List.of();
    }
    
    @Override
    public List<TestCaseFailureResult> executeTestCase(FieldTestCaseContext testContext) {
        List<MetaDataForMethod> getters = testContext.getMethodsClassifiedAs(MethodType.GETTER);
        List<MetaDataForMethod> setters = testContext.getMethodsClassifiedAs(MethodType.SETTER);
        if (setters.isEmpty() || getters.isEmpty()) {
            return List.of();
        }
        
        return getters.stream()
                    .map(getter -> testGetterWithSetters(testContext, getter.getMethod(), setters))
                    .flatMap(failures -> failures.stream())
                    .toList();
    }
    
    private List<TestCaseFailureResult> testGetterWithSetters(FieldTestCaseContext testContext, Method getter, List<MetaDataForMethod> setters) {
        return setters.stream()
                    .map(setter -> testGetterSetterCycle(testContext, getter, setter.getMethod()))
                    .filter(Objects::nonNull)
                    .toList();
    }
    
    private TestCaseFailureResult testGetterSetterCycle(FieldTestCaseContext testContext, Method getter, Method setter) {
        debugLog("start checking {} for field [{}]", NAME, testContext.getFieldName());
        
        Object value = null;
        Object received = null;
        Object instanceUnderTest = testContext.createTestableClassInstance();
        if (instanceUnderTest == null) {
            return fail("Can not instantiate class <" +testContext.getClassData().getNameOfClazzUnderTest() +"> for " +NAME);
        }
        
        try {
            value = testContext.createFieldValue();
            setter.setAccessible(true);
            getter.setAccessible(true);
            
            setter.invoke(instanceUnderTest, value);
            received = getter.invoke(instanceUnderTest);
        } catch (Throwable e) {
            return fail("Can not call setter/getter for field "
                    + testContext.getFieldName()
                    + " of class " +testContext.getClassData().getNameOfClazzUnderTest(), e);
        }

        if (!value.equals(received)) {
            warnLog("field [{}] values not equal", testContext.getFieldName());
            return fail("The getter of field [" 
                    +testContext.getFieldName()
                    + "] returns a different result than was set with the setter" );
        } else {
            debugLog("field [{}] values are equal", testContext.getFieldName());
        }

        return null;
    }
    
}
