package io.amecodelabs.ims.view.transactionstage;

import io.amecodelabs.ims.models.utils.ContentValues;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class DataModelTransaction {
	private final String id;
	private final SimpleStringProperty description;
	private final SimpleIntegerProperty quantity;
	private final SimpleStringProperty date;
	
	public DataModelTransaction(String id, String description, int quantity, String date) {
		this.id = id;
		this.description = new SimpleStringProperty(description);
		this.quantity = new SimpleIntegerProperty(quantity);
		this.date = new SimpleStringProperty(date);
	}
	
	public DataModelTransaction(ContentValues stock) {
		this.id = stock.getValueString("_id");
		this.description = new SimpleStringProperty(stock.getValueString("product"));
		this.quantity = new SimpleIntegerProperty(stock.getValueInteger("quantity"));
		this.date = new SimpleStringProperty(stock.getValueString("date"));
	}

	public String getId() {
		return id;
	}

	public String getDescription() {
		return description.getValue();
	}

	public int getQuantity() {
		return quantity.getValue();
	}

	public String getDate() {
		return date.getValue();
	}
}
