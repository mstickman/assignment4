package utils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionUtils {
    public static void inspectClass(Class<?> c) {

        System.out.println("Class name: " + c.getName());

        System.out.println("\nFields:");
        for (Field field : c.getDeclaredFields()) {
            System.out.println(field.getType().getSimpleName() + " " + field.getName());
        }

        System.out.println("\nMethods:");
        for (Method method : c.getDeclaredMethods()) {
            System.out.println(method.getReturnType().getSimpleName() + " " + method.getName() + "()");
        }
    }
}
