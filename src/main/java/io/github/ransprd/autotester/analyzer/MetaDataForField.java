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
package io.github.ransprd.autotester.analyzer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ranSprd
 */
public class MetaDataForField {
    
    private Field field;
    private List<Field> parentFields;

    public MetaDataForField() {
    }

    public void setField(Field field) {
        this.field = field;
    }
    
    public String getName() {
        return field.getName();
    }

    public int getModifiers() {
        return field.getModifiers();
    }

    public Class<?> getType() {
        return field.getType();
    }


    public void addParentField(Field fieldOfParent) {
        if (parentFields == null) {
            parentFields = new ArrayList<>();
        }
        parentFields.add(fieldOfParent);
    }
    
    public boolean hasParentFields() {
        return (parentFields != null && !parentFields.isEmpty());
    }
   

}
