package zx.soft.utils.http;

import java.io.IOException;

import org.restlet.Client;
import org.restlet.Context;
import org.restlet.data.MediaType;
import org.restlet.data.Protocol;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.utils.log.LogbackUtil;

/**
 * RESTlet客户端
 *
 * @author wanggang
 *
 */
public class RestletClientDaoImpl implements ClientDao {

	private static Logger logger = LoggerFactory.getLogger(RestletClientDaoImpl.class);

	private final Client client;

	public RestletClientDaoImpl() {
		client = new Client(new Context(), Protocol.HTTP);
	}

	/**
	 * GET请求，基于Client
	 */
	@Override
	public String doGet(String url, String cookie) {
		// cookie暂未处理
		ClientResource clientResource = new ClientResource(url);
		clientResource.setNext(client);
		try {
			String result = clientResource.get().getText();
			return result;
		} catch (ResourceException | IOException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}
	}

	@Override
	public String doPost(String url, String data) {
		ClientResource requestResource = new ClientResource(url);
		Representation entity = new StringRepresentation(data);
		entity.setMediaType(MediaType.APPLICATION_JSON);
		Representation response = requestResource.post(entity);
		try {
			String result = response.getText();
			return result;
		} catch (IOException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		} finally {
			response.release();
		}
	}

	/**
	 * GET请求，不基于Client
	 */
	public static String doGetWithoutClient(String url) {
		ClientResource clientResource = new ClientResource(url);
		try {
			String result = clientResource.get().getText();
			return result;
		} catch (ResourceException | IOException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}
	}

	@Override
	public void close() {
		try {
			client.stop();
		} catch (Exception e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException();
		}
	}

}
