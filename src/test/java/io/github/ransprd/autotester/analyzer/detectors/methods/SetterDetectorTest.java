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

import io.github.ransprd.autotester.analyzer.detectors.MethodDetectorScope;
import io.github.ransprd.autotester.analyzer.detectors.MethodType;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
public class SetterDetectorTest {
    
    private Map<String, Method> methods;

    public SetterDetectorTest() {
        // will not work for methods with equal name but different signature
        methods = Arrays.stream(ClassUnderTest.class.getDeclaredMethods())
                .collect(Collectors.toMap(Method::getName, Function.identity()));
    }
    
    
    @Test
    public void testSetterOfClass() {
        expectSetter("setStrParam1");
        expectSetter("setBoolParam1");
        expectSetter("setparamwithnoncompliantsetternaming");
        expectSetter("setParamWithFluentSetter");
        expectSetter("setParentDoubleParam");
        
        shouldNotDetectAsSetter("setParamWithPrivateSetter");
        shouldNotDetectAsSetter("setParamWithoutSetterButSameNameMethodExists");
        shouldNotDetectAsSetter("setSomething");
        shouldNotDetectAsSetter("setFoo");
        shouldNotDetectAsSetter("setNotExistingField");
        shouldNotDetectAsSetter("someOtherMethod");
        shouldNotDetectAsSetter("set");
    }
    
    @Test
    public void testSetterOfParents() {
        expectSetter("setParentDoubleParam");
    }
    
    
    public void expectSetter(String methodName) {
        List<MethodType> types = detect(methodName);
        assertEquals("Method '" +methodName +"' is not detected as SETTER", 1, types.size());
        assertTrue("Method '" +methodName +"' is not detected as SETTER", types.contains(MethodType.Setter));
    }
    
    public void shouldNotDetectAsSetter(String methodName) {
        List<MethodType> types = detect(methodName);
        assertTrue("Method '" +methodName +"' is detected as SETTER which is not expected", types.isEmpty());
    }
    
    private List<MethodType> detect(String methodName) {
        Method method = methods.get(methodName);
        assertNotNull("Test clase (ClassUnderTest) should contain a method called '" +methodName +"' otherwise our test will fail", method);
        
        MethodDetectorScope scope = new MethodDetectorScope(ClassUnderTest.class, method);
        
        SetterDetector setterDetector = new SetterDetector();
        return setterDetector.check(scope);
    }
    
    
    
    public class ClassUnderTest extends ParentClassUnderTest {
        private String strParam1;
        private Boolean boolParam1;
        
        private byte paramWithNonCompliantSetterNaming;
        
        private String paramWithoutSetter;
        private String paramWithPrivateSetter;
        private String paramWithFluentSetter;
        private String paramWithoutSetterButSameNameMethodExists;
        
        private String something;
        private String foo;

        public void setStrParam1(String strParam1) {
            this.strParam1 = strParam1;
        }

        public void setBoolParam1(Boolean boolParam1) {
            this.boolParam1 = boolParam1;
        }

        public void setparamwithnoncompliantsetternaming(byte paramWithNonCompliantSetterNaming) {
            this.paramWithNonCompliantSetterNaming = paramWithNonCompliantSetterNaming;
        }

        private void setParamWithPrivateSetter(String paramWithPrivateSetter) {
            this.paramWithPrivateSetter = paramWithPrivateSetter;
        }

        public ClassUnderTest setParamWithFluentSetter(String paramWithFluentSetter) {
            this.paramWithFluentSetter = paramWithFluentSetter;
            return this;
        }
        
        public void setParamWithoutSetterButSameNameMethodExists() {
        }
        
        public void setSomething(String strParam1, String strParam2) {}
        
        public static void setFoo(String str1) {};
        
        public void setNotExistingField(String str) {}
        
        public void someOtherMethod(String str) {}
        
        public void set(String str) {}
    }
    
    private class ParentClassUnderTest {
        private double parentDoubleParam;

        public void setParentDoubleParam(double parentDoubleParam) {
            this.parentDoubleParam = parentDoubleParam;
        }
    }
    
}
