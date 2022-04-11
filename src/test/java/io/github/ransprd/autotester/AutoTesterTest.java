package io.github.ransprd.autotester;

import io.github.ransprd.autotester.fixtures.ChildType;
import org.junit.Test;

public class AutoTesterTest {

    @Test
    public void test() {
        AutoTester.forClass(ChildType.class).test();
    }

    @Test
    public void testCreators() {
        AutoTester pojoTester = AutoTester.forClass(Object.class);

        ObjectUnderTestFactory objectFactory = new ObjectUnderTestFactory();
        objectFactory.createObject(String[].class);
    }

}
