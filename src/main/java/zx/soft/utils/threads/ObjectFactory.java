package zx.soft.utils.threads;

import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通用对象工厂，可限制产生的对象，并提供对象池
 * @author donglei
 * @param <T>
 */
public abstract class ObjectFactory<T> {
	private static Logger logger = LoggerFactory.getLogger(ObjectFactory.class);

	private final int NUM;
	private final int TRY;
	private HashSet<T> available = new HashSet<>();
	private HashSet<T> inUse = new HashSet<>();

	protected abstract T create();

	/**
	 * 最多创建num个对象，请求tryNum次
	 * @param num
	 * @param tryNum
	 */
	public ObjectFactory(int num, int tryNum) {
		this.NUM = num;
		this.TRY = tryNum;
	}

	public ObjectFactory() {
		this.NUM = Integer.MAX_VALUE;
		this.TRY = 1;
	}

	public synchronized T checkOut() throws InterruptedException {
		boolean isGet = false;
		int numTry = 0;
		while (!isGet && numTry <= TRY) {
			if (available.size() > 0) {
				isGet = true;
				logger.info("Get an instance from the pool");
				continue;
			}
			if (inUse.size() < NUM) {
				available.add(create());
				logger.info("Create a new instance !");
				isGet = true;
			} else {
				logger.info("All instances have bean used, wait 1 second!");
				wait(1000);
				numTry++;
			}
		}
		if (!isGet) {
			logger.info("No instance available after waiting {} seconds!", TRY);
			return null;
		}
		T instance = available.iterator().next();
		available.remove(instance);
		inUse.add(instance);
		return instance;
	}

	public synchronized void checkIn(T instance) {
		inUse.remove(instance);
		available.add(instance);
		notifyAll();
	}

	@Override
	public String toString() {
		return String.format("Pool available=%d inUse=%d", available.size(), inUse.size());
	}
}
