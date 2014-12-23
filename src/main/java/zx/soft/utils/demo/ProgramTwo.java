package zx.soft.utils.demo;

public class ProgramTwo {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("程序2没有输入参数");
			System.exit(-1);
		}
		System.out.println("程序2的参数：" + args[0]);
		System.out.println("程序2");
	}

}
