package zx.soft.utils.expiringmap;

public final class Assert {

	private Assert() {
	}

	public static <T> T notNull(T reference, String parameterName) {
		if (reference == null)
			throw new NullPointerException(parameterName + " cannot be null");
		return reference;
	}

	public static void operation(boolean condition, String message) {
		if (!condition)
			throw new UnsupportedOperationException(message);
	}

	public static void state(boolean expression, String errorMessageFormat, Object... args) {
		if (!expression)
			throw new IllegalStateException(String.format(errorMessageFormat, args));
	}

}
