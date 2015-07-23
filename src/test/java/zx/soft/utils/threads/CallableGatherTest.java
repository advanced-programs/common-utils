package zx.soft.utils.threads;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.concurrent.Callable;

import org.junit.Test;

public class CallableGatherTest {

	@Test
	public void testCallableGather() {
		Callable<String> callable = new CallableImpl(new CallableTask() {

			@Override
			public String doTask() {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// 返回执行该回调任务的线程名
				return Thread.currentThread().getName();
			}

		});
		// 初始化
		CallableGather callableGather = new CallableGather(10, callable);
		List<String> result = callableGather.gatherResult(20);
		callableGather.close();
		assertEquals(20, result.size());
	}

}
