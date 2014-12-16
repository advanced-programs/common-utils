package zx.soft.utils.driver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 进程驱动器，用于管理运行不同进程的
 * 
 * @author wanggang
 *
 */
public class ProgramDriver {

	private static Logger logger = LoggerFactory.getLogger(ProgramDriver.class);

	/**
	 * 进程描述器：基于进程的类和可读性描述
	 */
	Map<String, ProgramDescription> programs;

	public ProgramDriver() {
		programs = new TreeMap<String, ProgramDescription>();
	}

	/**
	 * 添加类到仓库
	 */
	public void addClass(String name, Class<?> mainClass, String description) throws Throwable {
		programs.put(name, new ProgramDescription(mainClass, description));
	}

	/**
	 * 主进程驱动
	 */
	public void driver(String[] args) throws Throwable {
		// 判断驱动参数是否为空
		if (args.length == 0) {
			logger.error("An main program must be given as the first argument.");
			printUsage(programs);
			System.exit(-1);
		}
		// 第一个参数存在，但是库中没有
		ProgramDescription pgm = programs.get(args[0]);
		if (pgm == null) {
			logger.error("Unknown program '" + args[0] + "' chosen.");
			printUsage(programs);
			System.exit(-1);
		}
		// 第一个参数存在且正确，截取剩余参数并启动
		String[] newArgs = new String[args.length - 1];
		System.arraycopy(args, 1, newArgs, 0, newArgs.length);
		// 唤起进程
		pgm.invoke(newArgs);
	}

	/**
	 * 打印说明
	 */
	private static void printUsage(Map<String, ProgramDescription> programs) {
		logger.error("Valid program names are:");
		for (Map.Entry<String, ProgramDescription> item : programs.entrySet()) {
			logger.error("  " + item.getKey() + ": " + item.getValue().getDescription());
		}
	}

	/**
	 * 描述器
	 */
	private static class ProgramDescription {

		static final Class<?>[] paramTypes = new Class<?>[] { String[].class };

		private final Method main;
		private final String description;

		/**
		 * 创建主进程描述
		 */
		public ProgramDescription(Class<?> mainClass, String description) throws NoSuchMethodException,
				SecurityException {
			this.main = mainClass.getMethod("main", paramTypes);
			this.description = description;
		}

		/**
		 * 通过指定参数唤起主应用或进程
		 */
		public void invoke(String[] args) throws Throwable {
			try {
				main.invoke(null, new Object[] { args });
			} catch (InvocationTargetException e) {
				throw e.getCause();
			}
		}

		public String getDescription() {
			return description;
		}

	}

}
