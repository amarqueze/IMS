package io.amecodelabs.ims.view.statstage;

public class DataModelStat {
	private int valueInput;
	private int valueOutput;
	private Month month;
	
	public DataModelStat(Month month) {
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
	
	public Month getMonth() {
		return month;
	}
}
