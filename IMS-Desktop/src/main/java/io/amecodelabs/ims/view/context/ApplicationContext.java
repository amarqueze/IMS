package io.amecodelabs.ims.view.context;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

public class ApplicationContext {
	private final String name;
	private final String description;
	private final Image icon;
	
	private Map<String, Object> attributes;
	
	private static ApplicationContext application;
	
	public static ApplicationContext getInstance() {
		if(application == null) return application = new ApplicationContext(); 
		return application;
	}
	
	private ApplicationContext() {
		name = "IMS";
		description = "";
		icon = new Image(getClass().getResourceAsStream("/images/storage-2.png"));
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

	public Image getIcon() {
		return icon;
	}
}
