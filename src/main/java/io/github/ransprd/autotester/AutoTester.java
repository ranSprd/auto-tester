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

import io.github.ransprd.autotester.analyzer.ClassAnalyzer;
import io.github.ransprd.autotester.analyzer.MetaDataForClass;
import io.github.ransprd.autotester.analyzer.MetaDataForField;
import io.github.ransprd.autotester.analyzer.MetaDataForMethod;
import java.util.List;

/**
 *
 * @author ranSprd
 */
public class AutoTester {

    public static AutoTester forClass(Class classUnderTest) {
        if (classUnderTest == null) {
            throw new NullPointerException("Given 'class under test' cannot be null.");
        }
        return new AutoTester(classUnderTest);
    }
    
    private final Class targetTestClass;

    private AutoTester(Class targetTestClass) {
        this.targetTestClass = targetTestClass;
    }
    
    public boolean test() {
        MetaDataForClass classData = ClassAnalyzer.analyze(targetTestClass);
        classData.getAllFields().stream().forEach(field -> testField(classData, field));
        classData.getAllMethods().stream().forEach(method -> testMethod(classData, method));
        return true;
    }
    
    private boolean testMethod(MetaDataForClass classData, MetaDataForMethod methodData) {
        return true;
    }
    
    private boolean testField(MetaDataForClass classData, MetaDataForField fieldData) {
        return true;
    }
    
}
