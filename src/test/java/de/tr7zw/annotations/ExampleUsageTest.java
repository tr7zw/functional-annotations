package de.tr7zw.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

import org.junit.Test;

public class ExampleUsageTest {

	@Test
	@RequiredClass(className = "de.tr7zw.annotations.FAUtil")
	public void noException() {
		FAUtil.check(this::noException, this::checkRequiredClass);
	}
	
	@Test(expected = Error.class)
	@RequiredClass(className = "com.google.gson.Gson")
	public void unavaliableClass() {
		FAUtil.check(this::unavaliableClass, this::checkRequiredClass);
	}
	
	private boolean checkRequiredClass(Method method) {
		try {
			Class.forName(method.getAnnotation(RequiredClass.class).className());
		}catch(ClassNotFoundException e) {
			throw new Error("The required class was not found!", e);
		}
		return true;
	}

	@Retention(RUNTIME)
	@Target({ TYPE, METHOD })
	private @interface RequiredClass {

		String className();

	}
	
}
