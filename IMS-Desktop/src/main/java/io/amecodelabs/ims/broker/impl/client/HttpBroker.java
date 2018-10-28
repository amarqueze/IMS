package io.amecodelabs.ims.broker.impl.client;

import io.amecodelabs.ims.broker.client.HttpConnect;

public class HttpBroker {
	private static HttpConnect connect;
	
	public static HttpConnect getHttpConnect() {
		if(connect == null) connect = new DefaultHttpConnect();
		return connect;
	}
	
}
