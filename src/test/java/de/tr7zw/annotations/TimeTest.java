package de.tr7zw.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.junit.Assert.assertTrue;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.junit.Test;

public class TimeTest {

	@Test
	@VersionInfo(version = 42)
	public void readValue() {
		FAUtil.getAnnotation(this::readValue, VersionInfo.class).version(); // Load the classes and precache everything,
																			// takes ~35ms on my pc
		long time = System.currentTimeMillis();
		int version = FAUtil.getAnnotation(this::readValue, VersionInfo.class).version(); // 0ms
		time = System.currentTimeMillis() - time;
		System.out.println(version);
		System.out.println(time);
		assertTrue(time <= 2);
	}

	@Retention(RUNTIME)
	@Target({ TYPE, METHOD })
	private @interface VersionInfo {

		int version();

	}

}
