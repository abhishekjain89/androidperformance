package com.num.utils;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import android.os.Environment;

public class SDCardFileReader {
	
	public static String getData(String fileName){
		File sdcard = Environment.getExternalStorageDirectory();

		//Get the text file
		File file = new File(sdcard,fileName);

		//Read text from file
		StringBuilder text = new StringBuilder();

		try {
		    BufferedReader br = new BufferedReader(new FileReader(file));
		    String line;

		    while ((line = br.readLine()) != null) {
		        text.append(line);
		        text.append('\n');
		    }
		}
		catch (IOException e) {
		    //You'll need to add proper error handling here
		}
		return text.toString();
	}
	
	public static void saveData(String fileName,String data){
		File sdcard = Environment.getExternalStorageDirectory();

		//Get the text file
		File file = new File(sdcard,fileName);
		
		try {
			BufferedWriter writer  = new BufferedWriter(new FileWriter(file));
		    writer.write(data);
			writer.close();
		}
		catch (IOException e) {
		    e.printStackTrace();
		}		
		
	}

}
