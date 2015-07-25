package zx.soft.utils.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author donglei
 *
 */
public class AwesomeThreadPool {
	public static <T> List<T> runCallables(int num, List<Callable<T>> calls, Class<T> cls) {
		List<T> results = new ArrayList<T>();
		ExecutorService executor = Executors.newFixedThreadPool(num);
		try {
			List<Future<T>> list = new ArrayList<Future<T>>();
			for (Callable<T> call : calls) {
				Future<T> future = executor.submit(call);
				list.add(future);
			}
			for (Future<T> fut : list) {
				try {
					results.add(fut.get());
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}

		} finally {
			executor.shutdown();
		}
		return results;
	}

}
