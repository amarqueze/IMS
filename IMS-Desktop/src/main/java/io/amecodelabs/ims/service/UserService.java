package io.amecodelabs.ims.service;

import java.net.URISyntaxException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import io.amecodelabs.ims.broker.client.HttpConnect;
import io.amecodelabs.ims.broker.client.HttpRequest;
import io.amecodelabs.ims.broker.impl.client.HttpBroker;
import io.amecodelabs.ims.broker.impl.client.HttpGetRequest;
import io.amecodelabs.ims.broker.impl.client.HttpPostRequest;
import io.amecodelabs.ims.models.utils.ContentValues;
import io.amecodelabs.ims.models.utils.JSONExportable;
import io.amecodelabs.ims.models.utils.JSONImportException;
import io.amecodelabs.ims.view.context.Session;

public class UserService implements Service {
	private final String LOCATION_URI = "http://localhost/users";
	
	public UserService() {
		
	}
	
	public void createUser(ContentValues provider, BiConsumer<ContentValues, ContentValues> success, Consumer<String> fail) {
		HttpConnect httpConnect = HttpBroker.getHttpConnect();
	
		HttpPostRequest request = null;
		try {
			request = new HttpPostRequest(LOCATION_URI + "/create");
			addHead(request);
			addBody(request, provider);
			
			httpConnect
			.setErrorHandler( (err) -> fail.accept(err.getMessage()) )
			.setSuccessHandler( (res) -> {
				try {
					success.accept(ContentValues.newInstanceOfImportJSON("response", res.getBody()), provider);
				} catch (JSONImportException e) {
					fail.accept(e.getMessage());
				}
			});
			
			httpConnect.execute(request);
			
		}catch (URISyntaxException e) {
			fail.accept(e.getMessage());
		} 
	}
	
	public void getUsers(Consumer<ContentValues> success, Consumer<String> fail) {
		HttpConnect httpConnect = HttpBroker.getHttpConnect();
		
		HttpGetRequest request = null;
		try {
			request = new HttpGetRequest(LOCATION_URI + "/list");
			addHead(request);
			
			httpConnect
				.setErrorHandler( (err) -> fail.accept(err.getMessage()) )
				.setSuccessHandler( (res) -> {
					try {
						success.accept(ContentValues.newInstanceOfImportJSON("response", res.getBody()));
					} catch (JSONImportException e) {
						fail.accept(e.getMessage());
					}
				});
			
			httpConnect.execute(request);
			
		} catch (URISyntaxException e) {
			fail.accept(e.getMessage());
		} 
	}
	
	public void editUsers(ContentValues updatedprovider, Consumer<ContentValues> success, Consumer<String> fail) {
		HttpConnect httpConnect = HttpBroker.getHttpConnect();
		
		HttpPostRequest request = null;
		try {
			request = new HttpPostRequest(LOCATION_URI + "/edit");
			addHead(request);
			request.addParams("_id", updatedprovider.getValueString("_id"));
			request.setContent(updatedprovider.exportJSON(), "application/json");
			
			httpConnect
			.setErrorHandler( (err) -> fail.accept(err.getMessage()) )
			.setSuccessHandler( (res) -> {
				try {
					success.accept(ContentValues.newInstanceOfImportJSON("response", res.getBody()));
				} catch (JSONImportException e) {
					fail.accept(e.getMessage());
				}
			});
		
			httpConnect.execute(request);
			
		} catch (URISyntaxException e) {
			fail.accept(e.getMessage());
		} 
	}
	
	public void deleteUsers(String id, Consumer<ContentValues> success, Consumer<String> fail) {
		HttpConnect httpConnect = HttpBroker.getHttpConnect();
		
		HttpGetRequest request = null;
		try {
			request = new HttpGetRequest(LOCATION_URI + "/delete/" + id);
			addHead(request);
			
			httpConnect
				.setErrorHandler( (err) -> fail.accept(err.getMessage()) )
				.setSuccessHandler( (res) -> {
					try {
						success.accept(ContentValues.newInstanceOfImportJSON("response", res.getBody()));
					} catch (JSONImportException e) {
						fail.accept(e.getMessage());
					}
				});
		
			httpConnect.execute(request);
		} catch (URISyntaxException e) {
			fail.accept(e.getMessage());
		} 
	}
	
	protected void addHead(HttpRequest request) {
		request.addHeader("User-Agent", USER_AGENT);
		request.addHeader("Accept", ACCEPT);
		request.addHeader("Accept-Charset", ACCEPT_CHARSET);
		request.addHeader("Accept-Language", ACCEPT_LANGUAGE);
		request.setAuth("Bearer", Session.getSession().getToken());
	}
	
	protected void addBody(HttpPostRequest request, JSONExportable content) {
		request.setContent("[" + content.exportJSON() + "]", "application/json");
	}
}
