package zx.soft.utils.threads;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.junit.Test;

public class CallableGatherTest {

	@Test
	public void testCallableGather() {
		List<Callable<String>> callables = new ArrayList<>();
		for (int i = 0; i < 23; i++) {
			callables.add(new CallableImpl<String>(new MyCallable()));
		}
		// 初始化
		CallableGather<String> callableGather = new CallableGather<String>(10, callables);
		List<String> result = callableGather.gatherResult();
		callableGather.close();
		assertEquals(23, result.size());
	}

	@Test
	public void testCallableGatherObject() {
		List<Callable<Integer>> callables = new ArrayList<>();
		for (int i = 0; i < 25; i++) {
			callables.add(new CallableImpl<Integer>(new MyCallableObject(new Foo())));
		}
		// 初始化
		CallableGather<Integer> callableGather = new CallableGather<Integer>(10, callables);
		List<Integer> result = callableGather.gatherResult();
		callableGather.close();
		int sum = 0;
		for (Integer r : result) {
			sum += r;
		}
		assertEquals(25, result.size());
		assertEquals(25 * 10, sum);
	}

	class MyCallable implements CallableTask<String> {

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

	}

	class MyCallableObject implements CallableTask<Integer> {

		Foo foo;

		public MyCallableObject(Foo foo) {
			this.foo = foo;
		}

		@Override
		public Integer doTask() {
			return foo.getBarCount();
		}

	}

	class Foo {

		public int getBarCount() {
			return 10;
		}

	}

}
