package zx.soft.utils.time;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import net.jodah.concurrentunit.ConcurrentTestCase;

import org.junit.Test;

import zx.soft.utils.threads.ApplyThreadPool;

public class TimeUtilsConcurrentTest extends ConcurrentTestCase {

	@Test
	public void testDateFormatThreadSafe() throws InterruptedException {

		final ThreadPoolExecutor pool = ApplyThreadPool.getThreadPoolExector(10);

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

			@Override
			public void run() {
				pool.shutdown();
			}

		}));

		// 多线程测试
		final String ORI = "Sat May 08 05:06:45 +0800 2010";
		final String DES = "Sat May 08 05:06:45 CST 2010";

		final AtomicInteger COUNT = new AtomicInteger(0);

		for (int i = 0; i < 1000; i++) {
			pool.execute(new Runnable() {

				@Override
				public void run() {
					System.err.println(COUNT.addAndGet(1));
					//					System.err.println(TimeUtils.tranSinaApiDate(ORI));
					// junit存在并发测试问题，最终并未执行1000次，如果注释掉下面，运行上面语句则可以执行1000次。
					//					assertEquals(DES, TimeUtils.tranSinaApiDate(ORI).toString());
					threadAssertEquals(DES, TimeUtils.tranSinaApiDate(ORI).toString());
				}

			});
		}

		pool.shutdown();
		pool.awaitTermination(60, TimeUnit.SECONDS);

	}

}
