package zx.soft.utils.threads;

import java.util.concurrent.Callable;

/**
 * Callable执行任务
 *
 * @author wanggang
 *
 */
public class CallableImpl<T> implements Callable<T> {

	private CallableTask<T> callableTask;

	public CallableImpl(CallableTask<T> callableTask) {
		this.callableTask = callableTask;
	}

	@Override
	public T call() throws Exception {
		return callableTask.doTask();
	}

}
