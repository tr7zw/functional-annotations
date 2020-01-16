package de.tr7zw.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.junit.Assert.assertEquals;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.junit.Test;

public class GetAnnotationTest {

	@Test
	@VersionInfo(version = 42)
	public void readValue() {
		assertEquals(42, FAUtil.getAnnotation(this::readValue, VersionInfo.class).version());
	}

	@Retention(RUNTIME)
	@Target({ TYPE, METHOD })
	private @interface VersionInfo {

		int version();

	}
	
}
