package com.blt.helloworld.model;

public enum EventType {
	BUTTON_PRESSED("Button Pressed"),
	APP_STARTUP("Application Started");
	
	private String display;
	
	EventType(String displayVal) {
		display = displayVal;
	}
	
	public String toString() {
		return display;
	}
	
	public static EventType find(String displayVal) {
		
		if (displayVal != null) {
			for (EventType e : values()) {
				if (e.toString().equalsIgnoreCase(displayVal)) {
					return e;
				}
			}
		}
		return null;
	}

}
