package io.amecodelabs.ims.view.statstage;

public enum Month {
	JANUARY(0, "Jan"),
	FEBRUARY(1, "Feb"),
	MARCH(2, "Mar"),
	APRIL(3, "Apr"),
	MAY(4, "May"),
	JUNE(5, "June"),
	JULY(6, "July"),
	AUGUST(7, "Aug"),
	SEPTEMBER(8, "Sept"),
	OCTOBER(9, "Oct"),
	NOVEMBER(10, "Nov"),
	DECEMBER(11, "Dec");
	
	private final int index;
	private final String acronym;
	
	private Month(int index, String acronym) {
		this.index = index;
		this.acronym = acronym;
	}
	
	public String getAcronym() {
		return acronym;
	}
	
	public int getIndex() {
		return index;
	}
	
}
