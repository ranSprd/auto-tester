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

import io.github.ransprd.autotester.analyzer.detectors.MethodTypeClassificator;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author ranSprd
 */
public class MetaDataForClass {
    
    private final Class<?> clazz;
    
    private final List<MetaDataForMethod> allMethods = new ArrayList<>();
    private final Map<String, MetaDataForField> allFields = new HashMap<>();
    
    public MetaDataForClass(Class<?> clazz) {
        this.clazz = clazz;
    }
    
    public Optional<MetaDataForField> findField(String fieldName) {
        if (fieldName == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(allFields.get( fieldName.toLowerCase()));
    }

    public List<MetaDataForMethod> getAllMethods() {
        return allMethods;
    }
    
    
    
    /**
     * 
     * @param clazz
     * @return 
     */
    static InstanceBuilder get(Class<?> clazz) {
        return new InstanceBuilder( new MetaDataForClass(clazz));
    }
    
    /** Helper class for creating class, field and method meta data */
    static class InstanceBuilder {
        private final MetaDataForClass instance;


        private InstanceBuilder(MetaDataForClass instance) {
            this.instance = instance;
        }

        public MetaDataForClass getInstance() {
            return instance;
        }
        
        void registerField(Field field) {
            
            String normalizedFieldName = field.getName().toLowerCase();
            MetaDataForField fieldEntry = instance.allFields.computeIfAbsent(normalizedFieldName, x -> new MetaDataForField());
            
            if (field.getDeclaringClass().equals( instance.clazz)) {
                // field of the target class, we expect that a field is not already set (no check)
                fieldEntry.setField(field);
            } else {
                // seems a field of a parent
                fieldEntry.addParentField(field);
            }
        }

        void registerMethod(Method method) {
            instance.allMethods.add(
                    new MetaDataForMethod(method, 
                         MethodTypeClassificator.INSTANCE.computeMethodTypes(instance.clazz, method)));
        }
    }
    

}
