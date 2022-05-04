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
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ranSprd
 */
public class TestCaseTest {
    
    @Test
    public void testGenerateTestCaseId() {
        TestCase testCase = new TestCaseImpl();
        String id = testCase.generateTestCaseId();
        assertNotNull(id);
        assertFalse(id.isBlank());
    }

    public class TestCaseImpl extends TestCase {

        @Override
        public boolean isTestable(MethodTestCaseContext testContext) {
            return false;
        }

        @Override
        public boolean isTestable(FieldTestCaseContext testContext) {
            return false;
        }

        @Override
        public List<TestCaseFailureResult> executeTestCase(MethodTestCaseContext testContext) {
            return null;
        }

        @Override
        public List<TestCaseFailureResult> executeTestCase(FieldTestCaseContext testContext) {
            return null;
        }
    }
    
}
