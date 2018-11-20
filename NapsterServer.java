/***********************************************************
* NapsterServer.java
* CIS 457-10
* Dylan Shoup
* Ali Scheffler
*
* This class is used to implement the centralized server. 
* It allows multiple hosts to connect to the server and upload
* an xml file that contains filenames and descriptions of the 
* files they own. Keeps track of users and files in two 
* separate tables. Upon a request to search for a file with 
* a matching keyword the server sends the information through
* TCP connection to host.  
***********************************************************/

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.*;

public class NapsterServer {


	public static void main(String argv[]) throws Exception	
	{
 
            String fromClient;
            String clientCommand;
            byte[] data;
            int port = 3802;
		
	    //User Table
	    Map<String,User> userTable = new HashMap<String,User>();

	    //File Table
	    Map<String,FileEntry> fileTable = new HashMap<String,FileEntry>();
            
            //Create socket on server side       
            ServerSocket welcomeSocket = new ServerSocket(3702);
         
            while(true)
            {
            	//Wait for connection from client
                Socket connectionSocket = welcomeSocket.accept();

                //Create a thread for each client
                ClientHandler handler = new ClientHandler(connectionSocket, userTable, fileTable, port);
                handler.start();
                port++;
            }
	}
}
            
            class ClientHandler extends Thread{
            	
            	private DataOutputStream outToClient;
            	private BufferedReader inFromClient;
            	private Socket connectionSocket;
		private Map<String,User> userTable;
		private Map<String,FileEntry> fileTable;
            	String fromClient;
                String clientCommand ="";
                byte[] data;
                int port;
                User curr;
            	
            	public ClientHandler(Socket connectionSocketIn, Map<String,User> userTable, Map<String,FileEntry> fileTable, int port){
            	
            		connectionSocket = connectionSocketIn;
            		try{
            			//Create input and output stream for client over control connection
            			outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                        	inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

            			System.out.println("User connected" + connectionSocket.getInetAddress());
				this.userTable = userTable;
				this.fileTable = fileTable;
				this.port = port;
            			
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
					
            				//Get command
                        		StringTokenizer tokens = new StringTokenizer(fromClient);
            				
					String userName = tokens.nextToken();
					
					String hostName = tokens.nextToken();
					
					String speed = tokens.nextToken();
					

					//Add to table if not already
					if(userTable.containsKey(userName) == false)
					{	
						User current = new User(userName, hostName, speed);
						userTable.put(userName,current);
						curr = current;
					}

					if(userName != null && hostName != null && speed != null)
            				outToClient.writeUTF("received");

					String nameFile = "";
					FileEntry current = null;

            			do{
					
                        		fromClient = inFromClient.readLine();

					//Create regular expression pattern to filter out the names and descriptions
					Pattern pattern = Pattern.compile("\\s*+<name>(.*?)</name>\\s*");
					Pattern pattern2 = Pattern.compile("\\s*+<description>(.*?)</description>\\s*");

					if(fromClient != null)
					{		
						//End of file marked by </filelist>
						if(fromClient.contains("</filelist>"))
						{
							done = true;
						}else{
							Matcher matcher = pattern.matcher(fromClient);
							Matcher matcher2 = pattern2.matcher(fromClient);
							String des="";
							while(matcher.find())
							{
								//Get file name
								nameFile = matcher.group(1);
							}
							while(matcher2.find())
							{	
								//Get description
								des = matcher2.group(1);	

								//Add to file table
								current = new FileEntry(userName, port, nameFile, des);
								fileTable.put(nameFile+" "+userName, current);
							}
						}
				
                      			}
            	  									   						   
            		}while(!done);
            			outToClient.writeUTF(Integer.toString(port));
		
				//Reset value of done
				done = false;
			do{
					
					fromClient = inFromClient.readLine();
					String clientCommand = "";
					StringTokenizer tok = new StringTokenizer(fromClient);

				if(fromClient.equals("Close"))
            			{
					//When host disconnects remove their files from fileTable
					List<String> toRemove = new ArrayList<String>();
            			
            				for (Map.Entry<String, FileEntry> entry : fileTable.entrySet()) {
            			   		String key = entry.getKey();
            			    		FileEntry value = entry.getValue();

            			    		if(value.userName.equals(curr.userName)){
            			    			toRemove.add(key);
            			    		}
            		   	 	}
            				for(String x : toRemove){
            					fileTable.remove(x);
            				}

            				//Close all streams and control socket
            				System.out.println("User" + connectionSocket.getInetAddress() + " disconnected");
            				inFromClient.close();	
            				outToClient.close();
            				connectionSocket.close();
            				done = true;
            			}
				else if(tok.hasMoreTokens())
				{
					frstln = tok.nextToken();
					port = Integer.parseInt(frstln);
					clientCommand = tok.nextToken();
				
					if(clientCommand.equals("Keyword:"))							
					{
				
						//Create socket on server side
						Socket dataSocket = new Socket(connectionSocket.getInetAddress(), port);
						
						//Create output stream to client
						DataOutputStream out = new DataOutputStream(dataSocket.getOutputStream());
					
						String key = tok.nextToken();
						
						//Use to iterate through files table
						Iterator it = fileTable.entrySet().iterator();
	
						String found = null;
						
						while(it.hasNext())
						{
							Map.Entry entry = (Map.Entry) it.next();
							FileEntry file = (FileEntry) entry.getValue(); 

							//If match send information to host
							if(file.description.contains(key))
							{
								//Send remote host name, port number, remote file name, connection speed
								//Search user's table for info
								User remoteHost = userTable.get(file.userName);
								found = remoteHost.hostName + " " + file.portNumber + " " + file.remoteFileName + " " + remoteHost.speed;
								//Write to host
								out.writeUTF(found + " \n");
							}
						}
						//Send in case no match found
					 	out.writeUTF("\n");
						
						//Close stream and socket
						out.close();
						dataSocket.close();
						
						//Done
						done = true;

					}
					else{
            					System.out.println("Command not found: " + clientCommand);
            				}
				}
			}while(!done);
			
			
            		}catch(IOException iox){
				//Remove files from table if exception occurs
            			List<String> toRemove = new ArrayList<String>();
            			
            			for (Map.Entry<String, FileEntry> entry : fileTable.entrySet()) {
            			    String key = entry.getKey();
            			    FileEntry value = entry.getValue();

            			    if(value.userName.equals(curr.userName)){
            			    	toRemove.add(key);
            			    }
            			
            		   	 }
            			for(String x : toRemove){
            				fileTable.remove(x);
            			}
            			System.out.println("Error");	
            		}catch(NoSuchElementException e)
			{
				System.out.println("Incorrect input");
			}

            	}

            }


