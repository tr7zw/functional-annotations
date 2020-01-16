package de.tr7zw.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.junit.Assert.assertEquals;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

import org.junit.Test;

public class NoArgTest {

	@Test
	@VersionInfo(version = 42)
	public void readValue() {
		FAUtil.check(this::readValue, this::checkVersion);
		assertEquals(42, value);
	}
	
	@Test
	public void readValueNonVoid() {
		booleanReturnType();
	}
	
	@VersionInfo(version = 7)
	public boolean booleanReturnType() {
		FAUtil.check(this::booleanReturnType, this::checkVersion);
		assertEquals(7, value);
		return false;
	}
	
	int value = 0;
	
	private boolean checkVersion(Method method) {
		value = method.getAnnotation(VersionInfo.class).version();
		return true;
	}
	

	@Retention(RUNTIME)
	@Target({ TYPE, METHOD })
	private @interface VersionInfo {

		int version();

	}

}
