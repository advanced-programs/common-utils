package zx.soft.utils.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.utils.log.LogbackUtil;

/**
 * 收集多线程执行的所有结果
 *
 * @author wanggang
 *
 */
public class CallableGather<T> {

	private static Logger logger = LoggerFactory.getLogger(CallableGather.class);

	// 初始化线程池服务
	private ExecutorService executor;
	// Future对象列表，存放Callable任务
	private List<Future<T>> futures;
	// Callable任务实现类，任务列表
	private List<Callable<T>> callables;

	public CallableGather(int threadNum, List<Callable<T>> callables) {
		logger.info("Thread pool's size:{}", threadNum);
		this.executor = Executors.newFixedThreadPool(threadNum);
		this.callables = callables;
		futures = new ArrayList<>();
	}

	/**
	 * 先进行Future收集
	 *
	 * @param count
	 */
	private void addCallables() {
		for (Callable<T> callable : callables) {
			// 提交Callable任务用于线程执行
			Future<T> future = executor.submit(callable);
			// 添加Future
			futures.add(future);
		}
	}

	/**
	 * 等待线程执行完毕，收集结果
	 * 注意：有延迟，Future.get()必须等线程任务执行完毕才能返回
	 *
	 * @return
	 */
	public List<T> gatherResult() {
		addCallables();
		List<T> result = new ArrayList<>();
		for (Future<T> future : futures) {
			try {
				result.add(future.get());
			} catch (InterruptedException | ExecutionException e) {
				logger.error("Thread Exception:{}", LogbackUtil.expection2Str(e));
			}
		}
		return result;
	}

	public void close() {
		executor.shutdown();
	}

}
