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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ranSprd
 */
public class MethodClassifications {
    
    public record MethodTypeData(MethodType methodType, List<Field> fields) {

        public MethodTypeData(MethodType methodType, List<Field> fields) {
            this.methodType = methodType;
            this.fields = (fields == null) ? List.of() : fields;
        }
    }
    
    
    private final List<MethodTypeData> classifications;

    /**
     * construct an instance with an single classification
     * @param methodType
     * @param singleField 
     */
    public MethodClassifications(MethodType methodType, Field singleField) {
        this( new ArrayList());
        classifications.add( new MethodTypeData(methodType, List.of(singleField)));
    }

    /**
     * instance with given classifications
     * @param types 
     */
    public MethodClassifications(List<MethodTypeData> types) {
        this.classifications = (types == null) ? new ArrayList() : types;
    }
    
    
    /**
     * check if there is a classification of given type
     * @param methodType
     * @return 
     */
    public boolean contains(MethodType methodType) {
        return classifications.stream().anyMatch(item -> item.methodType() == methodType);
    }
    
    /**
     * number of classifications
     * @return 
     */
    public int size() {
        return classifications.size();
    }

    public boolean isEmpty() {
        return classifications.isEmpty();
    }
    
    public List<MethodTypeData> getClassifications() {
        return classifications;
    }
    
}
