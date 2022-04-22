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

import java.util.Collection;
import java.util.List;
import java.util.Optional;
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
        
        Collection<MetaDataForField> fields = metaData.getAllFields();
        assertNotNull(fields);
        assertFalse(fields.isEmpty());
        assertEquals("expected number of fields failed", 2, fields.size());
        
        Optional<MetaDataForField> overwrittenFieldDef = metaData.findField("overwrittenfield");
        assertTrue(overwrittenFieldDef.isPresent());
        assertTrue(overwrittenFieldDef.get().hasParentFields());
        
        Optional<MetaDataForField> myFieldDef = metaData.findField("myField");
        assertTrue(myFieldDef.isPresent());
        assertFalse(myFieldDef.get().hasParentFields());
        
        List<MetaDataForMethod> methods = metaData.getAllMethods();
        assertNotNull(methods);
        assertFalse(methods.isEmpty());
        assertEquals(3, methods.size());
        methods.stream().forEach(m -> System.out.println(m) );
        
    }
    
    
    
    public static class ClassUnderTest extends ParentClassUnderTest {
        
        private boolean overwrittenField;
        private String myField;

        public boolean isOverwrittenField() {
            return overwrittenField;
        }

        public void setOverwrittenField(boolean overwrittenField) {
            this.overwrittenField = overwrittenField;
        }

        public String getMyField() {
            return myField;
        }

    }
    
    public static class ParentClassUnderTest {
        
        private String overwrittenField;

        public String getOverwrittenField() {
            return overwrittenField;
        }

        public void setOverwrittenField(String overwrittenField) {
            this.overwrittenField = overwrittenField;
        }
        
    }
    
    
    
}
