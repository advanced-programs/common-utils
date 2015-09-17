package zx.soft.utils.time;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import zx.soft.utils.threads.ApplyThreadPool;

public class TimeUtilsTest {

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
					assertEquals(DES, TimeUtils.tranSinaApiDate(ORI));
				}

			});
		}

		pool.shutdown();
		pool.awaitTermination(60, TimeUnit.SECONDS);

	}

	public static void assertConcurrent(final String message, final List<? extends Runnable> runnables,
			final int maxTimeoutSeconds) throws InterruptedException {
		final int numThreads = runnables.size();
		final List<Throwable> exceptions = Collections.synchronizedList(new ArrayList<Throwable>());
		final ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
		try {
			final CountDownLatch allExecutorThreadsReady = new CountDownLatch(numThreads);
			final CountDownLatch afterInitBlocker = new CountDownLatch(1);
			final CountDownLatch allDone = new CountDownLatch(numThreads);
			for (final Runnable submittedTestRunnable : runnables) {
				threadPool.submit(new Runnable() {
					@Override
					public void run() {
						allExecutorThreadsReady.countDown();
						try {
							afterInitBlocker.await();
							submittedTestRunnable.run();
						} catch (final Throwable e) {
							exceptions.add(e);
						} finally {
							allDone.countDown();
						}
					}
				});
			}
			// wait until all threads are ready
			assertTrue(
					"Timeout initializing threads! Perform long lasting initializations before passing runnables to assertConcurrent",
					allExecutorThreadsReady.await(runnables.size() * 10, TimeUnit.MILLISECONDS));
			// start all test runners
			afterInitBlocker.countDown();
			assertTrue(message + " timeout! More than" + maxTimeoutSeconds + "seconds",
					allDone.await(maxTimeoutSeconds, TimeUnit.SECONDS));
		} finally {
			threadPool.shutdownNow();
		}
		assertTrue(message + "failed with exception(s)" + exceptions, exceptions.isEmpty());
	}

	@Test
	public void testSINA_API_FORMAT() {
		Date date = TimeUtils.tranSinaApiDate("Sat May 08 05:06:45 +0800 2010");
		assertEquals("Sat May 08 05:06:45 CST 2010", date.toString());
	}

	@Test
	public void testTransToSolrDateStr() {
		String str = TimeUtils.transToSolrDateStr(System.currentTimeMillis());
		assertTrue(str.length() == 20);
		assertTrue(str.contains("T"));
		assertTrue(str.contains("Z"));
	}

	@Test
	public void testTransStrToCommonDateStr() {
		String str = TimeUtils.transToCommonDateStr(TimeUtils.transToSolrDateStr(System.currentTimeMillis()));
		assertTrue(str.length() == 19);
		assertFalse(str.contains("T"));
		assertFalse(str.contains("Z"));

		assertTrue(TimeUtils.transStrToCommonDateStr("Thu Apr 10 11:40:56 CST 2014").contains("10 11:40:56"));
	}

	@Test
	public void testTransLongToCommonDateStr() {
		String str = TimeUtils.transToCommonDateStr(System.currentTimeMillis());
		assertTrue(str.length() == 19);
		assertFalse(str.contains("T"));
		assertFalse(str.contains("Z"));
	}

	@Test
	public void testTransTimeStr() {
		assertEquals("2014-08-25T00:00:00Z", TimeUtils.transTimeStr("2014-08-25 00:00:00"));
	}

	@Test
	public void testTransTimeLong() {
		assertEquals(1408896000000L, TimeUtils.transTimeLong("2014-08-25 00:00:00"));
	}

	@Test
	public void testTransTwitterTimeLong() {
		assertEquals(1430495417083L, TimeUtils.transTwitterTimeLong("2015-05-01T23:50:17.083Z"));
	}

	@Test
	public void testGetMidnight() {
		long midight = TimeUtils.getMidnight(1430495417083L, -1);
		assertEquals("2015-04-30T00:00:00Z", TimeUtils.transToSolrDateStr(midight));
	}

	@Test
	public void testTransCurrentTime() {
		long time = TimeUtils.transCurrentTime(1430495417083L, 0, 0, -1, 0);
		assertEquals("2015-04-30T23:50:17Z", TimeUtils.transToSolrDateStr(time));
	}

	@Test
	public void testDateHour() {
		assertEquals("2015-09-17,10", TimeUtils.timeStrByHour(1442457761229L));
	}

	@Test
	public void testGetDateFormat() throws ParseException {
		assertEquals("2015-09-17,10", TimeUtils.getDateFormat("yyyy-MM-dd,HH").format(new Date(1442457761229L))
				.toString());
	}

	@Test
	public void testAll() {
		assertEquals("2014-04-10 11:40:56", TimeUtils.transStrToCommonDateStr("Thu Apr 10 11:40:56 CST 2014"));
		assertEquals("2014-04-10 03:40:56", TimeUtils.transStrToCommonDateStr("Thu Apr 10 11:40:56 CST 2014", 8));
		assertEquals("2015-08-17T00:00:00Z", TimeUtils.transToSolrDateStr(TimeUtils.getMidnight(1442457761229L, -31)));
		assertEquals("2015-08-17T10:42:41Z",
				TimeUtils.transToSolrDateStr(TimeUtils.transCurrentTime(1442457761229L, 0, 0, -31, 0)));
		assertEquals("1H 30M 10S 0MS ", TimeUtils.convertMilliToStr(1 * 3600 * 1000 + 30 * 60 * 1000 + 10000));
	}

}
