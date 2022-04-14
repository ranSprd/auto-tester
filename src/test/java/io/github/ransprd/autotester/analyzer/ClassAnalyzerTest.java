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
public class ClassAnalyzerTest {
    
    @Test
    public void testSeveralFieldsWithSameNameExist() {
        ClassAnalyzer analyzer = new ClassAnalyzer();
        MetaDataForClass metaData = analyzer.analyze(ClassUnderTest.class);
        
    }
    
    
    
    public static class ClassUnderTest extends ParentClassUnderTest {
        
        private boolean overwrittenParameter;
    }
    
    public static class ParentClassUnderTest {
        
        private String overwrittenParameter;
        
        
        
    }
    
    
    
}
