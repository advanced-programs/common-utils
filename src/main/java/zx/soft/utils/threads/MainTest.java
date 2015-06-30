package zx.soft.utils.threads;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
//public class MainTest {
//	public static void main(String[] args) {
//		Callable<Integer> callable = new Callable<Integer>() {
//			public Integer call() throws Exception {
//				Thread.sleep(5000);
//				return new Random().nextInt(100);
//			}
//		};
//		FutureTask<Integer> future = new FutureTask<Integer>(callable);
//		new Thread(future).start();
//
//		System.out.println("out");
//		try {
//			System.out.println(future.get());
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			e.printStackTrace();
//		}
//	}
//}
//public class MainTest {
//	public static void main(String[] args) {
//		ExecutorService threadPool = Executors.newCachedThreadPool();
//		CompletionService<Integer> cs = new ExecutorCompletionService<Integer>(threadPool);
//		for(int i = 1; i < 5; i++) {
//			final int taskID = i;
//			cs.submit(new Callable<Integer>() {
//				public Integer call() throws Exception {
//					Thread.sleep(5000);
//					return taskID;
//				}
//			});
//		}
//		// 可能做一些事情
//		for(int i = 1; i < 5; i++) {
//			try {
//				System.out.println(cs.take().get());
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			} catch (ExecutionException e) {
//				e.printStackTrace();
//			}
//		}
//		threadPool.shutdown();
//	}
//}
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainTest implements Callable<String> {

	@Override
	public String call() throws Exception {
		Thread.sleep(1000);
		//return the thread name executing this callable task
		return Thread.currentThread().getName();
	}

	public static void main(String args[]) {
		//Get ExecutorService from Executors utility class, thread pool size is 10
		ExecutorService executor = Executors.newFixedThreadPool(10);
		//create a list to hold the Future object associated with Callable
		List<Future<String>> list = new ArrayList<Future<String>>();
		//Create MyCallable instance
		Callable<String> callable = new MainTest();
		for (int i = 0; i < 100; i++) {
			//submit Callable tasks to be executed by thread pool
			Future<String> future = executor.submit(callable);
			//add Future to the list, we can get return value using Future
			list.add(future);
		}
		for (Future<String> fut : list) {
			try {
				//print the return value of Future, notice the output delay in console
				// because Future.get() waits for task to get completed
				System.out.println(new Date() + "::" + fut.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		//shut down the executor service now
		executor.shutdown();
	}

}