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

import io.github.ransprd.autotester.analyzer.MetaDataForMethod;
import io.github.ransprd.autotester.analyzer.detectors.MethodType;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ranSprd
 */
public class CombinedGetterSetterTestCase {
    private static final Logger log = LoggerFactory.getLogger(CombinedGetterSetterTestCase.class);
    
    public boolean isTestable(MethodTestCaseContext testContext) {
        return false;
    }
    
    public boolean isTestable(FieldTestCaseContext testContext) {
        return testContext.containsMethods(MethodType.Getter, MethodType.Setter);
    }
    
    public void executeTestCase(MethodTestCaseContext testContext) {
    }
    
    public List<TestCaseFailureResult> executeTestCase(FieldTestCaseContext testContext) {
        List<MetaDataForMethod> getters = testContext.getMethodsClassifiedAs(MethodType.Getter);
        List<MetaDataForMethod> setters = testContext.getMethodsClassifiedAs(MethodType.Setter);
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
        log.debug("start checking Getter/Setter cycle for field [{}]", testContext.getFieldData().getName());
        
        
        Object fieldValue1 = null;
        Object received = null;
        try {
            
            Object instanceUnderTest = testContext.createTestableClassInstance();
            fieldValue1 = testContext.createFieldValue();
            setter.setAccessible(true);
            getter.setAccessible(true);
            
            setter.invoke(instanceUnderTest, fieldValue1);
            received = getter.invoke(instanceUnderTest);
        } catch (Throwable e) {
            e.printStackTrace();
            return fail("Calling setter/getter cycle failed", e);
        }
            

        if (!fieldValue1.equals(received)) {
            log.debug("fields not equal");
            return fail("The setter returns a different result than was set with the getter" );
        } else {
            log.debug("fields are equal");
        }

        return null;
    }
    
    public TestCaseFailureResult fail(String message, Throwable throwable) {
        return new TestCaseFailureResult(message, throwable);
    }
    public TestCaseFailureResult fail(String message) {
        return fail(message, null);
    }
    
    
}
