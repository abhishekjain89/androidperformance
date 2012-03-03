package com.ping.models;

import java.util.Stack;

public class Buffer {
	
	public Stack bufferMeasurement;
	
	public Buffer(){
		bufferMeasurement = new Stack<Measurement>();
	}
	

	public void addMeasurment(Measurement m){
		bufferMeasurement.add(m);
	}
	
	public Measurement getLastMeasurement(){
		
		return (Measurement) bufferMeasurement.peek();
		
	}
	
	public boolean isEmpty(){
		if(bufferMeasurement.size()==0) return true;
		return false;
	}
	
	public Measurement removeLastMeasurement(){
		return (Measurement) bufferMeasurement.pop();
	}


}
