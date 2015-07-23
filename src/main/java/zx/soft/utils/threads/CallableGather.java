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
public class CallableGather {

	private static Logger logger = LoggerFactory.getLogger(CallableGather.class);

	// 初始化线程池服务
	private ExecutorService executor;
	// Future对象列表，存放Callable任务
	private List<Future<String>> futures;
	// Callable任务实现类
	private Callable<String> callable;

	public CallableGather(int threadNum, Callable<String> callable) {
		logger.info("Thread pool'size:{}", threadNum);
		this.executor = Executors.newFixedThreadPool(threadNum);
		this.callable = callable;
		futures = new ArrayList<>();
	}

	/**
	 * 先进行Future收集
	 *
	 * @param count
	 */
	private void addCallables(int count) {
		for (int i = 0; i < count; i++) {
			// 提交Callable任务用于线程执行
			Future<String> future = executor.submit(callable);
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
	public List<String> gatherResult(int count) {
		addCallables(count);
		List<String> result = new ArrayList<>();
		for (Future<String> future : futures) {
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
