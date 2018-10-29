package io.amecodelabs.ims.models.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ContentValues implements JSONExportable {
	private String nameObject;
	private Map<String, Object> properties;
	
	private String cacheKey;
	private Object cacheValue;
	
	public static ContentValues newInstanceEmpy(String name) {
		return new ContentValues(name, new LinkedHashMap<String,Object>());
	}
	
	public static ContentValues newInstanceOfImportJSON(String name, String json) throws JSONImportException {
		Map<String, Object> properties;
		try {
			properties = new ObjectMapper().readValue(json, Map.class);
		} catch (IOException  e) {
			throw new JSONImportException(e.getMessage());
		} 
		return new ContentValues(name, properties);
	}
	
	public static ContentValues[] newInstanceOfImportArrayJSON(String name, String json) throws JSONImportException {
		List<Object> arrayProperties;
		ContentValues[] properties = null;
		try {
			arrayProperties = new ObjectMapper().readValue(json, List.class);
			
			int sizeArray = arrayProperties.size();
			properties = new ContentValues[sizeArray];
			for(int i = 0; i < sizeArray; i++)
				properties[i] = new ContentValues(name, (Map<String, Object>) arrayProperties.get(i));
		} catch (IOException  e) {
			throw new JSONImportException(e.getMessage());
		} 
		
		return properties;
	}
	
	public static ContentValues newCopy(String name, ContentValues contentValues) {
		return new ContentValues(name, new LinkedHashMap<String,Object>(contentValues.properties));
	}
	
	protected ContentValues(String name, Map<String, Object> properties) {
		this.nameObject = name;
		this.properties = properties;
	}
	
	public void put(String key, String value) {
		properties.put(key, value);
	}
	
	public void put(String key, String[] value) {
		properties.put(key, value);
	}
	
	public void put(String key, int value) {
		properties.put(key, value);
	}
	
	public void put(String key, int[] value) {
		properties.put(key, value);
	}
	
	public void put(String key, double value) {
		properties.put(key, value);
	}
	
	public void put(String key, double[] value) {
		properties.put(key, value);
	}
	
	public void put(String key, boolean value) {
		properties.put(key, value);
	}
	
	public void put(String key, boolean[] value) {
		properties.put(key, value);
	}
	
	public void put(ContentValues contentValues) {
		if(!contentValues.properties.isEmpty()) 
			properties.put(contentValues.nameObject, contentValues.properties);
	}
	
	public void put(String key, ContentValues... arrayContentValues) {
		int arraySize = arrayContentValues.length;
		ArrayList<Object> arrayProperties = new ArrayList<>();
		for (int i = 0; i < arraySize; i++) 
			arrayProperties.add(arrayContentValues[i].properties);
		
		this.properties.put(key, arrayProperties);
	}
	
	public boolean containsKey(String key) {
		Object value = properties.get(key);
		if(value != null) {
			cacheKey = key;
			cacheValue = value;
			return true;
		}
		return false;
	}
	
	public Object getValue(String key) {
		return get(key);
	}
	
	public String getValueString(String key) {
		return (String) get(key);
	}
	
	public int getValueInteger(String key) {
		return (Integer) get(key);
	}
	
	public double getValueDouble(String key) {
		return (Double) get(key);
	}
	
	public ContentValues getContentValues(String key) {
		return new ContentValues(key, (Map<String, Object>) get(key));
	}
	
	public ContentValues[] getArrayContentValues(String key) {
		List<Object> arrayproperties = (List<Object>) get(key);
		int arraySize = arrayproperties.size();
		
		ContentValues[] arrayContentValues = new ContentValues[arraySize];

		for (int i = 0; i < arraySize; i++) 
			arrayContentValues[i] = new ContentValues(key, (Map<String, Object>) arrayproperties.get(i));
		
		return arrayContentValues;
	}
	
	private Object get(String key) {
		if(key == cacheKey) return cacheValue;
		return properties.get(key);
	}
	
	public String getNameObject() {
		return nameObject;
	}
	
	@Override
	public String exportJSON() throws JSONExportException {
		try {
			return new ObjectMapper().writeValueAsString(properties);
		} catch (JsonProcessingException e) {
			throw new JSONExportException(e.getMessage());
		}
	}
	
}
