package io.amecodelabs.ims.broker.impl.client;

import java.net.URISyntaxException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;

import io.amecodelabs.ims.broker.client.HttpRequest;

public class HttpGetRequest extends AbstractHttpConnect implements HttpRequest {
	protected HttpGet request;
	private URIBuilder uriBuilder;
	private StringBuffer cookies;
	
	public HttpGetRequest(String url) throws URISyntaxException {
		uriBuilder = new URIBuilder(url);
		request = new HttpGet();
		cookies = new StringBuffer();
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
		return "GET";
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
