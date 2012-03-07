package com.num.tasks;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.num.listeners.FakeListener;
import com.num.listeners.ResponseListener;

/*
 * Measurement Task 
 * set tasks to run and give ip address to ping and more
 * 
 * Call another task to backend
 * 
 * 
 */
public class InstallBinariesTask extends ServerTask{
	
	String[] binaries;
	public InstallBinariesTask(Context context, Map<String, String> reqParams, String[] binaries,	ResponseListener listener) {
		super(context, reqParams, listener);
		this.binaries = binaries;
	}


	@Override
	public void runTask() {
		
		try {
    		for(int i = 0; i < binaries.length; i++){
    			String path = "/data/data/com.android/" + binaries[i];
    			File file = new File(path);
    			if(!file.exists()){
					InputStream is = this.getContext().getAssets().open(binaries[i]);
					FileOutputStream fos = new FileOutputStream(path);
					byte[] buf = new byte[1024];
					int size = 0;
					while((size = is.read(buf)) > -1)
					{
						fos.write(buf, 0, size);
					}
					is.close();
					fos.close();
    			}
    		}
    	} catch (IOException e) {
			e.printStackTrace();
		} 
		
	
	}

	@Override
	public String toString() {
		return "Ping Task";
	}
	

}
