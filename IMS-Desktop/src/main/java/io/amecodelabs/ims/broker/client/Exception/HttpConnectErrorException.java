package io.amecodelabs.ims.broker.client.Exception;

public class HttpConnectErrorException extends Exception {
	private static final long serialVersionUID = -7830731753099808392L;
	
	public HttpConnectErrorException(String msg) {
		super(msg);
	}
	
	public HttpConnectErrorException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
