package zx.soft.utils.threads;

import org.junit.Assert;
import org.junit.Test;

public class ObjectFactoryTest {

	@Test
	public void test() throws InterruptedException {
		StringPool pool = new StringPool(3);
		System.out.println(pool);
		String oliphaunt1 = pool.checkOut();
		System.out.println("Checked out " + oliphaunt1);
		System.out.println(pool);
		String oliphaunt2 = pool.checkOut();
		System.out.println("Checked out " + oliphaunt2);
		String oliphaunt3 = pool.checkOut();
		System.out.println("Checked out " + oliphaunt3);
		String oliphaunt4 = pool.checkOut();
		Assert.assertTrue(oliphaunt4 == null);
		System.out.println(pool);
		pool.checkIn(oliphaunt1);
		System.out.println("Checking in " + oliphaunt1);
		String oliphaunt5 = pool.checkOut();
		Assert.assertTrue(oliphaunt1 == oliphaunt5);
		System.out.println("Checked out " + oliphaunt5);
		pool.checkIn(oliphaunt2);
		System.out.println("Checking in " + oliphaunt2);
		System.out.println(pool);
		String oliphaunt6 = pool.checkOut();
		Assert.assertTrue(oliphaunt2 == oliphaunt6);
		System.out.println("Checked out " + oliphaunt5);
		System.out.println(pool);
	}

}
