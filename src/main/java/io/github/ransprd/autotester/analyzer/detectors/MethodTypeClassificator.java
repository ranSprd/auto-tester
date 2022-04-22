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

import io.github.ransprd.autotester.analyzer.detectors.methods.GetterDetector;
import io.github.ransprd.autotester.analyzer.detectors.methods.SetterDetector;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author ranSprd
 */
public enum MethodTypeClassificator {
    
    INSTANCE;
    
    private final List<MethodDetector> detectors = Arrays.asList(
            new SetterDetector(), new GetterDetector()
    );
    
   
    /**
     * Classify means, that all registered method detectors (setter, getter...)
     * are excuted, the results are collected and returned.
     * @param clazz
     * @param method
     * @return 
     */
    public MethodClassifications classify(Class<?> clazz, Method method) {
        MethodDetectorScope scope = new MethodDetectorScope(clazz, method);
        return new MethodClassifications(detectors.stream()
                        .map(detector -> detector.check(scope))
                        .filter(classification -> classification.isPresent())
                        .flatMap(classification -> classification.get().getClassifications().stream())
                        .distinct()
                        .toList() );
    }
    
}
