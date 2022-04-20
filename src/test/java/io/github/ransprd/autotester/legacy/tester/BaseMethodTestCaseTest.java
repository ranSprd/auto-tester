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
package io.github.ransprd.autotester.legacy.tester;

import io.github.ransprd.autotester.legacy.tester.BaseMethodTestCase;
import java.lang.reflect.Method;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

/**
 *
 * @author ranSprd
 */
public class BaseMethodTestCaseTest {
    
    @Test
    public void testMethodIsPublicAndNotStatic() throws NoSuchMethodException {
        BaseMethodTestCase testInstance = new BaseMethodTestCase();
        
        Method staticMethod = ClassUnderTest.class.getDeclaredMethod("staticMethod");
        assertFalse(testInstance.methodIsPublicAndNotStatic(staticMethod));
        
        
        Method privateMethode = ClassUnderTest.class.getDeclaredMethod("privateMethod");
        assertFalse(testInstance.methodIsPublicAndNotStatic(privateMethode));
        
    }
    
    
    
    private static class ClassUnderTest {

        public static String staticMethod() {
            return "";
        }

        private String privateMethod() {
            return "";
        }
    }
    
}
