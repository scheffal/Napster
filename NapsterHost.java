
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class NapsterHost{

	public NapsterFrame gui;
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	private Socket controlSocket;
	
	public NapsterHost() {
		gui = new NapsterFrame();
		gui.getConnect().addActionListener(new Connect(gui, this));
		gui.getSearch().addActionListener(new Connect(gui, this));
	}
	
	public void ConnectToServer(String hostName, String portNumber) throws NumberFormatException, UnknownHostException, IOException, InterruptedException {
		
		controlSocket = new Socket(hostName, Integer.parseInt(portNumber));
		out = new DataOutputStream(controlSocket.getOutputStream());
		in = new DataInputStream(controlSocket.getInputStream());
		
		out.writeUTF(gui.getUsername().getText() +" "+gui.getHostName().getText()+" "+gui.getSpeed().getSelectedItem().toString()+"\n");
		
		while(in.available() <=0 );
		Thread.sleep(500);
		
		String ack = in.readUTF();
		
		if(!ack.equals("received")){
			
			System.out.println("Error getting info!");
			return;
		}
		
		File fileList = new File("fileList.xml");
		
		OutputStreamWriter outToServer = new OutputStreamWriter(controlSocket.getOutputStream(), "UTF-8");
		
		BufferedReader read = new BufferedReader(new FileReader(fileList));

		String str;

		while((str = read.readLine()) != null)
		{
	
			
			out.writeUTF(str + "\n");
			out.flush();
			
		}
		
	}

	public void search(String keyword)
	{
		try{
		//Create output stream
		DataOutputStream outToServer = new DataOutputStream(controlSocket.getOutputStream());

		//Create input stream
		DataInputStream inFromServer = new DataInputStream(new BufferedInputStream(controlSocket.getInputStream()));

		ServerSocket welcomeData = new ServerSocket(3704);

		//Send request over control connection	
		outToServer.writeBytes(3704 + " " + "Keyword: " + keyword + " " +  '\n');
		
		//Create socket on client side for data connection
		Socket dataSocket = welcomeData.accept();

		DataInputStream inData = new DataInputStream(new BufferedInputStream(dataSocket.getInputStream()));

		while(inData.available() <= 0);
		Thread.sleep(500);

		//Tokenize return
		String found = inData.readUTF();
		System.out.println(found);
	
		StringTokenizer tok = new StringTokenizer(found);
		String remoteHostName = tok.nextToken();
		String remotePort = tok.nextToken();
		String remoteFileName = tok.nextToken();
		String remoteSpeed = tok.nextToken();

		//Display table
		gui.addRow(remoteFileName, (remoteHostName + "/" + remotePort), remoteSpeed);
		
		//Close all streams and sockets
		inData.close();
		welcomeData.close();
		dataSocket.close();
		}catch(IOException e)
		{
			System.out.println("Exception");
		}catch(InterruptedException e)
		{
			//
		}
		System.out.println("END OF SEARCH");
		
	}
	
	public void disconnect()
	{
		try{
			//Close control socket
			controlSocket.close();
		}catch(IOException e)
		{
			//
		}
	}
	
	public static void main(String args[]) throws Exception
	{
		NapsterHost host = new NapsterHost();
	}
}

