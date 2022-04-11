package io.github.ransprd.autotester.tester;

import io.github.ransprd.autotester.AutoTesterContext;
import io.github.ransprd.autotester.ObjectReflectionTools;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.function.Consumer;

public class PJSetterGetterTest implements Consumer<AutoTesterContext> {

    @Override
    public void accept(AutoTesterContext context) {
        String fieldName = context.getTestedFieldName();
        String sufix = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Field field = ObjectReflectionTools.findField(context.getTestedType(), fieldName);

        Method setter = ObjectReflectionTools.findMethod(context.getTestedType(), "set" + sufix, field.getType());
        if (setter == null || !ObjectReflectionTools.checkAccessors(setter, Modifier::isPublic, m -> !Modifier.isStatic(m))) {
            return;
        }

        Method getter = null;
        for (String prefix : Arrays.asList("get", "is", "has", "can", "should")) {
            getter = ObjectReflectionTools.findMethod(context.getTestedType(), prefix + sufix);

            if (getter != null) {
                break;
            }
        }

        if (getter == null || !ObjectReflectionTools.checkAccessors(getter, Modifier::isPublic, m -> !Modifier.isStatic(m))) {
            return;
        }

        Object testObject = context.createObject(context.getTestedType());
        Object fieldObject = context.createObject(field.getType());

        ObjectReflectionTools.invokeMethod(testObject, setter, fieldObject);

        Object gotObject = ObjectReflectionTools.invokeMethod(testObject, getter);
        if (gotObject == null) {
            throw new IllegalStateException("null received!");
        }

        if (!fieldObject.equals(gotObject)) {
            throw new IllegalStateException("objects not equal!");
        }
    }
}
