package zx.soft.utils.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HTTP工具类
 * 
 * @author wanggang
 *
 */
public class HttpClientDaoImpl implements ClientDao {

	private static Logger logger = LoggerFactory.getLogger(HttpClientDaoImpl.class);

	public static void main(String[] args) {
		String url = "https://api.weibo.com/2/users/show.json?source=2936099636&uid=1732243641";
		HttpClientDaoImpl hcdi = new HttpClientDaoImpl();
		System.out.println(hcdi.doGet(url, null));
	}

	/**
	 * 新工具
	 */
	@Override
	public String doGet(String url, String cookie) {
		// 创建一个客户端，类似于打开一个浏览器
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		// 创建一个GET方法，类似于在浏览器地址栏中输入一个地址
		HttpGet httpGet = new HttpGet(url);
		if (cookie != null) {
			httpGet.setHeader("Cookie", cookie);
		}
		// 类似于在浏览器中输入回车，获得网页内容
		HttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
		} catch (IOException e) {
			logger.error("IOException in HttpClientDaoImpl: ", e);
		}
		// 查看返回内容，类似于在浏览器中查看网页源码
		HttpEntity entity = response.getEntity();
		String result = null;
		if (entity != null) {
			// 读入内容流，并以字符串形式返回，这里指定网页编码是UTF-8
			try {
				result = EntityUtils.toString(entity, "utf-8");
				// 网页的Meta标签中指定了编码
				EntityUtils.consume(entity); // 关闭内容流
			} catch (ParseException | IOException e) {
				logger.error("ParseException or IOException e=" + e.getMessage());
			}
		}
		try {
			httpClient.close();
		} catch (IOException e) {
			logger.error("IOException" + e.getMessage());
		}

		return result;
	}

	/**
	 * 执行一个HTTP GET请求，返回请求响应的HTML
	 *
	 * @param url: 请求的URL地址
	 * @param queryString: 请求的查询参数,可以为null
	 * @param charset: 字符集
	 * @param pretty: 是否美化
	 * @return: 返回请求响应的HTML
	 */
	public static String doGet(String url, String cookie, String queryString, String charset, boolean pretty) {

		StringBuffer response = new StringBuffer();
		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(url);
		if (cookie != null) {
			method.setRequestHeader("Cookie", cookie);
		}
		try {
			// 对get请求参数做了http请求默认编码，好像没有任何问题，汉字编码后，就成为%式样的字符串
			if (StringUtils.isNotBlank(queryString)) {
				method.setQueryString(URIUtil.encodeQuery(queryString));
			}
			client.executeMethod(method);
			if (method.getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(),
						charset));
				String line;
				while ((line = reader.readLine()) != null) {
					if (pretty) {
						response.append(line).append(System.getProperty("line.separator"));
					} else {
						response.append(line);
					}
				}
				reader.close();
			}
		} catch (URIException e) {
			logger.error("执行HTTP Get请求时，编码查询字符串“" + queryString + "”发生异常！", e);
		} catch (IOException e) {
			logger.error("执行HTTP Get请求" + url + "时，发生异常！", e);
		} finally {
			method.releaseConnection();
			//			client.getHttpConnectionManager().closeIdleConnections(1000);
		}
		return response.toString();
	}

	public static void doPostAndPutKeepAlive(String url, String data, String method) {

		EntityEnclosingMethod httpMethod = null;
		if ("post".equals(method)) {
			httpMethod = new PostMethod(url);
		} else if ("put".equals(method)) {
			httpMethod = new PutMethod(url);
		} else {
			return;
		}
		httpMethod.setContentChunked(true);
		RequestEntity requestEntity = null;
		try {
			requestEntity = new StringRequestEntity(data, null, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("HttpClientDaoImpl UnsupportedEncodingException:{}", e);
			return;
		}
		httpMethod.setRequestEntity(requestEntity);
		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		HttpConnectionManagerParams params = connectionManager.getParams();
		params.setMaxTotalConnections(200);
		params.setDefaultMaxConnectionsPerHost(150);
		params.setConnectionTimeout(30000);
		params.setSoTimeout(30000);
		HttpClientParams clientParams = new HttpClientParams();
		clientParams.setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
		org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient(clientParams,
				connectionManager);
		httpMethod.getParams()
				.setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
		httpMethod.addRequestHeader("Connection", "close");
		client.getParams().setBooleanParameter("http.protocol.expect-continue", false);

		try {
			client.executeMethod(httpMethod);
		} catch (IOException e) {
			logger.error("HttpClientDaoImpl IOException:{}", e);
			return;
		}
		int responseCode = httpMethod.getStatusCode();
		if (responseCode != 200 && responseCode != 201) {
			System.err.println(responseCode);
		}
		httpMethod.releaseConnection();
	}

	/**
	 * 重载函数
	 */
	public static String doGet(String url, String cookie, String charset) {
		return doGet(url, cookie, "", charset, Boolean.TRUE);
	}

	@Override
	public String doPost(String url, String data) {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-Type", "application/json");
		CloseableHttpResponse response = null;
		try {
			HttpEntity entity = new StringEntity(data, "UTF-8");
			httpPost.setEntity(entity);
			response = httpClient.execute(httpPost);
			int result = response.getStatusLine().getStatusCode();
			if (result != 200) {
				logger.error("POST request fials with data={}", data);
			}
			HttpEntity entity2 = response.getEntity();
			EntityUtils.consume(entity2);
			return result + "";
		} catch (IOException e) {
			logger.error("HttpClientDaoImpl IOException:{}", e);
			throw new RuntimeException(e);
		} finally {
			try {
				response.close();
				httpClient.close();
			} catch (IOException e) {
				logger.error("HttpClientDaoImpl IOException:{}", e);
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

}
