
import javax.swing.*;
import java.net.*;
import java.io.*;

public class NapsterHost{

	public NapsterFrame gui;
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	
	public NapsterHost(String name, String server, String port) {
		//TODO
		gui = new NapsterFrame();
		gui.getConnect().addActionListener(new Connect(gui, this));
	}
	
	public void ConnectToServer(String hostName, String portNumber) throws NumberFormatException, UnknownHostException, IOException, InterruptedException {
		
		Socket controlSocket = new Socket(hostName, Integer.parseInt(portNumber));
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
			System.out.println(str);
			outToServer.write(str);
		}
	}
	
	public static void main(String args[])
	{
		NapsterHost host = new NapsterHost("7", "9", "9");
	}
}