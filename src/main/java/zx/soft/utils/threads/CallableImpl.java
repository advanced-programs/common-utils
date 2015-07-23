package zx.soft.utils.threads;

import java.util.concurrent.Callable;

/**
 * Callable执行任务
 *
 * @author wanggang
 *
 */
public class CallableImpl implements Callable<String> {

	private CallableTask callableTask;

	public CallableImpl(CallableTask callableTask) {
		this.callableTask = callableTask;
	}

	@Override
	public String call() throws Exception {
		return callableTask.doTask();
	}

}
