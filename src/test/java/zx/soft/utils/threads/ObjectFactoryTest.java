package zx.soft.utils.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.Test;

public class ObjectFactoryTest {

	@Test
	public void testSingleThread() throws InterruptedException {
		StringPool pool = new StringPool(3, 2);
		String oliphaunt1 = pool.checkOut();
		String oliphaunt2 = pool.checkOut();
		String oliphaunt3 = pool.checkOut();
		String oliphaunt4 = pool.checkOut();
		Assert.assertTrue(oliphaunt4 == null);
		System.out.println(pool);
		pool.checkIn(oliphaunt1);
		String oliphaunt5 = pool.checkOut();
		Assert.assertTrue(oliphaunt1 == oliphaunt5);
		pool.checkIn(oliphaunt2);
		String oliphaunt6 = pool.checkOut();
		Assert.assertTrue(oliphaunt2 == oliphaunt6);
	}

	@Test
	public void testMultiThread() throws InterruptedException {
		final StringPool pool = new StringPool(3, 2);
		List<String> results = new ArrayList<>();
		ExecutorService executor = Executors.newFixedThreadPool(4);
		List<Future<String>> list = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			Future<String> future = executor.submit(new Callable<String>() {

				@Override
				public String call() throws Exception {
					String jdbc = null;
					try {
						jdbc = pool.checkOut();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					return jdbc;
				}
			});
			list.add(future);
		}
		for (Future<String> fut : list) {
			try {
				results.add(fut.get());
			} catch (InterruptedException | ExecutionException e) {
			}
		}
		Assert.assertEquals("[0, 1, 2, null]", results.toString());
	}

	@Test
	public void testMultiThread2() throws InterruptedException {
		final StringPool pool = new StringPool(3, 2);
		List<String> results = new ArrayList<>();
		ExecutorService executor = Executors.newFixedThreadPool(4);
		List<Future<String>> list = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			Future<String> future = executor.submit(new Callable<String>() {

				@Override
				public String call() throws Exception {
					String jdbc = null;
					try {
						jdbc = pool.checkOut();
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						if (jdbc != null) {
							pool.checkIn(jdbc);
						}
					}
					return jdbc;
				}
			});
			list.add(future);
		}
		for (Future<String> fut : list) {
			try {
				results.add(fut.get());
			} catch (InterruptedException | ExecutionException e) {
			}
		}
		Assert.assertNotEquals(null, results.get(results.size() - 1));
	}
}
