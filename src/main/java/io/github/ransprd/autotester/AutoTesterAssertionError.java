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

import io.github.ransprd.autotester.tests.TestCaseFailureResult;
import java.util.List;

/**
 *
 * @author ranSprd
 */
public class AutoTesterAssertionError extends AssertionError {
    
    public static AutoTesterAssertionError build(String clazzName, List<TestCaseFailureResult> failures, StackTraceElement[] stacktrace) {
        if (failures == null || failures.isEmpty()) {
            return new AutoTesterAssertionError("Some tests are broken for class <" +clazzName +">", List.of(), stacktrace);
        }
        
        StringBuilder str = new StringBuilder("There ");
        if (failures.size() > 1) {
            str.append("are ").append(failures.size()).append(" failed checks");
        } else {
            str.append("is 1 failed check");
        }
        str.append(" for class <").append(clazzName).append(">\n");
        
        for(TestCaseFailureResult failure : failures) {
            str.append("   [").append(failure.getTestCaseId()).append("] ").append(failure.getMessage()).append("\n");
        }
        
        return new AutoTesterAssertionError(str.toString(), failures, stacktrace);
    }
    
    private final List<TestCaseFailureResult> failures;
    
    public AutoTesterAssertionError(String message, List<TestCaseFailureResult> failures, StackTraceElement[] stacktrace) {
        super(message);
        this.failures = (failures == null) ? List.of() : failures;
        if (stacktrace != null) {
            setStackTrace(stacktrace);
        }
    }

    public List<TestCaseFailureResult> getFailures() {
        return failures;
    }
}
