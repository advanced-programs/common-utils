package zx.soft.utils.checksum;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CheckSumUtilsTest {

	@Test
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
		assertEquals(CheckSumUtils.MD5_LENGTH, CheckSumUtils.md5sum("").length);
	}

	@Test
	public void testString2MD53() {
		assertEquals("0cc175b9c0f1b6a831c399e269772661", CheckSumUtils.getMD5("a"));
		assertEquals(CheckSumUtils.MD5_LENGTH, CheckSumUtils.md5sum("a").length);
	}

	@Test
	public void testString2MD54() {
		assertEquals("900150983cd24fb0d6963f7d28e17f72", CheckSumUtils.getMD5("abc"));
		assertEquals(CheckSumUtils.MD5_LENGTH, CheckSumUtils.md5sum("abc").length);

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
