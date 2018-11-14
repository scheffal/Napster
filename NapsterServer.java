

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
	    Map<String,FileEntry> fileTable = new HashMap<String,FileEntry>();
            
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
		private Map<String,FileEntry> fileTable;
            	String fromClient;
                	String clientCommand ="";
                	byte[] data;
                	int port;
            	
            	public ClientHandler(Socket connectionSocketIn, Map<String,User> userTable, Map<String,FileEntry> fileTable){
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

					String nameFile = "";
					FileEntry current = null;

            			do{
					

					//Not sure if it is  fine to just read in file like this or should create
					//TCP connection                    		
					port = 3704;
                        		fromClient = inFromClient.readLine();
                      			//System.out.println(fromClient);
					Pattern pattern = Pattern.compile("\\s*+<name>(.*?)</name>\\s*");
					Pattern pattern2 = Pattern.compile("\\s*+<description>(.*?)</description>\\s*");

										

					if(fromClient != null)
					{		
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
								fileTable.put(nameFile, current);
							}
						}
				
                      			}
            	  									   						   
            		}while(!done);
			done = false;
			do{
					
					fromClient = inFromClient.readLine();
					String clientCommand = "";
					StringTokenizer tok = new StringTokenizer(fromClient);

					if(tok.hasMoreTokens())
					{
						frstln = tok.nextToken();
						port = Integer.parseInt(frstln);
						clientCommand = tok.nextToken();
					}
				
					if(clientCommand.equals("Keyword:"))							
					{
				
						//Create socket on server side
						Socket dataSocket = new Socket(connectionSocket.getInetAddress(), port);
						
						//Create output stream to client
						DataOutputStream out = new DataOutputStream(dataSocket.getOutputStream());
					
						String key = tok.nextToken();
						
						Iterator it = fileTable.entrySet().iterator();
	
						String found = null;
						
						while(it.hasNext())
						{
							Map.Entry entry = (Map.Entry) it.next();
							
							FileEntry file = (FileEntry) entry.getValue(); 
							if(file.description.contains(key))
							{
								//Send remote host name, port number, remote file name, connection speed
								//Search user's table for info
								User remoteHost = userTable.get(file.userName);
								found = remoteHost.hostName + " " + file.portNumber + " " + file.remoteFileName + " " + remoteHost.speed;	
							}
						}

						out.writeUTF(found + " \n");

						out.close();
						dataSocket.close();

					}
					else if(clientCommand.equals("close"))
            				{
            					//Close all streams and control socket
            					System.out.println("User" + connectionSocket.getInetAddress() + " disconnected");
            					inFromClient.close();	
            					outToClient.close();
            					connectionSocket.close();
            					done = true;
            				}else{
            					System.out.println("Command not found: " + clientCommand);
            				}
			}while(!done);
			
			
            		}catch(IOException iox){
            			
            			System.out.println("Error");	
            		}

            	}

            }

