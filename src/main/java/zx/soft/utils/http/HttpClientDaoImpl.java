package zx.soft.utils.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map.Entry;

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
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.utils.log.LogbackUtil;

/**
 * HTTP工具类
 *
 * @author wanggang
 *
 */
public class HttpClientDaoImpl implements ClientDao {

	private static Logger logger = LoggerFactory.getLogger(HttpClientDaoImpl.class);

	/**
	 * 新工具，线程安全
	 */

	@Override
	public String doGet(String url) {
		return doGet(url, "UTF-8");
	}

	@Override
	public String doGet(String url, String charset) {
		return doGet(url, "", charset);
	}

	@Override
	public String doGet(String url, String cookie, String charset) {
		return doGet(url, null, cookie, charset);
	}

	@Override
	public String doGet(String url, HashMap<String, String> headers) {
		return doGet(url, headers, "UTF-8");
	}

	@Override
	public String doGet(String url, HashMap<String, String> headers, String charset) {
		return doGet(url, headers, null, charset);
	}

	// 为了适应CDH5.3.3版本，httpclient和httpcore必须微4.2.5老版本，索引CloseableHttpClient只有在新版本中才可以使用
	@Override
	public String doGet(String url, HashMap<String, String> headers, String cookie, String charset) {
		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(url);
		if (headers != null) {
			for (Entry<String, String> header : headers.entrySet()) {
				method.setRequestHeader(header.getKey(), header.getValue());
			}
		}
		if ((cookie != null) && (cookie.length() > 0)) {
			method.setRequestHeader("Cookie", cookie);
		}
		try {
			client.executeMethod(method);
			StringBuffer sb = new StringBuffer();
			try (BufferedReader br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));) {
				String str = "";
				while ((str = br.readLine()) != null) {
					sb.append(str);
				}
			}
			return sb.toString();
		} catch (URIException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		} catch (IOException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		} finally {
			method.releaseConnection();
			//			client.getHttpConnectionManager().closeIdleConnections(1000);
		}
	}

	/**
	 * 旧工具
	 */

	@Deprecated
	public static String doGetOld(String url, String cookie, String charset) {
		return doGetOld(url, cookie, "", charset, Boolean.TRUE);
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
	@Deprecated
	public static String doGetOld(String url, String cookie, String queryString, String charset, boolean pretty) {

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
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
		} catch (IOException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
		} finally {
			method.releaseConnection();
			//			client.getHttpConnectionManager().closeIdleConnections(1000);
		}
		return response.toString();
	}

	@Override
	public String doPostAndPutKeepAlive(String url, String data) {
		return doPostAndPutKeepAlive(url, data, "UTF-8");
	}

	@Override
	public String doPostAndPutKeepAlive(String url, String data, String charset) {
		EntityEnclosingMethod httpMethod = new PostMethod(url);
		httpMethod.setContentChunked(true);
		RequestEntity requestEntity = null;
		try {
			requestEntity = new StringRequestEntity(data, null, charset);
		} catch (UnsupportedEncodingException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			return "error";
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
		httpMethod.addRequestHeader("Content-Type", "application/json");
		client.getParams().setBooleanParameter("http.protocol.expect-continue", false);

		try {
			client.executeMethod(httpMethod);
		} catch (IOException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			return "error";
		}
		int responseCode = httpMethod.getStatusCode();
		if (responseCode != 200 && responseCode != 201) {
			System.err.println(responseCode);
		}
		httpMethod.releaseConnection();

		return httpMethod.getStatusText();
	}

	@Override
	public String doPostAndGetResponse(String url, String data) {
		return doPostAndGetResponse(url, data, "UTF-8");
	}

	@Override
	public String doPostAndGetResponse(String url, String data, String charset) {
		EntityEnclosingMethod httpMethod = new PostMethod(url);
		httpMethod.setContentChunked(true);
		RequestEntity requestEntity = null;
		try {
			requestEntity = new StringRequestEntity(data, null, charset);
		} catch (UnsupportedEncodingException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			return "error";
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
		httpMethod.addRequestHeader("Content-Type", "application/json");
		client.getParams().setBooleanParameter("http.protocol.expect-continue", false);

		try {
			client.executeMethod(httpMethod);
			int responseCode = httpMethod.getStatusCode();
			if (responseCode != 200 && responseCode != 201) {
				System.err.println(responseCode);
			}
			return httpMethod.getResponseBodyAsString();
		} catch (IOException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
		} finally {
			httpMethod.releaseConnection();
		}
		return "error";

	}

	public EntityEnclosingMethod doPostAndKeepAlive(String url, String data, String charset) {

		EntityEnclosingMethod httpMethod = new PostMethod(url);
		httpMethod.setContentChunked(true);
		RequestEntity requestEntity = null;
		try {
			requestEntity = new StringRequestEntity(data, null, charset);
		} catch (UnsupportedEncodingException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			return null;
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
		httpMethod.addRequestHeader("Content-Type", "application/json");
		client.getParams().setBooleanParameter("http.protocol.expect-continue", false);

		try {
			client.executeMethod(httpMethod);
		} catch (IOException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			return null;
		}
		int responseCode = httpMethod.getStatusCode();
		if (responseCode != 200 && responseCode != 201) {
			logger.error(responseCode + "");
		}
		httpMethod.releaseConnection();

		return httpMethod;
	}

	@Override
	public String doPost(String url, String data) {
		return doPost(url, data, "UTF-8");
	}

	/**
	 * TODO 需要更改
	 */
	@Override
	public String doPost(String url, String data, String charset) {
		// 为了适应CDH5.3.3版本，httpclient和httpcore必须微4.2.5老版本
		// TODO
		return null;
	}

	@Override
	public void close() {
		// TODO
	}

}
