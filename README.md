# auto-tester

This project provides a Java library for automatic tests for your POJOs/DTOs that you never tested - 
and which may (just like everything else) contain bugs.

Library uses pure java (ReflectionAPI) for its functionality - so no 3rd party dependencies.

**Note** This software is based on [pojo-tester-junit4](https://github.com/pojo-tester/pojo-tester-junit4). 
It is provided as is and is released under the [Apache 2.0 license](https://www.apache.org/licenses/LICENSE-2.0).

# Integration

## Maven
Add dependency to your ``pom.xml`` file

````xml
<dependency>
        <groupId>io.github.ranSprd</groupId>
        <artifactId>auto-tester</artifactId>
        <version>0.1.0-SNAPSHOT</version>
	<scope>test</scope>
</dependency>
````

# Usage

One line to test them all

````java
public class PojoExemplaryTest {
    // my never tested object...
    private static class MyUntestedObject {
        private DifficultType value;
        
        public void setValue(DifficultType value) {
            this.value = value;
        }
        public DifficultType getValue() {
            return value;
        }
    }
    
    @Test
    public void testMyPojos() {
        AutoTester.forClass(MyUntestedObject.class).test();
    }
}
````

Library supports "basic and typical" types that you use in your POJOs, which are:
- All primitive (and wrapper) types
- Array type
- Set / List / Queue / Map / Collection

Since library is written in pure java, and depends on Reflection API, some POJOs/DTOs may be hard to test...here are few scenarios:
- type without default constructor as a field
- abstract type as a field
- interface type as a field
- ...and many more which will cause problems during object instantiation.

Luckily, you may instruct library how to create them by providing simple helper:

````java
public class PojoExemplaryTest {
    // my never tested object...
    private static class MyUntestedObject {
        private DifficultType value;
        
        public void setValue(DifficultType value) {
            this.value = value;
        }
        public DifficultType getValue() {
            return value;
        }
    }
    // with this unlucky type...
    private static class DifficultType {
        // no default construct :(
        public DifficultType(Object... arguments) {
            // argh!!                         
        } 
    }
    
    @Test
    public void testMyPojos() {
        AutoTester.forClass(MyUntestedObject.class)
            .registerObjectFactory(DifficultType.class, type -> new DifficultType(new Object()))        
            .test();
    }
} 
````

or simply delegate all "unsupported" types to your favourite mocking framework!

````java
public class PojoExemplaryTest {
    // my never tested object...
    private static class MyUntestedObject {
        private DifficultType value;
        
        public void setValue(DifficultType value) {
            this.value = value;
        }
        public DifficultType getValue() {
            return value;
        }
    }
    // with this unlucky type...
    private static class DifficultType {
        // no default construct :(
        public DifficultType(Object... arguments) {
            // argh!!                         
        } 
    }
    
    @Test
    public void testMyPojos() {
        PojoTester.forClass(MyUntestedObject.class)
            .registerObjectFactory(Object.class, Mockito::mock) // Mockito to the rescue!        
            .test();
    }
} 
````  
