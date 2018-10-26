package io.amecodelabs.ims.broker.client;

public interface HttpResponse {
	int getHttpStatus();
	String getHeader(String name);
	String getCookie(String name);
	String getBody();
}
