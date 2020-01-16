package de.tr7zw.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

import org.junit.Test;

public class ErrorTest {

	@Test(expected = Error.class)
	@VersionInfo(version = 42)
	public void throwsError() {
		FAUtil.check(this::throwsError, this::checkVersion);
	}
	
	@Test
	@VersionInfo(version = 43)
	public void noError() {
		FAUtil.check(this::noError, this::checkVersion);
	}

	private boolean checkVersion(Method method) {
		int value = method.getAnnotation(VersionInfo.class).version();
		if (value == 42)
			throw new Error();
		return true;
	}

	@Retention(RUNTIME)
	@Target({ TYPE, METHOD })
	private @interface VersionInfo {

		int version();

	}

}
