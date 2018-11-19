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
	Socket connectionSocket = null;
	
	public NapsterHost() {
		
		gui = new NapsterFrame();
		gui.getConnect().addActionListener(new Connect(gui, this));
		gui.getSearch().addActionListener(new Connect(gui, this));
		gui.getGo().addActionListener(new Connect(gui, this));
	}
	
	public boolean ConnectToServer() throws NumberFormatException, UnknownHostException, IOException, InterruptedException {
		
		String serverHostname = gui.getServerHostname().getText();
		String portNumber = gui.getPort().getText();
		String hostname = gui.getHostName().getText();
		String username = gui.getUsername().getText();
		String speed = gui.getSpeed().getSelectedItem().toString();

		if((serverHostname.equals("")) || (portNumber.equals("")) || (hostname.equals("")) || (username.equals("")) || (speed.equals("")))
		{
			System.out.println("Invalid input\n");
			return false;
		}

		controlSocket = new Socket(serverHostname, Integer.parseInt(portNumber));
		out = new DataOutputStream(controlSocket.getOutputStream());
		in = new DataInputStream(controlSocket.getInputStream());
		
		out.writeUTF(username +" "+ hostname + " "+ speed +"\n");
	
		while(in.available() <=0 );
		Thread.sleep(500);
		
		String ack = in.readUTF();
		
		if(!ack.equals("received")){
			
			System.out.println("Error getting info!");
			return false;
		}
		
		File fileList = new File("filelist.xml");
		
		OutputStreamWriter outToServer = new OutputStreamWriter(controlSocket.getOutputStream(), "UTF-8");
		
		BufferedReader read = new BufferedReader(new FileReader(fileList));

		String str;

		while((str = read.readLine()) != null)
		{
			out.writeUTF(str + "\n");
			out.flush();
			
		}
		while(in.available() <= 0);
		Thread.sleep(500);
		int serverPort = Integer.parseInt(in.readUTF());
			
		HostServer handler = new HostServer(serverPort);
		handler.start();
		
		return true;
	}

	public void search(String keyword)
	{
		try{
		gui.removeAllRows();

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

		while(inData.available() != 0)
		{
			//Tokenize return
			String found = inData.readUTF();
			StringTokenizer tok = new StringTokenizer(found);

			if(tok.hasMoreTokens())
			{	
				
				String remoteHostName = tok.nextToken();
				String remotePort = tok.nextToken();
				String remoteFileName = tok.nextToken();
				String remoteSpeed = tok.nextToken();

				//Display table
				gui.addRow(remoteSpeed, (remoteHostName + "/" + remotePort), remoteFileName);
			}
		}
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
		
	}
	//Connects to other host machine or retrieves file from connected host machine.
	public void command(String command) throws NumberFormatException, UnknownHostException, IOException{
		
		StringTokenizer tok = new StringTokenizer(command);
		gui.getFTPText().append(">>>"+command+"\n");
		String com = tok.nextToken();
		String arg1 = null;
		String arg2 = null;
		if(tok.hasMoreTokens()){
			arg1 = tok.nextToken();
		}
		if(tok.hasMoreTokens()){
			arg2 = tok.nextToken();
		}
		
		if(com.equals("connect")){
			connectionSocket = new Socket(arg1, Integer.parseInt(arg2));
			gui.getFTPText().append("Connected to "+arg1+"\n");
		}
		else if(com.equals("retr")){
			//Create output stream
			DataOutputStream outToServer = new DataOutputStream(connectionSocket.getOutputStream());

			//Create input stream
			DataInputStream inFromServer = new DataInputStream(new BufferedInputStream(connectionSocket.getInputStream()));
			
			ServerSocket serverSocket = new ServerSocket(8967);
			
			outToServer.writeUTF(com+" "+arg1+" 8967"+" \n");
			
			Socket dataSocket = serverSocket.accept();
			DataInputStream inData = new DataInputStream(new BufferedInputStream(dataSocket.getInputStream()));
			
			//Create a stream to write to file
			FileOutputStream fos = new FileOutputStream(arg1);
		        BufferedOutputStream bufOut = new BufferedOutputStream(fos);

			int bytesRead = 0;
			byte[] byteArray = new byte[4096];
			
			if(inData != null)
			{
				bytesRead = inData.read(byteArray, 0, byteArray.length);

			}

			
			//Write only if file not emtpy
			if(bytesRead > 0)
			{
				//Write to file
				bufOut.write(byteArray, 0, bytesRead);
			}

			//Close streams and socket
			bufOut.close();
			inData.close();
			serverSocket.close();
			dataSocket.close();
			
			gui.getFTPText().append("Successfully downloaded \""+arg1+"\".\n");
			
		}
		else if(com.equals("quit")){
			connectionSocket.close();
			gui.getFTPText().append("Disconnected from server.\n");
		}
	}
	
	public void disconnect()
	{
		try{
			//Close control socket
			controlSocket.close();
		}catch(IOException e)
		{
			System.out.println("ERROR");
		}
	}
	
	public static void main(String args[]) throws Exception
	{
		NapsterHost host = new NapsterHost();
	}
}

//Class that serves all the other hosts that are connected to this one.
class HostHandler extends Thread{
	ServerSocket welcomeSocket;
	Socket connectionSocket;
	private DataOutputStream outToClient;
	private BufferedReader inFromClient;
	
	public HostHandler(Socket connectionSocket) throws IOException{
		this.connectionSocket = connectionSocket;
	}
	
	public void run(){
			try {
				outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            	inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            	while(true){
            	while(!inFromClient.ready());
            	Thread.sleep(500);
            	
            	String command = inFromClient.readLine();
            	System.out.println(command.substring(2));
            	
            	StringTokenizer tok = new StringTokenizer(command.substring(2));
            	
            	String com = tok.nextToken();
            	System.out.println(com);
            	if(com.equals("quit")){
            		connectionSocket.close();
            		return;
            	}
            	else if(com.equals("retr")){
            		String fileName = tok.nextToken();
            		int prt = Integer.parseInt(tok.nextToken());
                	BufferedReader read = new BufferedReader(new FileReader(new File(fileName)));
                	String str;
                	
                	Socket dataSocket = new Socket(connectionSocket.getInetAddress(), prt);
                	OutputStreamWriter  dataOutToClient = new OutputStreamWriter(dataSocket.getOutputStream(), "UTF-8");

            		while((str = read.readLine()) != null)
            		{
            			dataOutToClient.write(str+"\r\n");
            			dataOutToClient.flush();
            		}
            		dataOutToClient.close();
            		dataSocket.close();
            		read.close();
            	}
            	}
            	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
	}
}
// Class is the ServerSocket thread.
class HostServer extends Thread{
	ServerSocket welcomeSocket;
	public HostServer(int serverPort) throws IOException{
		welcomeSocket = new ServerSocket(serverPort);
	}
	
	public void run(){
		while(true)
        {
	//Wait for connection from client
            try {
				Socket connectionSocket = welcomeSocket.accept();
				HostHandler handler = new HostHandler(connectionSocket);
	            handler.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
    }
	}
}


