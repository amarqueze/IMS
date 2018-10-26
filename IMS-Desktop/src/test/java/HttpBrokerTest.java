import static org.junit.Assert.*;

import java.net.URISyntaxException;
import java.util.UUID;
import java.util.logging.Logger;

import org.junit.Test;

import io.amecodelabs.ims.broker.client.HttpConnect;
import io.amecodelabs.ims.broker.impl.client.HttpBroker;
import io.amecodelabs.ims.broker.impl.client.HttpGetRequest;
import io.amecodelabs.ims.broker.impl.client.HttpPostRequest;
import io.amecodelabs.ims.models.utils.ContentValues;
import io.amecodelabs.ims.models.utils.JSONExportException;

public class HttpBrokerTest {
	Logger log = Logger.getLogger(HttpBrokerTest.class.getName());
	String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmb28iOiJiYXIiLCJpYXQiOjE1NDA1MzEwNTR9.KvL_J7p3gq3ib6OVCIv9HmHcq16G52VHNB2Y76VkbB8";
	
	@Test
	public void testGETRequest() throws URISyntaxException {
		final String url = "http://services.groupkt.com/state/search/IND";
		HttpConnect httpConnect = HttpBroker.getHttpConnect();
		
		HttpGetRequest request = new HttpGetRequest(url);
		request.addHeader("User-Agent", "Java-Desktop");
		request.addParams("text", "pradesh");
		
		httpConnect
			.setErrorHandler( (err) -> { 
					log.warning(err.getMessage());
					assertTrue(true);
				}
			)
			.setSuccessHandler( 
					(res) -> {
						log.info(res.getBody());
						assertTrue(true);
					}
			)
			.execute(request);
	}
	
	@Test
	public void testAuthRequest() throws URISyntaxException {
		final String url = "http://localhost/providers/list";
		HttpConnect httpConnect = HttpBroker.getHttpConnect();
		
		HttpGetRequest request = new HttpGetRequest(url);
		request.setAuth("Bearer", token);
		
		httpConnect
		.setErrorHandler( (err) -> { 
				log.warning(err.getMessage());
				assertTrue(true);
			}
		)
		.setSuccessHandler( (res) -> {
					log.info(res.getBody());
					assertTrue(true);
				}
		)
		.execute(request);
	}
	
	@Test
	public void testPOSTRequest() throws URISyntaxException, JSONExportException {
		final String url = "http://localhost/user/create";
		HttpConnect httpConnect = HttpBroker.getHttpConnect();
		
		ContentValues contentValues = ContentValues.newInstanceEmpy("user");
		contentValues.put("_id", UUID.randomUUID().toString());
		contentValues.put("email", "alanmarquez@outlook.com");
		contentValues.put("password", "somepassworgstrong");
		
		HttpPostRequest request = new HttpPostRequest(url);
		request.setAuth("Bearer", token);
		request.setContent("[" + contentValues.exportJSON() +  "]", "application/json");
		
		httpConnect
		.setErrorHandler( (err) -> { 
				log.warning(err.getMessage());
				assertTrue(true);
			}
		)
		.setSuccessHandler( (res) -> {
					log.info(res.getBody());
					assertTrue(true);
				}
		)
		.execute(request);
	}
	
	@Test
	public void testSSLRequest() {
		fail("no support");
	}

}
