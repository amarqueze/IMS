package io.amecodelabs.ims.broker.impl.client;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;

public class HttpProvider {
	
	public static HttpClient getClientHttp() {
		HttpClient client = null;
		final SSLContextBuilder builder = new SSLContextBuilder();
		try {
			builder.loadTrustMaterial((TrustStrategy) (X509Certificate[] chain, String authType) -> true);
			final SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					builder.build());
			
			client = HttpClients
					.custom()
					.setSSLSocketFactory(sslsf)
					.build();
			
		} catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
			e.printStackTrace();
		}
		
		return client;
	} 
	
}
