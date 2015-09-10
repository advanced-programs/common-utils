package zx.soft.utils.http;

import java.io.IOException;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * 基于commons-httpclient-3.1来实现Http工具类
 *
 * @author wanggang
 *
 */
public class HttpClient3Impl {

	public static void main(String[] args) {
		//
	}

	public void doGetProxy(String ip, int port, String username, String password, String url) {
		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(url);

		HostConfiguration config = client.getHostConfiguration();
		config.setProxy(ip, port);

		Credentials credentials = new UsernamePasswordCredentials(username, password);
		AuthScope authScope = new AuthScope(ip, port);

		client.getState().setProxyCredentials(authScope, credentials);
		try {
			client.executeMethod(method);

			if (method.getStatusCode() == HttpStatus.SC_OK) {
				String response = method.getResponseBodyAsString();
				System.out.println("Response = " + response);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
	}

}
