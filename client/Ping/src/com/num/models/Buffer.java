package com.num.models;

import java.util.ArrayList;
import java.util.Stack;

public class Buffer {
	
	public Stack bufferMeasurement;
	
	public Buffer(){
		bufferMeasurement = new Stack<String>();
	}
	

	public void addMeasurment(Measurement m){
		m.setScreens(new ArrayList<Screen>());
		bufferMeasurement.add(m.toJSON().toString());
	}
	
	public String getLastMeasurement(){
		
		return (String) bufferMeasurement.peek();
		
	}
	
	public boolean isEmpty(){
		if(bufferMeasurement.size()==0) return true;
		return false;
	}
	
	public String removeLastMeasurement(){
		return (String) bufferMeasurement.pop();
	}


}
