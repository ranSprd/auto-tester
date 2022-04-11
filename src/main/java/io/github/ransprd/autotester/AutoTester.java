package io.github.ransprd.autotester;

import io.github.ransprd.autotester.tester.PJSetterGetterTest;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class AutoTester<T> {

    private final Map<Class<?>, Consumer<AutoTesterContext>> defaultTests = new LinkedHashMap<>();
    private final Map<String, List<Consumer<AutoTesterContext>>> userTests = new LinkedHashMap<>();
    private final ObjectUnderTestFactory objectFactory;

    private final Class<T> testType;
    private boolean testSuperclassFields = false;

    private AutoTester(Class<T> clazz) {
        this.testType = clazz;
        defaultTests.put(PJSetterGetterTest.class, new PJSetterGetterTest());
        objectFactory = new ObjectUnderTestFactory();
    }

    public static <T> AutoTester forClass(Class<T> clz) {
        return new AutoTester<>(clz);
    }

    /**
     * Sometimes the library can't auto-detect constructors or can create 
     * instances of necessary parameters. For such cases you can register your own 'instance factory'.
     * 
     * .registerObjectFactory(DifficultType.class, type -> new DifficultType(new Object()))    
     * 
     * @param clazz
     * @param factory
     * @return 
     */
    public AutoTester registerObjectFactory(Class<?> clazz, Function<Class<?>, ?> factory) {
        objectFactory.addObjectFactory(clazz, factory);
        return this;
    }

    public AutoTester addFieldTest(String fieldName, Consumer<AutoTesterContext> test) {
        List<Consumer<AutoTesterContext>> testsPerField = userTests.computeIfAbsent(fieldName, e -> new LinkedList<>());
        testsPerField.add(test);
        return this;
    }

    public AutoTester removeDefaultTest(Class<?> testClass) {
        defaultTests.remove(testClass);
        return this;
    }

    public AutoTester includeSuperclass() {
        testSuperclassFields = true;
        return this;
    }

    public void test() {
        Field[] allDeclaredFields = ObjectReflectionTools.getAllDeclaredFields(testType, testSuperclassFields);
        String[] fieldNames = Stream.of(allDeclaredFields).map(Field::getName).sorted().toArray(String[]::new);
        for (String fieldName : fieldNames) {
            AutoTesterContext<T> ctx = new AutoTesterContext<>(testType, fieldName, objectFactory::createObject);
            defaultTests.values().forEach(test -> test.accept(ctx));
            userTests.getOrDefault(fieldName, Collections.emptyList()).forEach(test -> test.accept(ctx));
        }
    }

}
