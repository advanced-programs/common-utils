package zx.soft.utils.checksum;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

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
		assertEquals("d41d8cd98f00b204e9800998ecf8427e", CheckSumUtils.getMD5(""));
		assertEquals("d41d8cd98f00b204e9800998ecf8427e", CheckSumUtils.getMD5Speed(""));
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
	public void testCRC32() {
		assertTrue(CheckSumUtils.getCRC32("123abcfffeettt") == 4042279602L);
	}

}
