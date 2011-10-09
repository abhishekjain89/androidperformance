package com.android.activities;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;



import com.android.R;
import com.android.helpers.PingHelper;


public class AnalysisActivity extends Activity 
{

	/*private final int UPDATE_MSG = 0;

	private Button 		pingButton;
	private Button 		saveButton;
	//private Button 		updateButton;
	private EditText 	input;
	private EditText 	cmd;
	private TextView 	output;
	private Handler 	handler = new Handler();	
	private boolean 	mPing = false;
	private String		message;
	private UIUpdateThread mUIUpdateThread = null;
	private String 	   pingMsg = "";
	private Object     pingMsgLock = new Object();*/
	
	private Button testButton;
	private TextView tv;
	private PingHelper ph;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		testButton=(Button)findViewById(R.id.test);
		tv = (TextView)findViewById(R.id.textView1);
		
		
		testButton.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) 
			{
				//Whatever we need to call
				ph = new PingHelper();
				ph.pingHelp();
				String output = ph.getPingOutput();
				output += "";
				tv.append(output);
			}
		});
		
		

		/*handler.post(new Runnable(){

			public void run() {
				// call your function
				synchronized (pingMsgLock) {
					if(pingMsg != ""){
						output.append(pingMsg);
						pingMsg = "";
					}
				}
				
				handler.postDelayed(this,100);//will update the textview every 1.5 seconds..it is supposed that you append different text in your program.

			}

		});

		mUIUpdateThread = new UIUpdateThread();
		mUIUpdateThread.start();

		pingButton 	= (Button)findViewById(R.id.ping);
		saveButton 	= (Button)findViewById(R.id.save);
		//updateButton = (Button)findViewById(R.id.update);
		input 		= (EditText)findViewById(R.id.entry);
		cmd 		= (EditText)findViewById(R.id.cmd);
		output 		= (TextView)findViewById(R.id.output);

		output.setMovementMethod(ScrollingMovementMethod.getInstance());
		output.setMovementMethod(new ScrollingMovementMethod());


		pingButton.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) 
			{
				output.setText("");
				mPing = true;
				//				ping (input.getText());
			}
		});

		saveButton.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) 
			{
				// Save to external file	
				//saveToExternalFile (message);
				saveToExternalFile (output.getText().toString());
			}
		});


		input.setOnClickListener(new OnClickListener()
		{
			public void onClick (View v)
			{
				input.setText("");
			}
		});

		cmd.setOnClickListener(new OnClickListener()
		{
			public void onClick (View v)
			{
				cmd.setText("");
			}
		});*/
	}

	/**
	 * Saves ping data to external storage
	 * @param msg
	 */
	/*public void saveToExternalFile (String msg)
	{
		OutputStream outStream = null;
		String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
		File file = new File(extStorageDirectory, "ping.txt");
		try {
			outStream = new FileOutputStream(file, false);
			OutputStreamWriter osw = new OutputStreamWriter(outStream);
			osw.write(msg + "\n");
			osw.flush();
			osw.close();
		}
		catch (Exception e)
		{
			Log.d("PingActivity", e.getLocalizedMessage());
		}
		ftp();
	}

	public void ftp()
	{
		
		String server = "31.170.160.85";
		String user = "a5398321";
		String password = "KYS@z3r0";
		try {
			FTPClient ftpClient = new FTPClient();
			ftpClient.connect(InetAddress.getByName(server));
			ftpClient.login(user, password);
			ftpClient.changeWorkingDirectory("/Test");
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			BufferedInputStream buffIn = null;
			File file = new File(Environment.getExternalStorageDirectory().toString(), "ping.txt");
			buffIn=new BufferedInputStream(new FileInputStream(file));
			ftpClient.enterLocalPassiveMode();
			Date now = new Date();
			String fileName = "ping" + now.getMonth() + "_" + now.getDate() + "_" + now.getHours() + ".txt";
			ftpClient.storeFile(fileName, buffIn);
			buffIn.close();
			ftpClient.logout();
			ftpClient.disconnect();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public class UIUpdateThread extends Thread
	{
		public void run()
		{
			while(true)
			{
				if(mPing)
				{
					ping(input.getText());
					mPing = false;
				}
				try 
				{
					Thread.sleep(1000);
				} 
				catch (InterruptedException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}*/
		

		/**
		 * 
		 * @param ipAddress
		 */
		//public void ping (InetAddress in)
		/*public void ping (Editable editable)
		{
			Process process;
			String ping_cmd = "/system/bin/ping " + cmd.getText().toString() + " " + input.getText().toString();
			//String ping_cmd = "/system/bin/netstat";
			String line 	= "";
			message 		= "";
			try 
			{
				if (cmd.getText().toString().equals("") || cmd.getText().toString().equalsIgnoreCase("Enter command"))
				{
					ping_cmd = "/system/bin/ping -c 5 " + editable.toString();
				}
				process = Runtime.getRuntime().exec(ping_cmd);
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				message += "";
				while ((line = bufferedReader.readLine()) != null)
				{
					message += line + "\n";
					synchronized(pingMsgLock)
					{
						pingMsg += line + "\n";
					}
				}
				int status = process.waitFor();
				Log.d("PingActivity", "Current Status: " + status);
			}
			catch (Exception e) 
			{
				Log.d("PingActibity", e.getLocalizedMessage());
				output.setText("Error" + e.toString());
			}
		}

		public void upload ()
		{
			HttpURLConnection connection = null;
			DataOutputStream outputStream = null;
			DataInputStream inputStream = null;

			String pathToOurFile =  Environment.getExternalStorageDirectory().toString() + "/ping.txt";
			String urlServer = "http://192.168.1.1/handle_upload.php";
			String lineEnd = "\r\n";
			String twoHyphens = "--";
			String boundary =  "*****";

			int bytesRead, bytesAvailable, bufferSize;
			byte[] buffer;
			int maxBufferSize = 1*1024*1024;

			try
			{
				FileInputStream fileInputStream = new FileInputStream(new File(pathToOurFile) );
	
				URL url = new URL(urlServer);
				connection = (HttpURLConnection) url.openConnection();
	
				// Allow Inputs & Outputs
				connection.setDoInput(true);
				connection.setDoOutput(true);
				connection.setUseCaches(false);
	
				// Enable POST method
				connection.setRequestMethod("POST");
	
				connection.setRequestProperty("Connection", "Keep-Alive");
				connection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
	
				outputStream = new DataOutputStream( connection.getOutputStream() );
				outputStream.writeBytes(twoHyphens + boundary + lineEnd);
				outputStream.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + pathToOurFile +"\"" + lineEnd);
				outputStream.writeBytes(lineEnd);
	
				bytesAvailable = fileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				buffer = new byte[bufferSize];
	
				// Read file
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);
	
				while (bytesRead > 0)
				{
					outputStream.write(buffer, 0, bufferSize);
					bytesAvailable = fileInputStream.available();
					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					bytesRead = fileInputStream.read(buffer, 0, bufferSize);
				}
	
				outputStream.writeBytes(lineEnd);
				outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
	
				// Responses from the server (code and message)
				int serverResponseCode = connection.getResponseCode();
				String serverResponseMessage = connection.getResponseMessage();
	
				fileInputStream.close();
				outputStream.flush();
				outputStream.close();
			}
			catch (Exception ex)
			{
			//Exception handling
			}
		}
	}*/
}