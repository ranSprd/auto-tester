package io.github.ransprd.autotester.fixtures;

public class StaticType {

    private static String value;

    public static void setValue(String value) {
        StaticType.value = value;
    }

    public static String getValue() {
        return value;
    }
}
