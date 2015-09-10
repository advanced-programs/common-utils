package zx.soft.utils.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Test;

public class ThreadPoolIntervalTimeTest {

	@Test
	public void testThreadPoolIntervalTime() {
		// Callable列表
		List<Callable<String>> callables = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			callables.add(new CallableImpl<String>(new CallableTaskImpl()));
		}
		ThreadPoolIntervalTime pool = new ThreadPoolIntervalTime(new LinkedBlockingQueue<Runnable>(), 2, 4_000L, 5_000L);
		System.err.println("任务添加之前，工作线程个数为： " + pool.getWorkersSize() + ", 等待队列大小为： " + pool.getWaitingTasksSize());
		for (int i = 0; i < callables.size(); i++) {
			pool.submit(callables.get(i));
			System.err.println("第i个任务添加后，工作线程个数为： " + pool.getWorkersSize() + ", 等待队列大小为： "
					+ pool.getWaitingTasksSize());
		}
		System.err.println("任务添加完成后，工作线程个数为： " + pool.getWorkersSize() + ", 等待队列大小为： " + pool.getWaitingTasksSize());
	}

	class CallableTaskImpl implements CallableTask<String> {

		@Override
		public String doTask() {
			// 返回执行该回调任务的线程名
			System.out.println("Hello: " + Thread.currentThread().getName());
			return "ok: " + Thread.currentThread().getName();
		}

	}

}
