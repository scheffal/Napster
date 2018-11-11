

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

public class NapsterServer {


	public static void main(String argv[]) throws Exception	
	{
 
            String fromClient;
            String clientCommand;
            byte[] data;
            int port;
		
	    //User Table
	    Map<String,User> userTable = new HashMap<String,User>();

	    //File Table
	    Map<String,File> fileTable = new HashMap<String,File>();
            
            //Create socket on server side       
            ServerSocket welcomeSocket = new ServerSocket(3702);
         
            while(true)
            {
            	//Wait for connection from client
                Socket connectionSocket = welcomeSocket.accept();

                //Create a thread for each client
                ClientHandler handler = new ClientHandler(connectionSocket, userTable, fileTable);
                handler.start();
            }
	}
}
            
            class ClientHandler extends Thread{
            	
            	private DataOutputStream outToClient;
            	private BufferedReader inFromClient;
            	private Socket connectionSocket;
		private Map<String,User> userTable;
		private Map<String,File> fileTable;
            	String fromClient;
                	String clientCommand ="";
                	byte[] data;
                	int port;
            	
            	public ClientHandler(Socket connectionSocketIn, Map<String,User> userTable, Map<String,File> fileTable){
            		connectionSocket = connectionSocketIn;
            		try{
            			//Create input and output stream for client over control connection
            			outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                        	inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

            			System.out.println("User connected" + connectionSocket.getInetAddress());
				this.userTable = userTable;
				this.fileTable = fileTable;
            			
            		}catch(IOException iox){
            			System.out.println("Error");
            		}
            	
            	}
            	
            	public void run(){
            		String frstln;
            		boolean done = false;
            		try{
					
					//Read line in from client
            				fromClient = inFromClient.readLine();
            				//System.out.println(fromClient);
					
            				//Get command
                        		StringTokenizer tokens = new StringTokenizer(fromClient);
            				
					String userName = tokens.nextToken();
					//System.out.println(userName);
					String hostName = tokens.nextToken();
					//System.out.println(hostName);
					String speed = tokens.nextToken();
					//System.out.println(speed);
					
					//Add to table if not already
					if(userTable.containsKey(userName) == false)
					{	
						User current = new User(userName, hostName, speed);
						userTable.put(userName,current);
					}

					//TODO: Needs conditions to make sure it has all the info it needs.
            				outToClient.writeUTF("received");

            			do{
					

					//TODO                        		
					port = 3704;
                        		fromClient = inFromClient.readLine();
                      			//System.out.println(fromClient);
					Pattern pattern = Pattern.compile("\\s*+<name>(.*?)</name>\\s*");
					Pattern pattern2 = Pattern.compile("\\s*+<description>(.*?)</description>\\s*");

					
					if(fromClient != null)
					{
						//System.out.println("Line: " + fromClient);
						Matcher matcher = pattern.matcher(fromClient);
						Matcher matcher2 = pattern2.matcher(fromClient);
						String nameFile="", des="";
						while(matcher.find())
						{
							//Get file name
							nameFile = matcher.group(1);
							System.out.println(nameFile);
						}
						while(matcher2.find())
						{
							//Get description
							des = matcher2.group(1);
							System.out.println(des);	

							//Add to file table
							File current = new File(userName, port, nameFile, des);
							fileTable.put(nameFile, current);
						}
				
                      			}
            	  									   						   						if(fromClient != null && fromClient.equals("close"))
            				{
            					//Close all streams and control socket
            					System.out.println("User" + connectionSocket.getInetAddress() + " disconnected");
            					inFromClient.close();
            					outToClient.close();
            					connectionSocket.close();
            					done = true;
            				}else{
            					//System.out.println("Command not found: " + fromClient);
            				}

            		}while(!done);
            		}catch(IOException iox){
            			
            			System.out.println("Error");	
            		}

            	}

            }