package io.amecodelabs.ims.broker.impl.client;

import org.apache.http.client.methods.HttpUriRequest;

import io.amecodelabs.ims.broker.client.HttpRequest;

public abstract class AbstractHttpConnect implements HttpRequest {
	
	
	
	
	
	protected abstract HttpUriRequest getHttpUriRequest();

}
