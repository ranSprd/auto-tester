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
import io.github.ransprd.autotester.analyzer.MetaDataForMethod;
import io.github.ransprd.autotester.analyzer.detectors.MethodType;

/**
 *
 * @author ranSprd
 */
public class MethodTestCaseContext extends TestCaseContext {

    private final MetaDataForMethod methodData;

    public MethodTestCaseContext(MetaDataForClass classData, MetaDataForMethod methodData) {
        super(classData);
        this.methodData = methodData;
    }

    public MetaDataForMethod getMethodData() {
        return methodData;
    }

    public boolean contains(MethodType... types) {
        return methodData.contains(types);
    }
    
    
}
