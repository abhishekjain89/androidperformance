package com.android.utils;

import java.util.Scanner;

import com.android.models.Measurement;

public class ParseUtil {
	
	public static Measurement PingParser(String s){
		String lastLine=null;
		Scanner scanLines=new Scanner(s);
		Scanner scanLastLine;
		Scanner scanValues;
		double max;
		double min;
		double avr;
		double stddev;
		
		while (scanLines.hasNextLine())
			lastLine=scanLines.nextLine();
		
		scanLastLine=new Scanner(lastLine);
		scanLastLine.findInLine("=");
		
		if (scanLastLine.hasNext()) {
			scanValues=new Scanner(scanLastLine.next());
			scanValues.useDelimiter("/");
			try {
				min=scanValues.nextDouble();
				avr=scanValues.nextDouble();
				max=scanValues.nextDouble();
				stddev=scanValues.nextDouble();
				return (new Measurement(max,min,avr,stddev));
			}catch(Exception e){return null;}
		}
		else
			return null;
	}

}
