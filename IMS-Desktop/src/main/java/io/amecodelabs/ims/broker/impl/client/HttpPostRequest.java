package io.amecodelabs.ims.broker.impl.client;

import java.net.URISyntaxException;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import io.amecodelabs.ims.broker.client.HttpRequest;

public class HttpPostRequest extends AbstractHttpConnect implements HttpRequest {
	private HttpPost request;
	private URIBuilder uriBuilder;
	private StringBuffer cookies;
	
	public HttpPostRequest(String url) throws URISyntaxException {
		uriBuilder = new URIBuilder(url);
		request = new HttpPost();
		cookies = new StringBuffer();
	}
	
	public void setContent(String content, String contentType) {
		StringEntity entity = new StringEntity(content, ContentType.parse(contentType));
		request.setEntity(entity);
	}
	
	@Override
	public void setAuth(String type, String credentials) {
		request.setHeader("Authorization", type + " " + credentials);
	}

	@Override
	public void addHeader(String name, String value) {
		request.setHeader(name, value);
	}

	@Override
	public void addCookie(String name, String value) {
		cookies.append(name + "=" + value + "; ");
		request.setHeader("cookie", cookies.toString());
	}

	@Override
	public void addParams(String name, String value) {
		uriBuilder.addParameter(name, value);
	}

	@Override
	public String getMethod() {
		return "POST";
	}

	@Override
	protected HttpUriRequest getHttpUriRequest() {
		try {
			request.setURI(uriBuilder.build());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return request;
	}

}
