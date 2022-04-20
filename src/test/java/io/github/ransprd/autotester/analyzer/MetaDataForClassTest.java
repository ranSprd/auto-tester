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

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ranSprd
 */
public class MetaDataForClassTest {
    
    @Test
    public void testFindFieldWithInvalidParameters() {
        MetaDataForClass testInstance = new MetaDataForClass(Object.class);
        assertTrue( testInstance.findField(null).isEmpty());
        assertTrue( testInstance.findField("").isEmpty());
        assertTrue( testInstance.findField("clazz").isEmpty());
    }
    
}
