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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ranSprd
 */
public class CombinedGetterSetterTestCase extends TestCase {
    private static final Logger log = LoggerFactory.getLogger(CombinedGetterSetterTestCase.class);
    
    private static final String NAME = "Getter/Setter cycle check";
    
    @Override
    public boolean isTestable(MethodTestCaseContext testContext) {
        return false;
    }
    
    @Override
    public boolean isTestable(FieldTestCaseContext testContext) {
        return testContext.containsMethods(MethodType.Getter, MethodType.Setter);
    }
    
    @Override
    public void executeTestCase(MethodTestCaseContext testContext) {
    }
    
    @Override
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
        log.debug("start checking " +NAME + " for field [{}]", testContext.getFieldData().getName());
        
        
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
            return fail("Can not call setter/getter of class " +testContext.getClassData().getNameOfClazzUnderTest(), e);
        }

        if (!value.equals(received)) {
            log.debug("fields not equal");
            return fail("The getter returns a different result than was set with the setter" );
        } else {
            log.debug("fields are equal");
        }

        return null;
    }
    
}
