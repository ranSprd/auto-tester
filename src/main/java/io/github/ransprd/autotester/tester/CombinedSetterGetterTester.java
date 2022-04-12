package io.github.ransprd.autotester.tester;

import io.github.ransprd.autotester.AutoTesterContext;
import io.github.ransprd.autotester.ObjectReflectionTools;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Consumer;

/**
 * Test if the getter returns the same value which was set by the setter.
 * 
 */
public class CombinedSetterGetterTester extends BaseMethodTester implements Consumer<AutoTesterContext> {

    @Override
    public void accept(AutoTesterContext context) {
        String fieldName = context.getTestedFieldName();
        String suffix = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Field field = ObjectReflectionTools.findField(context.getTestedType(), fieldName);

        Method setter = ObjectReflectionTools.findMethod(context.getTestedType(), "set" + suffix, field.getType());
        if (setter == null || !methodIsPublicAndNotStatic(setter)) {
            return;
        }

        Method getter = ObjectReflectionTools.findGetter(context.getTestedType(), field, true);

        if (getter == null || !methodIsPublicAndNotStatic(getter)) {
            return;
        }

        Object testInstance = context.createObject(context.getTestedType());
        Object fieldValue = context.createObject(field.getType());

        ObjectReflectionTools.invokeMethod(testInstance, setter, fieldValue);

        Object gotValue = ObjectReflectionTools.invokeMethod(testInstance, getter);
        if (gotValue == null) {
            throw new IllegalStateException("null received!");
        }

        if (!fieldValue.equals(gotValue)) {
            throw new IllegalStateException("objects not equal!");
        }
    }
    
}
