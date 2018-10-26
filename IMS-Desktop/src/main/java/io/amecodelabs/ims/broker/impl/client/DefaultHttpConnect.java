package io.amecodelabs.ims.broker.impl.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;

import io.amecodelabs.ims.broker.client.HttpConnect;
import io.amecodelabs.ims.broker.client.HttpError;
import io.amecodelabs.ims.broker.client.HttpRequest;
import io.amecodelabs.ims.broker.client.HttpResponse;
import io.amecodelabs.ims.broker.client.HttpSuccess;
import io.amecodelabs.ims.broker.client.Exception.HttpConnectErrorException;

public class DefaultHttpConnect implements HttpConnect {
	private HttpError httpError = (err) -> {};
	private HttpSuccess httpSuccess = (res) -> {};
	private HttpClient client;
	
	public DefaultHttpConnect() {
		
	}

	@Override
	public HttpConnect setErrorHandler(HttpError httpError) {
		this.httpError = httpError;
		return this;
	}

	@Override
	public HttpConnect setSuccessHandler(HttpSuccess httpSuccess) {
		this.httpSuccess = httpSuccess;
		return this;
	}

	@Override
	public void execute(HttpRequest httpRequest) {
		HttpUriRequest httpUriRequest;
		if( client == null ) client = HttpProvider.getClientHttp();
		
		httpUriRequest = ((AbstractHttpConnect) httpRequest).getHttpUriRequest();
		Runnable backgroundTask = () -> {
			try {
				org.apache.http.HttpResponse response = client.execute(httpUriRequest);
				
				Header[] cookies = response.getHeaders("Set-Cookie");
				int nroCookies = cookies.length;
				this.httpSuccess.onSuccess(new HttpResponse() {
					@Override
					public int getHttpStatus() {
						return response.getStatusLine().getStatusCode();
					}

					@Override
					public String getHeader(String name) {
						return response.getFirstHeader(name).getValue();
					}

					@Override
					public String getCookie(String name) {
						for(int i=0; i<nroCookies; i++) {
							if(cookies[i].getName().equals(name)) {
								return cookies[i].getValue();
							}
						}
						return null;
					}

					@Override
					public String getBody() {
						StringBuffer result = new StringBuffer("");
						try {
							BufferedReader rd = new BufferedReader(
							        new InputStreamReader(response.getEntity().getContent()));
							
							result = new StringBuffer();
							String line = "";
							while ((line = rd.readLine()) != null) {
								result.append(line);
							}
						} catch (UnsupportedOperationException | IOException e) {
							e.printStackTrace();
						}
						
						return result.toString();
					}
				});
			} catch (ClientProtocolException e) {
				this.httpError.onError(new HttpConnectErrorException("Error in the HTTP protocol"));
			} catch (IOException e) {
				this.httpError.onError(new HttpConnectErrorException("Connect failed or interrupted", e.getCause()));
			} finally {
				try {
					((CloseableHttpClient) client).close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		new Thread(backgroundTask).start();
	}

}
