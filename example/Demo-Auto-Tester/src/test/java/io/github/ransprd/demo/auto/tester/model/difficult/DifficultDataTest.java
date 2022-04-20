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
package io.github.ransprd.demo.auto.tester.model.difficult;

import io.github.ransprd.autotester.legacy.OldAutoTester;
import org.junit.jupiter.api.Test;

/**
 *
 * @author ranSprd
 */
public class DifficultDataTest {
    
    @Test
    public void testGetterAndSetter() {
        OldAutoTester.forClass(DifficultData.class)
                  .registerObjectFactory(DifficultType.class, type -> new DifficultType(new Object()))    
                  .test();
        
    }
    
}
