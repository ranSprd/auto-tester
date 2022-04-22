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

import io.github.ransprd.autotester.analyzer.detectors.MethodType;

/**
 *
 * @author ranSprd
 */
public class CombinedGetterSetterTestCase {
    
    public boolean isTestable(MethodTestCaseContext testContext) {
        return false;
    }
    
    public boolean isTestable(FieldTestCaseContext testContext) {
        return testContext.containsMethods(MethodType.Getter, MethodType.Setter);
    }
    
    public void executeTestCase(MethodTestCaseContext testContext) {
    }
    
    public void executeTestCase(FieldTestCaseContext testContext) {
//        testContext.getFieldData().
    }
    
}
