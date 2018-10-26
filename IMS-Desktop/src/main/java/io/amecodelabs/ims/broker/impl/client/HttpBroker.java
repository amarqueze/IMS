package io.amecodelabs.ims.broker.impl.client;

import io.amecodelabs.ims.broker.client.HttpConnect;

public class HttpBroker {
	
	public static HttpConnect getHttpConnect() {
		return new DefaultHttpConnect();
	}
	
}
