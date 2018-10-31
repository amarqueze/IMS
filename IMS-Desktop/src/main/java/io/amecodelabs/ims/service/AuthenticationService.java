package io.amecodelabs.ims.service;

import java.net.URISyntaxException;
import java.util.function.Consumer;

import io.amecodelabs.ims.broker.client.HttpConnect;
import io.amecodelabs.ims.broker.impl.client.HttpBroker;
import io.amecodelabs.ims.broker.impl.client.HttpPostRequest;
import io.amecodelabs.ims.models.utils.JSONExportable;

public class AuthenticationService implements Service {
	private final String LOCATION_URI = "http://localhost/auth";
	private Consumer<String> success;
	private Consumer<String> fail;
	
	public AuthenticationService(Consumer<String> success, Consumer<String> fail) {
		this.success = success;
		this.fail = fail;
	}
	
	public void authenticate(JSONExportable user) {
		HttpConnect httpConnect = HttpBroker.getHttpConnect();
		httpConnect
			.setErrorHandler( (err) -> fail.accept(err.getMessage()) )
			.setSuccessHandler( (res) -> success.accept(res.getBody()) );
		
		HttpPostRequest request = null;
		try {
			
			request = new HttpPostRequest(LOCATION_URI);
			request.addHeader("User-Agent", USER_AGENT);
			request.addHeader("Accept", ACCEPT);
			request.addHeader("Accept-Charset", ACCEPT_CHARSET);
			request.addHeader("Accept-Language", ACCEPT_LANGUAGE);
			
			request.setContent(user.exportJSON(), "application/json");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		httpConnect.execute(request);
	}
	
}
