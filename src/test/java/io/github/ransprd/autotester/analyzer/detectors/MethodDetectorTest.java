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
package io.github.ransprd.autotester.analyzer.detectors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author ranSprd
 */
public class MethodDetectorTest {
    
    @Test
    public void testSomeMethod() throws NoSuchMethodException {
        assertFalse(MethodDetector.methodIsPublicAndNotStatic(ClassUnderTest.class.getDeclaredMethod("method1")));
        assertFalse(MethodDetector.methodIsPublicAndNotStatic(ClassUnderTest.class.getDeclaredMethod("method2")));
        assertFalse(MethodDetector.methodIsPublicAndNotStatic(ClassUnderTest.class.getDeclaredMethod("method3")));
        assertFalse(MethodDetector.methodIsPublicAndNotStatic(ClassUnderTest.class.getDeclaredMethod("method5")));
        
        assertTrue(MethodDetector.methodIsPublicAndNotStatic(ClassUnderTest.class.getDeclaredMethod("method4")));
    }
    
    
    private class ClassUnderTest {
        
        private static void method1() {}
        
        private void method2() {}
        
        public static void method3() {}
        
        public void method4() {}
        
        void method5() {}
    }
    
}
