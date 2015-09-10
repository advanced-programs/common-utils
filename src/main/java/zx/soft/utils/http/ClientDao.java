package zx.soft.utils.http;

import java.util.HashMap;

/**
 * HTTP客户端接口
 *
 * @author wanggang
 *
 */
public interface ClientDao {

	/**
	 * GET请求
	 */

	public String doGet(String url);

	public String doGet(String url, String charset);

	public String doGet(String url, String cookie, String charset);

	public String doGet(String url, HashMap<String, String> headers);

	public String doGet(String url, HashMap<String, String> headers, String charset);

	public String doGet(String url, HashMap<String, String> headers, String cookie, String charset);

	/**
	 * POST请求
	 */

	public String doPost(String url, String data);

	public String doPost(String url, String data, String charset);

	/**
	 * 关闭资源
	 */
	public void close();

}
