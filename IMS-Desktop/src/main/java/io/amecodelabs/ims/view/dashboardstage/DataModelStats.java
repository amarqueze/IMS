package io.amecodelabs.ims.view.dashboardstage;

public class DataModelStats {
	private int valueInput;
	private int valueOutput;
	private String month;
	
	public DataModelStats(String month) {
		this.month = month;
		valueInput = 0;
		valueOutput = 0;
	}
	
	public void plus(int value) {
		valueInput += value;
	}
	
	public void minus(int value) {
		valueOutput += value;
	}
	
	public void clear() {
		valueInput = 0;
		valueOutput = 0;
	}
	
	public int getValueInput() {
		return valueInput;
	}
	
	public int getValueOutput() {
		return valueOutput;
	}
	
	public String getMonth() {
		return month;
	}
}
