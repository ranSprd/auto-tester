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

import java.util.List;

/**
 *
 * @author ranSprd
 */
public abstract class TestCase {
    
    
    public abstract boolean isTestable(MethodTestCaseContext testContext);
    public abstract boolean isTestable(FieldTestCaseContext testContext);
    
    public abstract void executeTestCase(MethodTestCaseContext testContext);
    public abstract List<TestCaseFailureResult> executeTestCase(FieldTestCaseContext testContext);
    
    public TestCaseFailureResult fail(String message, Throwable throwable) {
        return new TestCaseFailureResult(message, throwable);
    }

    public TestCaseFailureResult fail(String message) {
        return fail(message, null);
    }
    
}
