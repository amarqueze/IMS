package io.amecodelabs.ims.view.userstage;

import io.amecodelabs.ims.models.utils.ContentValues;
import javafx.beans.property.SimpleStringProperty;

public class DataModelUsers {
	private final String id;
	private final SimpleStringProperty firstName;
	private final SimpleStringProperty lastName;
	private final SimpleStringProperty email;
	private final SimpleStringProperty dni;
	private final SimpleStringProperty address;
	private final SimpleStringProperty phone;
	
	public DataModelUsers(String id, String firstName, String lastName, String email, 
			String dni, String address, String phone) {
		this.id = id;
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.email = new SimpleStringProperty(email);
		this.dni = new SimpleStringProperty(dni);
		this.address = new SimpleStringProperty(address);
		this.phone = new SimpleStringProperty(phone);
	}

	public DataModelUsers(ContentValues user) {
		this.id = user.getValueString("_id");
		this.firstName = new SimpleStringProperty(user.getValueString("firstname"));
		this.lastName = new SimpleStringProperty(user.getValueString("lastname"));
		this.email = new SimpleStringProperty(user.getValueString("email"));
		this.dni = new SimpleStringProperty(user.getValueString("dni"));
		this.address = new SimpleStringProperty(user.getValueString("address"));
		this.phone = new SimpleStringProperty(user.getValueString("phone"));
	}

	public String getId() {
		return id;
	}
	
	public void setFirstName(String newValue) {
		firstName.set(newValue);
	}

	public String getFirstName() {
		return firstName.getValue();
	}
	
	public void setLastName(String newValue) {
		lastName.set(newValue);
	}

	public String getLastName() {
		return lastName.getValue();
	}
	
	public void setEmail(String newValue) {
		email.set(newValue);
	}
	
	public String getEmail() {
		return email.getValue();
	}
	
	public void setDni(String newValue) {
		dni.setValue(newValue);
	}

	public String getDni() {
		return dni.getValue();
	}
	
	public void setAddress(String newValue) {
		address.set(newValue);
	}

	public String getAddress() {
		return address.getValue();
	}
	
	public void setPhone(String newValue) {
		phone.set(newValue);
	}

	public String getPhone() {
		return phone.getValue();
	}	
}
