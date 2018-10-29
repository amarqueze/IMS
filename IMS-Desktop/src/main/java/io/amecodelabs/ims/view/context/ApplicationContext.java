package io.amecodelabs.ims.view.context;

import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {
	private final String name;
	private final String description;
	private final String icon;
	
	private Map<String, Object> attributes;
	
	private static ApplicationContext application;
	
	public static ApplicationContext getInstance() {
		if(application == null) return application = new ApplicationContext(); 
		return application;
	}
	
	private ApplicationContext() {
		name = "IMS";
		description = "";
		icon = "/images/storage-2.png";
		attributes = new HashMap<String, Object>();
	}
	
	public void addAttribute(String key, Object value) {
		attributes.put(key, value);
	}
	
	public Object getAttribute(String key) {
		return attributes.get(key);
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getIcon() {
		return icon;
	}
}
