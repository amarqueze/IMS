package io.amecodelabs.ims.view.productstage;

import io.amecodelabs.ims.models.utils.ContentValues;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class DataModelProduct {
	private final String id;
	private final SimpleStringProperty description;
	private final SimpleIntegerProperty availableStock;
	private final SimpleStringProperty unit;
	private final SimpleStringProperty providerMain;
	private final SimpleStringProperty category;
	private final SimpleIntegerProperty minimumStock;
	private final SimpleIntegerProperty maximumStock;
	
	public DataModelProduct(String id, String description, int availableStock, String unit, 
			String providerMain, String category, int minimumStock, int maximumStock) {
		this.id = id;
		this.description = new SimpleStringProperty(description);
		this.availableStock = new SimpleIntegerProperty(availableStock);
		this.unit = new SimpleStringProperty(unit);
		this.providerMain = new SimpleStringProperty(providerMain);
		this.category = new SimpleStringProperty(category);
		this.minimumStock = new SimpleIntegerProperty(minimumStock);
		this.maximumStock = new SimpleIntegerProperty(maximumStock);
	}
	
	public DataModelProduct(ContentValues product) {
		this.id = product.getValueString("_id");
		this.description = new SimpleStringProperty(product.getValueString("description"));
		this.availableStock = new SimpleIntegerProperty(product.getValueInteger("available_stock"));
		this.unit = new SimpleStringProperty(product.getValueString("unit"));
		this.providerMain = new SimpleStringProperty(product.getValueString("provider_main"));
		this.category = new SimpleStringProperty(product.getArrayContentValues("categorydetail")[0].getValueString("name"));
		this.minimumStock = new SimpleIntegerProperty(product.getValueInteger("minimum_stock"));
		this.maximumStock = new SimpleIntegerProperty(product.getValueInteger("maximum_stock"));
	}

	public String getId() {
		return id;
	}

	public String getDescription() {
		return description.getValue();
	}
	
	public void setDescription(String newValue) {
		description.set(newValue);
	}

	public Integer getAvailableStock() {
		return availableStock.getValue();
	}
	
	public void setAvailableStock(int newValue) {
		availableStock.set(newValue);
	}

	public String getUnit() {
		return unit.getValue();
	}
	
	public void setUnit(String newValue) {
		unit.set(newValue);
	}

	public String getProviderMain() {
		return providerMain.getValue();
	}
	
	public void setProviderMain(String newValue) {
		providerMain.set(newValue);
	}

	public String getCategory() {
		return category.getValue();
	}
	
	public void setCategory(String newValue) {
		category.set(newValue);
	}

	public Integer getMinimumStock() {
		return minimumStock.getValue();
	}
	
	public void setMinimumStock(int newValue) {
		minimumStock.set(newValue);
	}

	public Integer getMaximumStock() {
		return maximumStock.getValue();
	}
	
	public void setMaximumStock(int newvalue) {
		maximumStock.set(newvalue);
	}
	
}
