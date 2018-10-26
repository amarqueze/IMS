package io.amecodelabs.ims.broker.client;

public interface HttpRequest {
	void setAuth(String type, String credentials);
	void addHeader(String name, String value);
	void addCookie(String name, String value);
	void addParams(String name, String value);
	String getMethod();
}
