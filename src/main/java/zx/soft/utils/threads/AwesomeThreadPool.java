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
 *  多线执行结果汇总
 *
 * @author donglei
 *
 */
public class AwesomeThreadPool {

	private static Logger logger = LoggerFactory.getLogger(AwesomeThreadPool.class);

	/**
	 * 启动线程池，并得到运行结果
	 *
	 * @param num 线程池的大小
	 * @param calls 要运行的任务
	 * @param cls 返回结果类型
	 * @return
	 */
	public static <T> List<T> runCallables(int num, List<Callable<T>> calls) {
		long start = System.currentTimeMillis();
		List<T> results = new ArrayList<T>();
		ExecutorService executor = Executors.newFixedThreadPool(num);
		try {
			List<Future<T>> list = new ArrayList<>();
			for (Callable<T> call : calls) {
				Future<T> future = executor.submit(call);
				list.add(future);
			}
			for (Future<T> fut : list) {
				try {
					results.add(fut.get());
				} catch (InterruptedException | ExecutionException e) {
					logger.error("Thread Exception:{}", LogbackUtil.expection2Str(e));
				}
			}
		} finally {
			executor.shutdown();
		}
		logger.info("The time coss of pool is: {} ms", System.currentTimeMillis() - start);

		return results;
	}

}
