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

import io.github.ransprd.autotester.analyzer.detectors.MethodClassifications;
import io.github.ransprd.autotester.analyzer.detectors.MethodType;
import java.lang.reflect.Method;

/**
 *
 * @author ranSprd
 */
public class MetaDataForMethod {
    
    private final Method method;
    private final MethodClassifications methodClassifications;

    public MetaDataForMethod(Method method, MethodClassifications methodClassifications) {
        this.method = method;
        this.methodClassifications = methodClassifications;
    }

    public Method getMethod() {
        return method;
    }

    public boolean contains(MethodType... types) {
        return methodClassifications.contains(types);
    }
    
    @Override
    public String toString() {
        return "{" + "methodName=" + method.getName() + ", types=" + methodClassifications.getClassifications() + '}';
    }

    
    
    
    
}
