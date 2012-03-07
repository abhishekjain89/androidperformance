package com.num.utils;



import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


public class HTTPUtil {

	public static final String TAG = "APIUtil";
	public static final String URL =  "http://ruggles.gtnoise.net/";
	
	public static final String SHARED_SECRET = "shared_secret";

	public String request(HashMap<String,String> params, String method, String function, String secret,String data)
	throws FileNotFoundException, MalformedURLException, IOException, NoSuchAlgorithmException {
		
		
		return request(params,method,function,secret,URL,data);
	}
	
	public String request(HashMap<String, String> params,
			String method, String function, String secret, String hostname,String data) 	throws FileNotFoundException, MalformedURLException, IOException, NoSuchAlgorithmException{
		


		String url = hostname + function;
		
		System.out.println("URLRequest: " + url);
		
		if(method.equals("GET"))
			return openGetUrl(url, params,secret);

		if(method.equals("POST"))
			return openPostUrl(url, params,secret,data);

		return null;
	}

	private String openPostUrl(String url, HashMap<String,String> params, String secret,String data) throws MalformedURLException, IOException, NoSuchAlgorithmException {

		params.put("method", "POST");	
		
		String charset = "UTF-8";
		
		String data_hashed = data; //encodeUrl(data,secret);
		
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setDoOutput(true);
		conn.setRequestProperty("Accept-Charset", charset);
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
		OutputStream output = null;
		
		try {
		     output = conn.getOutputStream();
		     output.write(data_hashed.getBytes(charset));
		     
		} finally {
		     if (output != null) try { output.close(); } catch (IOException logOrIgnore) {}
		}

		System.out.println(data_hashed.getBytes(charset));
		String response =  read(conn.getInputStream());
		conn.disconnect();
		return response;
		
		
	}

	private String openGetUrl(String url, HashMap<String,String> params, String secret)  throws MalformedURLException, IOException, NoSuchAlgorithmException {

		params.put("method", "GET");	
		
		
		System.out.println("Request URL: " + url );
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setRequestProperty("User-Agent", System.getProperties().getProperty("http.agent"));
		conn.setRequestMethod("GET");

		String response =  read(conn.getInputStream());
		conn.disconnect();
		return response;
		

	}
	
	
	public static String encodeUrl(String data, String secret) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
		
		String charset = "UTF-8";
		
		String data_hashed = data + secret;	
		
		return URLEncoder.encode(data,charset);

	}
	
	

	private static String read_old(InputStream in) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader r = new BufferedReader(new InputStreamReader(in), 1000);
		for (String line = r.readLine(); line != null; line = r.readLine()) {
			sb.append(line);
		}
		in.close();
		return sb.toString();
		
	}
	
	private static String read(InputStream in) throws IOException {
		
		InputStreamReader input = new InputStreamReader(in, "UTF-8");
        final int CHARS_PER_PAGE = 5000; //counting spaces
        final char[] buffer = new char[CHARS_PER_PAGE];
        StringBuilder output = new StringBuilder(CHARS_PER_PAGE);
        try {
            for(int read = input.read(buffer, 0, buffer.length);
                    read != -1;
                    read = input.read(buffer, 0, buffer.length)) {
                output.append(buffer, 0, read);
            }
            
        } catch (IOException ignore) { }
        finally{
        	input.close();
        }

        return output.toString();
        
	}

	

}
