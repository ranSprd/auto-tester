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
package io.github.ransprd.autotester;

import io.github.ransprd.autotester.analyzer.ClassAnalyzer;
import io.github.ransprd.autotester.analyzer.MetaDataForClass;
import io.github.ransprd.autotester.analyzer.MetaDataForField;
import io.github.ransprd.autotester.analyzer.MetaDataForMethod;
import io.github.ransprd.autotester.tests.cases.CombinedGetterSetterTestCase;
import io.github.ransprd.autotester.tests.FieldTestCaseContext;
import io.github.ransprd.autotester.tests.TestCase;
import io.github.ransprd.autotester.tests.TestCaseFailureResult;
import io.github.ransprd.autotester.tests.cases.GetterTestCase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author ranSprd
 */
public class AutoTester {

    public static AutoTester forClass(Class<?> classUnderTest) {
        if (classUnderTest == null) {
            throw new NullPointerException("Given 'class under test' cannot be null.");
        }
        return new AutoTester(classUnderTest);
    }
    
    private final Class<?> targetTestClass;
    
    private final List<TestCase> testCases = 
            Arrays.asList(new CombinedGetterSetterTestCase(), new GetterTestCase());

    private AutoTester(Class<?> targetTestClass) {
        this.targetTestClass = targetTestClass;
    }
    
    public boolean test() {
        MetaDataForClass classData = ClassAnalyzer.analyze(targetTestClass);
        List<TestCaseFailureResult> failures = classData.getAllFields().stream()
                        .map(field -> testField(classData, field))
                        .flatMap(Collection::stream)
                        .toList();
        if (!failures.isEmpty()) {
            throw AutoTesterAssertionError.build(targetTestClass.getCanonicalName(), failures, getStackTrace("test"));
        }
        classData.getAllMethods().stream().forEach(method -> testMethod(classData, method));
        return true;
    }
    
    private boolean testMethod(MetaDataForClass classData, MetaDataForMethod methodData) {
        assert classData != null;
        assert methodData != null;
        return true;
    }
    
    private List<TestCaseFailureResult> testField(MetaDataForClass classData, MetaDataForField fieldData) {
        FieldTestCaseContext context = new FieldTestCaseContext(classData, fieldData);
        return testCases.stream()
                    .filter(testCase -> testCase.isTestable(context))
                    .map( testCase -> testCase.executeTestCase(context))
                    .flatMap(Collection::stream)
                    .toList();
    }
    
    
    /**
     * Create a stacktrace of the current call and remove some trace elements.
     * 
     * @param methodName everything before AutoTester.$methodName will be removed
     * @return 
     */
    private StackTraceElement[] getStackTrace(String methodName) {
        String clazzName = AutoTester.class.getCanonicalName();
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        boolean use = false;
        List<StackTraceElement> list = new ArrayList<>();
        
        for(StackTraceElement element : stacktrace) {
            if (use) {
                list.add(element);
            } else if (element.getClassName().equals( clazzName)) {
                if (element.getMethodName().equals(methodName)) {
                    use = true;
                }
            }
        }
        
        return list.toArray(StackTraceElement[]::new);
    }
    
}
