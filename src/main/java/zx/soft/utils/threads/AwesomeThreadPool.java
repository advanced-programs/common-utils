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
	 * 执行多线程结果汇总
	 *
	 * @param threadNum 线程数
	 * @param calls
	 * @param cls
	 * @return
	 */
	public static <T> List<T> runCallables(int threadNum, List<Callable<T>> calls, Class<T> cls) {
		List<T> results = new ArrayList<>();
		ExecutorService executor = Executors.newFixedThreadPool(threadNum);
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
		return results;
	}

}
