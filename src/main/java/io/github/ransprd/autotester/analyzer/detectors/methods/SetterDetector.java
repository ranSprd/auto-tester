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
package io.github.ransprd.autotester.analyzer.detectors.methods;

import io.github.ransprd.autotester.analyzer.detectors.MethodClassifications;
import io.github.ransprd.autotester.analyzer.detectors.MethodDetector;
import io.github.ransprd.autotester.analyzer.detectors.MethodDetectorScope;
import io.github.ransprd.autotester.analyzer.detectors.MethodType;
import java.lang.reflect.Field;
import java.util.Optional;

/**
 *
 * @author ranSprd
 */
public class SetterDetector implements MethodDetector {
    
    
    @Override
    public Optional<MethodClassifications> check(MethodDetectorScope scope) {
        if (MethodDetector.methodIsPublicAndNotStatic(scope.getMethod())) {
            final String methodName = scope.getMethodName();
            if (methodName.length() > 3 && scope.getMethod().getParameterCount() == 1 && methodName.startsWith("set")) {
                String setterFieldName = methodName.substring(3);
                Optional<Field> field = scope.findField(setterFieldName);
                if (field.isPresent()) {
                    return Optional.of(new MethodClassifications(MethodType.SETTER, field.get()));
                }
            }
        }
        return Optional.empty();
    }
    
}
