package zx.soft.utils.http;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class HttpClientDaoImplTest {

	public static final String URL = "https://api.weibo.com/2/statuses/user_timeline.json?source=140226478&uid=2230913455&page=1&count=2";

	public static ClientDao clientDao;

	@BeforeClass
	public static void prepare() {
		clientDao = new HttpClientDaoImpl();
	}

	@Test
	public void testdoGet1() {
		String res = clientDao.doGet(URL);
		assertTrue(res.contains("statuses"));
	}

	@Test
	public void testdoGet2() {
		String res = clientDao.doGet(URL, "UTF-8");
		assertTrue(res.contains("statuses"));
	}

	@Test
	public void testdoGet31() {
		String res = clientDao.doGet(URL, "", "UTF-8");
		assertTrue(res.contains("statuses"));
	}

	@Test
	public void testdoGet4() {
		String res = clientDao.doGet(URL, null, null, "UTF-8");
		assertTrue(res.contains("statuses"));
	}

	@AfterClass
	public static void clear() {
		clientDao = null;
	}

}
