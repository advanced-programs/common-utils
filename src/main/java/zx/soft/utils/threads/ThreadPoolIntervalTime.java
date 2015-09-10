package zx.soft.utils.threads;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.utils.log.LogbackUtil;

/**
 * 该类实现的线程池，可以在指定时间间隔内有只有maxCurrentThread个任务执行，同时也可以设定在线程执行结束后释放的时间。
 *
 * @author wanggang
 *
 */
public class ThreadPoolIntervalTime {

	private static Logger logger = LoggerFactory.getLogger(ThreadPoolIntervalTime.class);

	// 等待任务队列
	private BlockingQueue<Runnable> waitingTasks;

	// 最大执行线程数
	private int maxCurrentThread;

	// 时间间隔，毫秒
	private long intervalTime;

	// 存活时间，毫秒
	private long aliveTime;

	// 工作线程集合
	private static final Set<Worker> WORKERS = new HashSet<>();

	/**
	 * 构造函数
	 *
	 * @param waitingTasks
	 * @param maxCurrentThread
	 * @param intervalTime
	 * @param aliveTime
	 */
	public ThreadPoolIntervalTime(BlockingQueue<Runnable> waitingTasks, int maxCurrentThread, long intervalTime,
			long aliveTime) {
		this.waitingTasks = waitingTasks;
		this.maxCurrentThread = maxCurrentThread;
		this.intervalTime = intervalTime;
		this.aliveTime = aliveTime;
	}

	public ThreadPoolIntervalTime(BlockingQueue<Runnable> waitingTasks, long intervalTime, long aliveTime) {
		this(waitingTasks, 1, intervalTime, aliveTime);
	}

	public ThreadPoolIntervalTime(BlockingQueue<Runnable> waitingTasks, long intervalTime) {
		this(waitingTasks, intervalTime, 0L);
	}

	public ThreadPoolIntervalTime(long intervalTime, long aliveTime) {
		this(new LinkedBlockingQueue<Runnable>(), intervalTime, aliveTime);
	}

	public ThreadPoolIntervalTime(long intervalTime) {
		this(intervalTime, 0L);
	}

	public ThreadPoolIntervalTime() {
		this(0L);
	}

	public int getWorkersSize() {
		return WORKERS.size();
	}

	public int getWaitingTasksSize() {
		return waitingTasks.size();
	}

	/**
	 * 提交Callable任务，返回Future任务
	 *
	 * @param callable
	 * @param <T>
	 * @return future任务
	 */
	public <T> Future<T> submit(Callable<T> callable) {
		if (callable == null) {
			throw new IllegalArgumentException("Callable can't be null");
		}
		RunnableFuture<T> task = new FutureTask<>(callable);
		excute(task);
		return task;
	}

	/**
	 * 添加任务到等待队列中，并且等待执行
	 *
	 * @param task
	 */
	private void excute(Runnable task) {
		if (task == null) {
			throw new IllegalArgumentException("Runnable can't be null");
		}
		synchronized (waitingTasks) {
			// 插入任务到队列中
			waitingTasks.offer(task);
		}
		synchronized (WORKERS) {
			if (WORKERS.size() < maxCurrentThread) {
				Worker worker = new Worker();
				WORKERS.add(worker);
				worker.start();
			}
		}
	}

	private class Worker extends Thread {

		private Runnable runnable;

		@Override
		public void run() {
			long lastExecuteTime = 0L;
			try {
				while (true) {
					// 根据存活时间从队列中提取一个等待任务
					if (aliveTime > 0) {
						runnable = waitingTasks.poll(aliveTime, TimeUnit.MILLISECONDS);
					} else {
						runnable = waitingTasks.poll();
					}
					// 计算该相邻两次任务执行的间隔时间
					long now = System.currentTimeMillis();
					if (now - lastExecuteTime < intervalTime) {
						sleep(now - lastExecuteTime);
					}
					lastExecuteTime = System.currentTimeMillis();
					if (runnable != null) {
						runnable.run();
					} else {
						break;
					}
				}
			} catch (InterruptedException e) {
				logger.error("Thread Exception:{}", LogbackUtil.expection2Str(e));
			}

			// 该任务不能执行了，要从工作队列中删除
			synchronized (WORKERS) {
				WORKERS.remove(this);
				if (WORKERS.isEmpty()) {
					WORKERS.notifyAll();
				}
			}
		}

	}

}
