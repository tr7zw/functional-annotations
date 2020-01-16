package de.tr7zw.annotations;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.function.Function;

public class FAUtil {

	public static void check(MethodRefrence method, Function<Method, Boolean> checker) {
		checkLambda(method, checker);
	}

	public static <T> T check(MethodRefrence1<T> method, Function<Method, Boolean> checker) {
		checkLambda(method, checker);
		return null;
	}

	public static <T, Z> T check(MethodRefrence2<T, Z> method, Function<Method, Boolean> checker) {
		checkLambda(method, checker);
		return null;
	}

	private static HashSet<String> cache = new HashSet<>();

	private static void checkLambda(Object obj, Function<Method, Boolean> callable) {
		if (cache.contains(obj.toString().split("/")[0]))
			return;
		System.out.println(obj.toString().split("/")[0]);
		for (Class<?> cl = obj.getClass(); cl != null; cl = cl.getSuperclass()) {
			try {
				Method m = cl.getDeclaredMethod("writeReplace");
				m.setAccessible(true);
				Object replacement = m.invoke(obj);
				if (!(replacement instanceof SerializedLambda))
					break;// custom interface implementation
				SerializedLambda l = (SerializedLambda) replacement;
				for (Method method : Class.forName(l.getImplClass().replace('/', '.')).getDeclaredMethods()) {
					if (method.getName().equals(l.getImplMethodName())) { // TODO maybe it is possible to use toGenericString
																			// and getImplMethodSignature to make it
																			// work with methods that have same name?
						Boolean noRechecking = callable.apply(method);
						if(noRechecking == true) {// null is possible
							cache.add(obj.toString().split("/")[0]);
						}
						return;
					}
				}
			} catch (IllegalAccessException | InvocationTargetException e) {
				break;
			} catch (Exception e) {
			}
		}
		cache.add(obj.toString().split("/")[0]);
	}

}
