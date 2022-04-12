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
package io.github.ransprd.autotester.tester;

import io.github.ransprd.autotester.AutoTester;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ranSprd
 */
public class CombinedSetterGetterTestCaseTest {
    
    @Test
    public void testHappyCase() {
        AutoTester.forClass(WorkingClass.class).test();
    }
    
    @Test(expected = IllegalStateException.class)
    public void testSetterModifiesInput() {
        AutoTester.forClass(NotWorkingSetterClass.class).test();
    }
    
    
    
    public static class WorkingClass {
        private String strParam;

        public String getStrParam() {
            return strParam;
        }

        public void setStrParam(String strParam) {
            this.strParam = strParam;
        }
    }
    
    public static class NotWorkingSetterClass {
        private String strParam;

        public String getStrParam() {
            return strParam;
        }

        public void setStrParam(String strParam) {
            this.strParam = strParam +"....";
        }
    }
    
}
