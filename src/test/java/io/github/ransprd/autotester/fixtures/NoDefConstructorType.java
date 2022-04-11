package io.github.ransprd.autotester.fixtures;

public class NoDefConstructorType {

    private String value;

    private NoDefConstructorType() {

    }

    public NoDefConstructorType(Object object) {

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
