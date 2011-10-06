package com.android.utils;



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
import java.util.Set;
import java.util.TreeMap;


public class HTTPUtil {

	public static final String TAG = "APIUtil";
	public static final String URL =  "http://www.qa-idinc.us/api/";
	public static final String FB_TOKEN = "access_token";
	public static final String SHARED_SECRET = "shared_secret";
	public static final String API_SIG = "api_sig";
	public static final String USER_ID = "user_id";

	
	
	public InputStream request(String path, HashMap<String,String> params, String method, String secret)
	throws FileNotFoundException, MalformedURLException, IOException, NoSuchAlgorithmException {
		
		
		return request(path,params,method,secret,URL);
	}
	
	public InputStream request(String path, HashMap<String, String> params,
			String method, String secret, String hostname) 	throws FileNotFoundException, MalformedURLException, IOException, NoSuchAlgorithmException{
		


		String url = hostname + path;
		System.out.println("URLRequest: " + url);
		if(method.equals("GET"))
			return openGetUrl(url, params,secret);

		if(method.equals("POST"))
			return openPostUrl(url, params,secret);

		return null;
	}

	private InputStream openPostUrl(String url, HashMap<String,String> params, String secret) throws MalformedURLException, IOException, NoSuchAlgorithmException {

		params.put("method", "POST");	
		
		String charset = "UTF-8";
		
		String data = encodeUrl(params,secret);
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setDoOutput(true);
		conn.setRequestProperty("Accept-Charset", charset);
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
		OutputStream output = null;
		
		try {
		     output = conn.getOutputStream();
		     output.write(data.getBytes(charset));
		} finally {
		     if (output != null) try { output.close(); } catch (IOException logOrIgnore) {}
		}

		
		String response = "";
		return conn.getInputStream();
		/*
		try {
			response = read(conn.getInputStream());
		} catch (FileNotFoundException e) {
			response = read(conn.getErrorStream());
		}
		return response;*/
	}

	private InputStream openGetUrl(String url, HashMap<String,String> params, String secret)  throws MalformedURLException, IOException, NoSuchAlgorithmException {

		params.put("method", "GET");	
		
		url = url + "?" + encodeUrl(params,secret);	
		System.out.println("Request URL: " + url );
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setRequestProperty("User-Agent", System.getProperties().getProperty("http.agent"));
		conn.setRequestMethod("GET");

		String response = "";
		return conn.getInputStream();
		/*
		try {
			response = read(conn.getInputStream());
		} catch (FileNotFoundException e) {
			response = read(conn.getErrorStream());
		}
		return response;*/

	}
	


	public boolean isSessionValid()
	{
			
		
		if(URL==null) return true;
		

		return true;
	}

	public boolean isSessionReady()
	{
		
		if(URL==null) return false;

		return true;
	}
	
	
	public static String encodeUrl(HashMap<String,String> params, String secret) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (params == null) {
			return "";
		}
		String charset = "UTF-8";
		
		
		Map<String,String> sortedMap = new TreeMap<String,String>(params);
		Iterator<String> it = sortedMap.keySet().iterator();
		
		
		
		StringBuilder result = new StringBuilder();
		
		StringBuilder data = new StringBuilder();
		data.append(secret);
		while(it.hasNext())
		{		
			String key = it.next();
			
			result.append(URLEncoder.encode(key,charset));
			data.append(URLEncoder.encode(key,charset));
			
			result.append("=");
			
			
			
			StringBuilder value_sb = new StringBuilder();
			String val = sortedMap.get(key);
			
			int i=0;
			
			while(i<val.length())
			{
				int range = Math.min(500, val.length()-i);
		
				value_sb.append(URLEncoder.encode(val.substring(i,i+ range),charset));
				
				i+=500;
			}
			String value = value_sb.toString();
			result.append(value);
			data.append(value);
			
			if(it.hasNext()) result.append("&");
		
			
			
		}
		
		if(secret!=null)
		{
		String api_sig = SHA1Util.SHA1(data.toString());
		
		result.append("&");
		result.append(API_SIG);
		result.append("=");
		result.append(api_sig);

		}
		
		
		return result.toString();
	}
	
	public static ArrayList<String> makeSortList(HashMap<String,String> params)
	{
		if (params == null) {
			return null;
		}
		
		ArrayList<String> list = new ArrayList<String>();
		
		for(String key: params.keySet())
			{
		
			list.add(key+ " " + params.get(key).toString());
		
			}
		Collections.sort(list);
		
		return list;
	}

	public static HashMap<String,String> decodeUrl(String s) {
		HashMap<String,String> params = new HashMap<String,String>();
		if (s != null) {
			String array[] = s.split("&");
			for (String parameter : array) {
				String v[] = parameter.split("=");
				params.put(URLDecoder.decode(v[0]),
						URLDecoder.decode(v[1]));
			}
		}
		return params;
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
