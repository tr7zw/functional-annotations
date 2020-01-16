package de.tr7zw.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.junit.Assert.assertEquals;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

import org.junit.Test;

public class ArgsTest {

	@Test
	public void testArgs() {
		oneArg("");
		oneArgVoid("");
	}
	
	@VersionInfo(version = 9)
	public String oneArg(String str) {
		FAUtil.check(this::oneArg, this::checkVersion);
		assertEquals(9, value);
		return str;
	}
	
	@VersionInfo(version = 42)
	public void oneArgVoid(String str) {
		FAUtil.check(this::oneArgVoid, this::checkVersion);
		assertEquals(42, value);
	}
	
	int value = 0;
	
	private boolean checkVersion(Method method) {
		value = method.getAnnotation(VersionInfo.class).version();
		return false;
	}

	
	@Retention(RUNTIME)
	@Target({ TYPE, METHOD })
	private @interface VersionInfo {

		int version();

	}
	
}
