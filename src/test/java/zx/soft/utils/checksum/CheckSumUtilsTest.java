package zx.soft.utils.checksum;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import zx.soft.utils.threads.ApplyThreadPool;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CheckSumUtilsTest {

	@Test
	public void testGetMD5() {
		assertEquals("d41d8cd98f00b204e9800998ecf8427e", CheckSumUtils.getMD5(""));
		assertEquals("0cc175b9c0f1b6a831c399e269772661", CheckSumUtils.getMD5("a"));
		assertEquals("900150983cd24fb0d6963f7d28e17f72", CheckSumUtils.getMD5("abc"));
		assertEquals("f96b697d7cb7938d525a2f31aaf161d0", CheckSumUtils.getMD5("message digest"));
		assertEquals("c3fcd3d76192e4007dfb496cca67e13b", CheckSumUtils.getMD5("abcdefghijklmnopqrstuvwxyz"));
	}

	@Test
	public void testGetMD5Speed() {
		assertEquals("d41d8cd98f00b204e9800998ecf8427e", CheckSumUtils.getMD5Speed(""));
		assertEquals("0cc175b9c0f1b6a831c399e269772661", CheckSumUtils.getMD5Speed("a"));
		assertEquals("900150983cd24fb0d6963f7d28e17f72", CheckSumUtils.getMD5Speed("abc"));
		assertEquals("f96b697d7cb7938d525a2f31aaf161d0", CheckSumUtils.getMD5Speed("message digest"));
		assertEquals("c3fcd3d76192e4007dfb496cca67e13b", CheckSumUtils.getMD5Speed("abcdefghijklmnopqrstuvwxyz"));
	}

	@Test
	public void testComparing_测试两种MD5算法的速度() {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			CheckSumUtils.getMD5(i + "");
		}
		long mid = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			CheckSumUtils.getMD5Speed(i + "");
		}
		long end = System.currentTimeMillis();
		assertTrue(((mid - start) > (end - mid)));
	}

	@Test
	public void testConcurrentGetMD5_测试并发情况下MD5是否出现线程问题() throws InterruptedException {
		final Map<Integer, String> md5s = new HashMap<>();
		for (int i = 0; i < 10000; i++) {
			md5s.put(i, CheckSumUtils.getMD5(i + ""));
		}
		ThreadPoolExecutor pool = ApplyThreadPool.getThreadPoolExector(10);
		for (int i = 0; i < 10000; i++) {
			pool.execute(new RunnableMD5(md5s, i + "", Boolean.TRUE));
		}
		pool.shutdown();
		pool.awaitTermination(5, TimeUnit.SECONDS);
	}

	@Test
	public void testConcurrentGetMD5Speed_测试并发情况下MD5是否出现线程问题() throws InterruptedException {
		final Map<Integer, String> md5s = new HashMap<>();
		for (int i = 0; i < 10000; i++) {
			md5s.put(i, CheckSumUtils.getMD5Speed(i + ""));
		}
		ThreadPoolExecutor pool = ApplyThreadPool.getThreadPoolExector(10);
		for (int i = 0; i < 10000; i++) {
			pool.execute(new RunnableMD5(md5s, i + "", Boolean.FALSE));
		}
		pool.shutdown();
		pool.awaitTermination(5, TimeUnit.SECONDS);
	}

	private static class RunnableMD5 implements Runnable {

		private Map<Integer, String> md5s;
		private String str;
		private boolean flag;

		public RunnableMD5(Map<Integer, String> md5s, String str, boolean flag) {
			this.md5s = md5s;
			this.str = str;
			this.flag = flag;
		}

		@Override
		public void run() {
			if (flag) {
				assertEquals(md5s.get(Integer.parseInt(str)), CheckSumUtils.getMD5(str));
			} else {
				assertEquals(md5s.get(Integer.parseInt(str)), CheckSumUtils.getMD5Speed(str));
			}
		}

	}

	public void testString2MD57() {
		assertEquals("d174ab98d277d9f5a5611c2c9f419d9f",
				CheckSumUtils.getMD5("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"));
	}

	@Test
	public void testString2MD58() {
		assertEquals("57edf4a22be3c955ac49da2e2107b67a",
				CheckSumUtils
						.getMD5("12345678901234567890123456789012345678901234567890123456789012345678901234567890"));
	}

	@Test
	public void testString2MD51() {
		assertEquals("a906449d5769fa7361d7ecc6aa3f6d28", CheckSumUtils.getMD5("123abc"));
	}

	@Test
	public void testString2MD52() {
		assertEquals("d41d8cd98f00b204e9800998ecf8427e", CheckSumUtils.getMD5(""));
		assertEquals(16, CheckSumUtils.md5sum("").length);
	}

	@Test
	public void testString2MD53() {
		assertEquals("0cc175b9c0f1b6a831c399e269772661", CheckSumUtils.getMD5("a"));
		assertEquals(16, CheckSumUtils.md5sum("a").length);
	}

	@Test
	public void testString2MD54() {
		assertEquals("900150983cd24fb0d6963f7d28e17f72", CheckSumUtils.getMD5("abc"));
		assertEquals(16, CheckSumUtils.md5sum("abc").length);

	}

	@Test
	public void testString2MD55() {
		assertEquals("f96b697d7cb7938d525a2f31aaf161d0", CheckSumUtils.getMD5("message digest"));
	}

	@Test
	public void testString2MD56() {
		assertEquals("c3fcd3d76192e4007dfb496cca67e13b", CheckSumUtils.getMD5("abcdefghijklmnopqrstuvwxyz"));
	}

	@Test
	public void testCRC32() {
		assertTrue(CheckSumUtils.getCRC32("123abcfffeettt") == 4042279602L);
	}

}
