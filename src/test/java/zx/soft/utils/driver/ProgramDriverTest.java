package zx.soft.utils.driver;

import org.junit.Ignore;

public class ProgramDriverTest {

	@Ignore
	public void testProgramDriver1() {
		int exitCode = -1;
		ProgramDriver pgd = new ProgramDriver();
		try {
			pgd.addClass("helloWorld1", HelloWorld1.class, "HelloWorld1");
			pgd.addClass("helloWorld2", HelloWorld2.class, "HelloWorld2");
			pgd.driver(new String[] { "helloWorld1" });
			// Success
			exitCode = 0;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
		System.exit(exitCode);
	}

	@Ignore
	public void testProgramDriver2() {
		int exitCode = -1;
		ProgramDriver pgd = new ProgramDriver();
		try {
			pgd.addClass("helloWorld1", HelloWorld1.class, "HelloWorld1");
			pgd.addClass("helloWorld2", HelloWorld2.class, "HelloWorld2");
			pgd.driver(new String[] { "helloWorld2", "param1", "param2" });
			// Success
			exitCode = 0;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
		System.exit(exitCode);
	}

	private static class HelloWorld1 {

		@SuppressWarnings("unused")
		public static void main(String[] args) {
			System.out.println("HelloWorld1");
		}

	}

	private static class HelloWorld2 {

		@SuppressWarnings("unused")
		public static void main(String[] args) {
			if (args.length < 2) {
				System.err.println("args should more then 2.");
				System.exit(-1);
			}
			System.out.println("HelloWorld2");
			System.out.println(args[0]);
			System.out.println(args[1]);
		}

	}

}
