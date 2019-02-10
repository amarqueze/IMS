package io.amecodelabs.ims.service;

import java.net.URISyntaxException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import io.amecodelabs.ims.broker.client.HttpConnect;
import io.amecodelabs.ims.broker.impl.client.HttpBroker;
import io.amecodelabs.ims.broker.impl.client.HttpGetRequest;
import io.amecodelabs.ims.broker.impl.client.HttpPostRequest;
import io.amecodelabs.ims.models.utils.ContentValues;
import io.amecodelabs.ims.models.utils.JSONImportException;

public class CategoryService {
	private static final String LOCATION_URI = "http://localhost/categories";
	
	protected CategoryService() {
		
	}
	
	public static void createCategory(ContentValues category, BiConsumer<ContentValues, ContentValues> success, Consumer<String> fail) {
		HttpConnect httpConnect = HttpBroker.getHttpConnect();
	
		HttpPostRequest request = null;
		try {
			request = new HttpPostRequest(LOCATION_URI + "/create");
			AbstractService.addHead(request);
			AbstractService.addBody(request, category);
			
			httpConnect
			.setErrorHandler( (err) -> fail.accept(err.getMessage()) )
			.setSuccessHandler( (res) -> {
				try {
					success.accept(ContentValues.newInstanceOfImportJSON("response", res.getBody()), category);
				} catch (JSONImportException e) {
					fail.accept(e.getMessage());
				}
			});
			
			httpConnect.execute(request);
			
		}catch (URISyntaxException e) {
			fail.accept(e.getMessage());
		} 
	}
	
	public static void getCategories(Consumer<ContentValues> success, Consumer<String> fail) {
		HttpConnect httpConnect = HttpBroker.getHttpConnect();
		
		HttpGetRequest request = null;
		try {
			request = new HttpGetRequest(LOCATION_URI + "/list");
			AbstractService.addHead(request);
			
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
	
	public static void editCategory(ContentValues updatedCategory, Consumer<ContentValues> success, Consumer<String> fail) {
		HttpConnect httpConnect = HttpBroker.getHttpConnect();
		
		HttpPostRequest request = null;
		try {
			request = new HttpPostRequest(LOCATION_URI + "/edit");
			AbstractService.addHead(request);
			request.addParams("_id", updatedCategory.getValueString("_id"));
			request.setContent(updatedCategory.exportJSON(), "application/json; charset=utf-8");
			
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
	
	public static void deleteCategory(String id, Consumer<ContentValues> success, Consumer<String> fail) {
		HttpConnect httpConnect = HttpBroker.getHttpConnect();
		
		HttpGetRequest request = null;
		try {
			request = new HttpGetRequest(LOCATION_URI + "/delete/" + id);
			AbstractService.addHead(request);
			
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
}
