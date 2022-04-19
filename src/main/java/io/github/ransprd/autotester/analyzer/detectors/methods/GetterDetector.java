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

import io.github.ransprd.autotester.ObjectReflectionTools;
import io.github.ransprd.autotester.analyzer.detectors.MethodDetector;
import io.github.ransprd.autotester.analyzer.detectors.MethodDetectorScope;
import io.github.ransprd.autotester.analyzer.detectors.MethodType;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 *
 * @author ranSprd
 */
public class GetterDetector implements MethodDetector {
    
    
    @Override
    public List<MethodType> check(MethodDetectorScope scope) {
        if (MethodDetector.methodIsPublicAndNotStatic(scope.getMethod())) {
            if (scope.getMethod().getParameterCount() == 0) {
                String lowerCaseMethodName = scope.getMethodName().toLowerCase();
                for (String prefix : ObjectReflectionTools.GETTER_PREFIXES) {
                    Optional<Field> field = findFieldForGetter(scope, lowerCaseMethodName, prefix);
                    if (field.isPresent()) {
                        Class<?> methodResultType = scope.getMethod().getReturnType();
                        Class<?> fieldType = field.get().getType();
                        if (Objects.equals(methodResultType, fieldType)) {
                            return List.of( MethodType.Getter);
                        }
                    }
                }
            }
        }
        return List.of();
    }
    
    private Optional<Field> findFieldForGetter(MethodDetectorScope scope, String lowerCaseMethodName, String getterPrefix) {
        final int methodNameSize = lowerCaseMethodName.length();
        // prefix of Getter method found and name is longer as that prefix
        if (lowerCaseMethodName.startsWith(getterPrefix) && methodNameSize > getterPrefix.length()) {
            String lowerCaseFieldName = lowerCaseMethodName.substring(getterPrefix.length());
            return scope.findField(lowerCaseFieldName);
        }
        return Optional.empty();

    }

}
