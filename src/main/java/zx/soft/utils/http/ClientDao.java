package zx.soft.utils.http;

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
	public String doGet(String url, String cookie);

	/**
	 * POST请求
	 */
	public String doPost(String url, String data);

	/**
	 * 关闭资源
	 */
	public void close();

}
