package io.github.ransprd.autotester.legacy;

import io.github.ransprd.autotester.ObjectReflectionTools;
import io.github.ransprd.autotester.legacy.ObjectUnderTestFactory;
import io.github.ransprd.autotester.config.TestCaseConfig;
import io.github.ransprd.autotester.config.TestCaseConfigBuilder;
import io.github.ransprd.autotester.legacy.tester.CombinedSetterGetterTestCase;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 
 * @author ranSprd
 * @param <T> 
 * @deprecated 
 */
public class OldAutoTester<T> {

    private final Map<Class<?>, Consumer<OldAutoTesterContext>> defaultTests = new LinkedHashMap<>();
    private final Map<String, List<Consumer<OldAutoTesterContext>>> userTests = new LinkedHashMap<>();
    private final ObjectUnderTestFactory objectFactory;

    private final Class<T> testType;
    private final TestCaseConfigBuilder configBuilder;

    private OldAutoTester(Class<T> clazz) {
        this.testType = clazz;
        defaultTests.put(CombinedSetterGetterTestCase.class, new CombinedSetterGetterTestCase());
        objectFactory = new ObjectUnderTestFactory();
        configBuilder = new TestCaseConfigBuilder();
    }

    public static <TYPE> OldAutoTester<TYPE> forClass(Class<TYPE> clz) {
        return new OldAutoTester<>(clz);
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
    public OldAutoTester registerObjectFactory(Class<?> clazz, Function<Class<?>, ?> factory) {
        objectFactory.addObjectFactory(clazz, factory);
        return this;
    }

    public OldAutoTester registerTestForField(String fieldName, Consumer<OldAutoTesterContext>... tests) {
        if (tests != null && tests.length > 0) {
            List<Consumer<OldAutoTesterContext>> testsPerField = userTests.computeIfAbsent(fieldName, e -> new LinkedList<>());
            testsPerField.addAll( Arrays.asList(tests));
        }
        return this;
    }

    public OldAutoTester removeDefaultTest(Class<?> testClass) {
        defaultTests.remove(testClass);
        return this;
    }

    /**
     * short cut method for TestCaseConfigBuilder.includeSuperclass()
     * @return 
     */
    public OldAutoTester includeSuperclass() {
        configBuilder.includeSuperclass();
        return this;
    }

    public void test() {
        TestCaseConfig testConfig = configBuilder.getConfig();
        
        List<Field> sortedFields = ObjectReflectionTools.getAllDeclaredFields(testType, testConfig.isTestSuperclassFields()).stream()
                                         .sorted(Comparator.comparing(Field::getName))
                                         .toList();
        
        for (Field field : sortedFields) {
            // add loggging and failure output here
            testField(field, testConfig);
        }
    }
    
    private void testField(Field field, TestCaseConfig config) {
        OldAutoTesterContext<T> ctx = new OldAutoTesterContext<>(testType, field.getName(), config, objectFactory::createObject);
        defaultTests.values().forEach(test -> test.accept(ctx));
        userTests.getOrDefault(field.getName(), Collections.emptyList()).forEach(test -> test.accept(ctx));
    }

}
