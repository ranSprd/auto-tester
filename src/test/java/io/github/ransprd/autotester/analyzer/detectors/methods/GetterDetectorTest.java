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
package io.github.ransprd.autotester.analyzer.detectors.methods;

import io.github.ransprd.autotester.analyzer.detectors.MethodClassifications;
import io.github.ransprd.autotester.analyzer.detectors.MethodDetector;
import io.github.ransprd.autotester.analyzer.detectors.MethodDetectorScope;
import io.github.ransprd.autotester.analyzer.detectors.MethodType;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author ranSprd
 */
public class GetterDetectorTest {
    
    private final Map<String, Method> methods;

    public GetterDetectorTest() {
        // will not work for methods with equal name but different signature
        methods = Arrays.stream(ClassUnderTest.class.getDeclaredMethods())
                .collect(Collectors.toMap(Method::getName, Function.identity()));
    }
    
    @Test
    public void testGetterDetection() {
        
        shouldNotDetectAsSetter("anyMethod");
        shouldNotDetectAsSetter("anyOtherMethod");
        shouldNotDetectAsSetter("anotherMethod");
        shouldNotDetectAsSetter("getNotExistingField");
        shouldNotDetectAsSetter("getOtherType");
        shouldNotDetectAsSetter("should");
        
        expectGetter("getStr");
    }
    
    
    public void expectGetter(String methodName) {
        Optional<MethodClassifications> types = detect(methodName);
        assertTrue("There should be a classification", types.isPresent());
        assertEquals("Method '" +methodName +"' is not detected as GETTER", 1, types.get().size());
        assertTrue("Method '" +methodName +"' is not detected as GETTER", types.get().contains(MethodType.GETTER));
    }
    
    public void shouldNotDetectAsSetter(String methodName) {
        Optional<MethodClassifications> types = detect(methodName);
        assertTrue("Method '" +methodName +"' is detected as GETTER which is not expected", types.isEmpty());
    }
    
    
    private Optional<MethodClassifications> detect(String methodName) {
        Method method = methods.get(methodName);
        assertNotNull("Test clase (ClassUnderTest) should contain a method called '" +methodName +"' otherwise our test will fail", method);
        
        MethodDetectorScope scope = new MethodDetectorScope(ClassUnderTest.class, method);
        
        GetterDetector getterDetector = new GetterDetector();
        return getterDetector.check(scope);
    }
    
    
    private class ClassUnderTest extends ParentClassUnderTest {
        
        private String str;
        private String otherType;
        
        public static void anyMethod() {};
        
        public void anyOtherMethod() {};
        public void anotherMethod(String str) {};

        public String getStr() {
            return str;
        }
        public String getNotExistingField() {
            return str;
        }

        public boolean getOtherType() {
            return true;
        }
        
        public void should() {};
        
    }
    
    private class ParentClassUnderTest {
        
    }
    
    
    
}
