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
package io.github.ransprd.autotester.config;

import io.github.ransprd.autotester.tests.TestCase;
import io.github.ransprd.autotester.tests.cases.CombinedGetterSetterTestCase;
import io.github.ransprd.autotester.tests.cases.GetterTestCase;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author ranSprd
 */
public class TestCaseConfig {
    
    private boolean testSuperclassFields = false;

    public boolean isTestSuperclassFields() {
        return testSuperclassFields;
    }

    void setTestSuperclassFields(boolean testSuperclassFields) {
        this.testSuperclassFields = testSuperclassFields;
    }
    
    public List<TestCase> getTestCases() {
        return Arrays.asList(new CombinedGetterSetterTestCase(), new GetterTestCase());
    }
    
}
