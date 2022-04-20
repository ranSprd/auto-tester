package io.github.ransprd.autotester.legacy;

import io.github.ransprd.autotester.config.TestCaseConfig;
import java.util.function.Function;

/**
 * 
 * @author ranSprd
 * @param <T> 
 * @deprecated 
 */
public class OldAutoTesterContext<T> {

    private final Class<T> type;
    private final Function<Class<?>, ?> objectFactory;
    private final String testedFieldName;
    private final TestCaseConfig config;

    /**
     * PojoTesterContext
     *
     * @param classType     - (root) class to test
     * @param fieldToTest   - field  to test
     * @param config
     * @param objectFactory - object factory - knows how to create new objects for given type
     */
    public OldAutoTesterContext(Class<T> classType, String fieldToTest, TestCaseConfig config, Function<Class<?>, ?> objectFactory) {
        this.type = classType;
        this.testedFieldName = fieldToTest;
        this.config = config;
        this.objectFactory = objectFactory;
    }

    public Class<?> getTestedType() {
        return type;
    }

    public String getTestedFieldName() {
        return testedFieldName;
    }

    public <S> S createObject(Class<S> cls) {
        return (S) objectFactory.apply(cls);
    }

    public TestCaseConfig getConfig() {
        return config;
    }

}
