package io.amecodelabs.ims.broker.client;

public interface HttpConnect {
	HttpConnect setErrorHandler(HttpError httpError);
	HttpConnect setSuccessHandler(HttpSuccess httpSuccess);
	void execute(HttpRequest httpRequest);
}
