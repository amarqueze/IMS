package io.amecodelabs.ims.view.providerstage;

import io.amecodelabs.ims.models.utils.ContentValues;
import javafx.beans.property.SimpleStringProperty;

public class DataModelProvider {
	private final String id;
	private final SimpleStringProperty provider;
	private final SimpleStringProperty phone;
	private final SimpleStringProperty email;
	private final SimpleStringProperty address;
	private final SimpleStringProperty city;
	private final SimpleStringProperty comments;

	public DataModelProvider(String id, String provider, String phone, String email, String address, String city,
			String comments) {
		this.id = id;
		this.provider = new SimpleStringProperty(provider);
		this.phone = new SimpleStringProperty(phone);
		this.email = new SimpleStringProperty(email);
		this.address = new SimpleStringProperty(address);
		this.city = new SimpleStringProperty(city);
		this.comments = new SimpleStringProperty(comments);
	}
	
	public DataModelProvider(ContentValues contentValues) {
		this.id = contentValues.getValueString("_id");
		this.provider = new SimpleStringProperty(contentValues.getValueString("name"));
		this.phone = new SimpleStringProperty(contentValues.getValueString("phone"));
		this.email = new SimpleStringProperty(contentValues.getValueString("email"));
		this.address = new SimpleStringProperty(contentValues.getValueString("address"));
		this.city = new SimpleStringProperty(contentValues.getValueString("city"));
		this.comments = new SimpleStringProperty(contentValues.getValueString("observations"));
	}
	
	
	public String getId() {
		return id;
	}
	
	public void setProvider(String newValue) {
		provider.set(newValue);
	}
	
	public String getProvider() {
		return provider.get();
	}
	
	public void setPhone(String newValue) {
		phone.set(newValue);
	}

	public String getPhone() {
		return phone.get();
	}
	
	public void setEmail(String newValue) {
		email.set(newValue);
	}

	public String getEmail() {
		return email.get();
	}
	
	public void setAddress(String newValue) {
		address.set(newValue);
	}

	public String getAddress() {
		return address.get();
	}
	
	public void setCity(String newValue) {
		city.set(newValue);
	}

	public String getCity() {
		return city.get();
	}
	
	public void setComments(String newValue) {
		comments.set(newValue);
	}

	public String getComments() {
		return comments.get();
	}
    
}
