package io.github.ransprd.autotester;

import java.util.function.Function;

public class AutoTesterContext<T> {

    private final Class<T> type;
    private final Function<Class<?>, ?> objectFactory;
    private final String testedFieldName;

    /**
     * PojoTesterContext
     *
     * @param classType     - (root) class to test
     * @param fieldToTest   - field  to test
     * @param objectFactory - object factory - knows how to create new objects for given type
     */
    public AutoTesterContext(Class<T> classType, String fieldToTest, Function<Class<?>, ?> objectFactory) {
        this.type = classType;
        this.objectFactory = objectFactory;
        this.testedFieldName = fieldToTest;
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

}
