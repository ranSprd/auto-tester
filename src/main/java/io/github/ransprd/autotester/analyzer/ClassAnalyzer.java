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

import io.github.ransprd.autotester.ObjectReflectionTools;

/**
 * This class is responsible for extraction of methods/fields and 
 * @author ranSprd
 */
public class ClassAnalyzer {
    
    
    public static MetaDataForClass analyze(Class clazz) {
        MetaDataForClass.InstanceBuilder builder = MetaDataForClass.get(clazz);
        
        ObjectReflectionTools.getAllDeclaredFields(clazz, true)
                .forEach(field -> builder.registerField(field));
        
        ObjectReflectionTools.getAllMethods(clazz)
                .forEach(method -> builder.registerMethod(method));
        
        return builder.getInstance();
    }
}