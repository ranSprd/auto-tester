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
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ranSprd
 */
public class AutoTesterAssertionErrorTest {
    
    @Test
    public void testEmpty() {
        assertNotNull( AutoTesterAssertionError.build("clazz", null, null));
        assertNotNull( AutoTesterAssertionError.build("clazz", null, new StackTraceElement[0]));
        assertNotNull( AutoTesterAssertionError.build("clazz", List.of(), new StackTraceElement[0]));
        
        assertNotNull( AutoTesterAssertionError.build("clazz", List.of( new TestCaseFailureResult("error1")), new StackTraceElement[0]));
        assertNotNull( AutoTesterAssertionError.build("clazz", List.of( new TestCaseFailureResult("error1"), new TestCaseFailureResult("error2")), new StackTraceElement[0]));
    }
    
}
