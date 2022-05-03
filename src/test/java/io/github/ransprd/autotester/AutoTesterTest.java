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

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ranSprd
 */
public class AutoTesterTest {
    
    @Test
    public void testGivenClassIsNull() {
        NullPointerException assertThrows = assertThrows(NullPointerException.class, () -> AutoTester.forClass(null));
    }
    
    @Test 
    public void testHappyCase() {
        AutoTester.forClass( ClassUnderTest.class).test();
    }
    
    
    public class ClassUnderTest {
        private String field;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }
    }
    
}
