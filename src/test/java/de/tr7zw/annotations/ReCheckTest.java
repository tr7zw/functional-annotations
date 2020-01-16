package de.tr7zw.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.junit.Assert.assertEquals;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

import org.junit.Test;

/**
 * The checkVersion method has noRechecking enabled (returns true), so non error
 * throwing methods will be checked once and than never again
 * 
 * @author tr7zw
 *
 */
public class ReCheckTest {

	@Test
	public void testRechecking() {
		noError();
		assertEquals(1, checks);
		noError();
		assertEquals(1, checks);
		try {
			throwsError();
		} catch (Throwable e) {
		}
		assertEquals(2, checks);
		try {
			throwsError();
		} catch (Throwable e) {
		}
		assertEquals(3, checks);
	}

	@VersionInfo(version = 42)
	public void throwsError() {
		FAUtil.check(this::throwsError, this::checkVersion);
	}

	@VersionInfo(version = 43)
	public void noError() {
		FAUtil.check(this::noError, this::checkVersion);
	}

	int checks = 0;

	private boolean checkVersion(Method method) {
		checks++;
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
