package io.github.ransprd.autotester.legacy;

import io.github.ransprd.autotester.fixtures.ChildType;
import org.junit.Test;

public class OldAutoTesterTest {

    @Test
    public void test() {
        OldAutoTester.forClass(ChildType.class).test();
    }

    @Test
    public void testCreators() {
        OldAutoTester pojoTester = OldAutoTester.forClass(Object.class);

        ObjectUnderTestFactory objectFactory = new ObjectUnderTestFactory();
        objectFactory.createObject(String[].class);
    }

}
