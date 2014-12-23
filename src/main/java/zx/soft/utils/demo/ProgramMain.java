package zx.soft.utils.demo;

import zx.soft.utils.driver.ProgramDriver;

public class ProgramMain {

	public static void main(String[] args) {

		ProgramDriver pgd = new ProgramDriver();
		int errCode = -1;
		try {
			pgd.addClass("programOne", ProgramOne.class, "测试程序1");
			pgd.addClass("programTwo", ProgramTwo.class, "测试程序2");
			pgd.driver(args);
			errCode = 0;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
		System.exit(errCode);
	}

}
