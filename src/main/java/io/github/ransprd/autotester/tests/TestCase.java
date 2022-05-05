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
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ranSprd
 */
public abstract class TestCase {
    
    private final String testCaseID = UUID.randomUUID().toString();
    private final String logPrefix;
    private final Logger logger;
    
    public abstract boolean isTestable(MethodTestCaseContext testContext);
    public abstract boolean isTestable(FieldTestCaseContext testContext);
    
    public abstract List<TestCaseFailureResult> executeTestCase(MethodTestCaseContext testContext);
    public abstract List<TestCaseFailureResult> executeTestCase(FieldTestCaseContext testContext);

    public TestCase() {
        logger = LoggerFactory.getLogger( getClass());
        logPrefix = "[" + testCaseID +"] - ";
    }
    
    
    
    public TestCaseFailureResult fail(String message, Throwable throwable) {
        return new TestCaseFailureResult(testCaseID, message, throwable);
    }

    public TestCaseFailureResult fail(String message) {
        return fail(message, null);
    }

    public String getTestCaseID() {
        return testCaseID;
    }
    
    
    
    public void debugLog(String string, Object... os) {
        logger.debug(logPrefix +string, os);
    }

    public void infoLog(String string, Object... os) {
        logger.info(logPrefix +string, os);
    }

    public void warnLog(String string, Object... os) {
        logger.warn(logPrefix +string, os);
    }

    public void errorLog(String string, Object... os) {
        logger.error(logPrefix +string, os);
    }
    
    
    
}
