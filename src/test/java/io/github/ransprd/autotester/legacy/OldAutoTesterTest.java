package io.github.ransprd.autotester.legacy;

import io.github.ransprd.autotester.fixtures.ChildType;
import org.junit.Test;

public class OldAutoTesterTest {

    @Test
    public void test() {
        OldAutoTester.forClass(ChildType.class).test();
    }

}
