package zx.soft.utils.system;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;

import com.sun.jna.platform.win32.Kernel32;

/**
 * 进程处理工具
 *
 * @author wanggang
 *
 */
public class ProcessAnalysis {

	private static final SigarInstance sigarInstance;

	static {
		sigarInstance = new SigarInstance();
	}

	/**
	 * 获取当前进程的Pid
	 *
	 * @return int 进程号
	 */
	public static long getCurrentPidByLang() {
		String name = ManagementFactory.getRuntimeMXBean().getName();
		return Long.parseLong(name.split("@")[0]);
	}

	public static Long getCurrentPidByProcfs() {
		String pid = "0";
		try {
			pid = new File("/proc/self").getCanonicalFile().getName();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return Long.parseLong(pid);
	}

	public static long getCurrentPidByJNA() {
		return CLibrary.INSTANCE.getpid();
	}

	public static long getCurrentWindowPidByJNA() {
		return Kernel32.INSTANCE.GetCurrentProcessId();
	}

	/**
	 * Kill进程
	 */
	public static void killPid(long pid) {
		try {
			Runtime rt = Runtime.getRuntime();
			if (System.getProperty("os.name").toLowerCase().contains("windows")) {
				rt.exec("taskkill " + pid);
			} else if (System.getProperty("os.name").toLowerCase().contains("linux")) {
				rt.exec("kill -9 " + pid);
			} else {
				throw new RuntimeException("Os.Name is not Windows or Linux.");
			}
		} catch (IOException e) {
			throw new RuntimeException("Kill Pid error.");
		}
	}

	/**
	 * 尚不能使用
	 * @return
	 */
	@Deprecated
	public static long getCurrentPidBySigar() {
		return sigarInstance.getPid();
	}

}
