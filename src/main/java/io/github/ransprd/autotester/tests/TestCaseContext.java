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

import io.github.ransprd.autotester.ObjectReflectionTools;
import io.github.ransprd.autotester.analyzer.MetaDataForClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ranSprd
 */
public class TestCaseContext {
    private static final Logger log = LoggerFactory.getLogger(TestCaseContext.class);
    
    private final MetaDataForClass classData;

    public TestCaseContext(MetaDataForClass classData) {
        this.classData = classData;
    }

    public MetaDataForClass getClassData() {
        return classData;
    }

    public Object createTestableClassInstance() {
        try {
            return ObjectReflectionTools.newInstance(getClassData().getClazzUnderTest());
        } catch (Exception e) {
            log.error("Can not call the non-args constructor of class [{}]", getClassData().getNameOfClazzUnderTest());
        }
        return null;
    }
    
}
