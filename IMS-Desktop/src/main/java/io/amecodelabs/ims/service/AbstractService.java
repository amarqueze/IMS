package io.amecodelabs.ims.service;

import io.amecodelabs.ims.broker.client.HttpRequest;
import io.amecodelabs.ims.broker.impl.client.HttpPostRequest;
import io.amecodelabs.ims.models.utils.JSONExportable;
import io.amecodelabs.ims.view.context.Session;

public interface AbstractService {
	final String USER_AGENT = "IMS/2.0 (compatible x64; Linux; Macintosh; Window; JavaFx2.x, JDK 10) amecodelabs.io";
	final String ACCEPT = "application/json";
	final String ACCEPT_CHARSET = "utf-8, iso-8859-1;q=0.5";
	final String ACCEPT_LANGUAGE = "*";
	
	public static void addHead(HttpRequest request) {
		request.addHeader("User-Agent", USER_AGENT);
		request.addHeader("Accept", ACCEPT);
		request.addHeader("Accept-Charset", ACCEPT_CHARSET);
		request.addHeader("Accept-Language", ACCEPT_LANGUAGE);
		request.setAuth("Bearer", Session.getSession().getToken());
	}
	
	public static void addBody(HttpPostRequest request, JSONExportable content) {
		request.setContent("[" + content.exportJSON() + "]", "application/json; charset=utf-8");
	}
}
