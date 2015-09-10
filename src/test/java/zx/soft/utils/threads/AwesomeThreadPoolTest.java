package zx.soft.utils.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.junit.Assert;
import org.junit.Test;

public class AwesomeThreadPoolTest {

	@Test
	public void testRunCallables() {
		List<Callable<String>> lists = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			lists.add(new MyCallable());
		}
		List<String> strs = AwesomeThreadPool.runCallables(5, lists);
		Assert.assertTrue(strs.size() == 10);
	}

	static class MyCallable implements Callable<String> {
		@Override
		public String call() throws Exception {
			Thread.sleep(1000);
			//return the thread name executing this callable task
			return Thread.currentThread().getName();
		}

	}

}
