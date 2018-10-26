package io.amecodelabs.ims.broker.client;

import io.amecodelabs.ims.broker.client.Exception.HttpConnectErrorException;

public interface HttpError {
	void onError(HttpConnectErrorException httpConnectErrorException);
}
