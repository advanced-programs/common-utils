package zx.soft.utils.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基于httpclient-4.4.1版本实现Http相关工具
 *
 * <依赖包>  httpclient-4.4.1.jar  httpcore-4.4.1.jar  commons-logging-1.2.jar   commons-codec-1.9.jar
 *
 * <执行步骤>
 *     Now that we have all the required dependencies, below are the steps for using HttpClient to send GET/POST requests.
 * <1> Create instance of CloseableHttpClient using helper class HttpClients.
 * <2> Create HttpGet or HttpPost instance based on the HTTP request type.
 * <3> Use addHeader method to add required headers such as User-Agent, Accept-Encoding etc.
 * <4> For POST, create list of NameValuePair and add all the form parameters. Then set it to the HttpPost entity.
 * <5> Get CloseableHttpResponse by executing the HttpGet or HttpPost request.
 * <6> Get required details such as status code, error information, response html etc from the response.
 * <7> Finally close the HttpClient resource.
 *
 * @author wanggang
 *
 */
public class HttpClient4Impl {

	private static Logger logger = LoggerFactory.getLogger(HttpClient4Impl.class);

	// Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.114 Safari/537.36
	private static final String USER_AGENT = "Mozilla/5.0";

	public static void main(String[] args) {
		//

	}

	public static String doGet(String url) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpGet httpGet = new HttpGet(url);
			httpGet.addHeader("User-Agent", USER_AGENT);
			CloseableHttpResponse httpResponse;
			httpResponse = httpClient.execute(httpGet);
			System.out.println("GET Response Status:: " + httpResponse.getStatusLine().getStatusCode());
			StringBuffer response = new StringBuffer();
			try (BufferedReader reader = new BufferedReader(
					new InputStreamReader(httpResponse.getEntity().getContent()));) {
				String inputLine;
				while ((inputLine = reader.readLine()) != null) {
					response.append(inputLine);
				}
			}
			return response.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static String doPOST(String url, String data) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader("User-Agent", USER_AGENT);

			//		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			//		urlParameters.add(new BasicNameValuePair("userName", "Pankaj Kumar"));
			//		HttpEntity postParams = new UrlEncodedFormEntity(urlParameters);
			httpPost.setEntity(new StringEntity(data, "UTF-8"));

			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
			logger.info("URL:{}, status_code:{}", url, httpResponse.getStatusLine().getStatusCode());
			StringBuffer response = new StringBuffer();
			try (BufferedReader reader = new BufferedReader(
					new InputStreamReader(httpResponse.getEntity().getContent()));) {
				String inputLine;
				while ((inputLine = reader.readLine()) != null) {
					response.append(inputLine);
				}
			}
			return response.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public void doGetProxy() {
		//        CredentialsProvider credsProvider = new BasicCredentialsProvider();
		//        credsProvider.setCredentials(
		//                new AuthScope("localhost", 8080),
		//                new UsernamePasswordCredentials("username", "password"));
		//        CloseableHttpClient httpclient = HttpClients.custom()
		//                .setDefaultCredentialsProvider(credsProvider).build();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpHost target = new HttpHost("localhost", 443, "https");
			HttpHost proxy = new HttpHost("127.0.0.1", 8080, "http");

			RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
			HttpGet request = new HttpGet("/");
			request.setConfig(config);

			System.out.println("Executing request " + request.getRequestLine() + " to " + target + " via " + proxy);

			CloseableHttpResponse response = httpclient.execute(target, request);
			try {
				System.out.println("----------------------------------------");
				System.out.println(response.getStatusLine());
				EntityUtils.consume(response.getEntity());
			} finally {
				response.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
