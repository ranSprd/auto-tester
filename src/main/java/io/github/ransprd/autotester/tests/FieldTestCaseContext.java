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

import io.github.ransprd.autotester.analyzer.MetaDataForClass;
import io.github.ransprd.autotester.analyzer.MetaDataForField;
import io.github.ransprd.autotester.analyzer.MetaDataForMethod;
import io.github.ransprd.autotester.analyzer.detectors.MethodType;
import io.github.ransprd.autotester.legacy.ObjectUnderTestFactory;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ranSprd
 */
public class FieldTestCaseContext {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(FieldTestCaseContext.class);
    
    private final MetaDataForClass classData;
    private final MetaDataForField fieldData;

    public FieldTestCaseContext(MetaDataForClass classData, MetaDataForField fieldData) {
        this.classData = classData;
        if (fieldData == null) {
            throw new NullPointerException("can not test an empty field");
        }
        this.fieldData = fieldData;
    }

    public MetaDataForClass getClassData() {
        return classData;
    }

    public MetaDataForField getFieldData() {
        return fieldData;
    }

    public Field getField() {
        return fieldData.getField();
    }
    
    public boolean containsMethods(MethodType... methodTypes) {
        // no methodTypes/classifications given
        if (methodTypes == null || methodTypes.length < 1) {
            return false;
        }
        Collection<MethodType> allMethodClassifications = fieldData.getAllMethodTypes();
        
        // number of found classifications for the methods of field are lower then the
        // required number - it is not possible that all types are available
        if (allMethodClassifications.size() < methodTypes.length) {
            return false;
        }
        
        for(MethodType type : methodTypes) {
            if (!allMethodClassifications.contains(type)) {
                return false;
            }
        }
        return true;
    }
    
    public List<MetaDataForMethod> getMethodsClassifiedAs(MethodType methodType) {
        if (methodType == null) {
            return List.of();
        }
        return fieldData.getUsedByMethods().stream()
                            .filter(m -> m.contains(methodType))
                            .toList();
    }
    
    
    public Object createTestableClassInstance() {
        Constructor[] ctors = classData.getClazzUnderTest().getDeclaredConstructors();
        for (int i = 0; i < ctors.length; i++) {
            Constructor ctor = ctors[i];
            if (ctor.getParameterCount() == 0) {
                ctor.setAccessible(true);
                try {
                    return ctor.newInstance();
                } catch (Exception ex) {
                    log.error("Can not find a non-args constructor for class [{}]", classData.getClazzUnderTest().getName());
                }
            }
        }
        return null;
    }
    
    public Object createFieldValue() {
        return new ObjectUnderTestFactory().createObject( fieldData.getType());
    }
    
}
