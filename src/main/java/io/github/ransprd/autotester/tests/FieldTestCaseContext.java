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
import io.github.ransprd.autotester.analyzer.detectors.MethodType;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author ranSprd
 */
public class FieldTestCaseContext {
    
    private final MetaDataForClass classData;
    private final MetaDataForField fieldData;

    public FieldTestCaseContext(MetaDataForClass classData, MetaDataForField fieldData) {
        this.classData = classData;
        this.fieldData = fieldData;
    }

    public MetaDataForClass getClassData() {
        return classData;
    }

    public MetaDataForField getFieldData() {
        return fieldData;
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
    
}
