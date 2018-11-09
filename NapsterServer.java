

import java.io.*;
import java.net.*;
import java.util.*;

public class NapsterServer {
	public static void main(String argv[]) throws Exception	
	{
 
            String fromClient;
            String clientCommand;
            byte[] data;
            int port;
            
            //Create socket on server side       
            ServerSocket welcomeSocket = new ServerSocket(3702);
         
            while(true)
            {
            	//Wait for connection from client
                Socket connectionSocket = welcomeSocket.accept();
                System.out.println("Something connected");

                //Create a thread for each client
                ClientHandler handler = new ClientHandler(connectionSocket);
                handler.start();
            }
	}
}
            
            class ClientHandler extends Thread{
            	
            	private DataOutputStream outToClient;
            	private BufferedReader inFromClient;
            	private Socket connectionSocket;
            	String fromClient;
                	String clientCommand;
                	byte[] data;
                	int port;
            	
            	public ClientHandler(Socket connectionSocketIn){
            		connectionSocket = connectionSocketIn;
            		try{
            			//Create input and output stream for client over control connection
            			outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                        		inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

            			System.out.println("User connected" + connectionSocket.getInetAddress());
            			
            		}catch(IOException iox){
            			System.out.println("Error");
            		}
            	
            	}
            	
            	public void run(){
            		String frstln;
            		boolean done = false;
            		try{
            			do{
            				//Read line in from client
            				fromClient = inFromClient.readLine();
            				System.out.println(fromClient);
            				//TODO: Needs conditions to make sure it has all the info it needs.
            				outToClient.writeUTF("received");
            				//Get command
                        			StringTokenizer tokens = new StringTokenizer(fromClient);
            				frstln = tokens.nextToken();
                        			port = Integer.parseInt(frstln);
                        			clientCommand = tokens.nextToken();
                       
                    		 
                      			if(clientCommand.equals("retr:"))
                      			{
            					//Create data socket
                    	  			Socket dataSocket = new Socket(connectionSocket.getInetAddress(), port);
            					
            					OutputStreamWriter dataOutToClient = new OutputStreamWriter(dataSocket.getOutputStream(), "UTF-8");
            	
            					//Get filename
            			        	String fileName = tokens.nextToken();
            					
            					try{
            						//Create file object
            						File file = new File(fileName);

            						//Create stream to read in from file
            						BufferedReader read = new BufferedReader(new FileReader(file));
            						String str;

            						//Read from file and write out to client
            						while((str = read.readLine()) != null)
            						{	
            							dataOutToClient.write(str);
            						}
            				
            						System.out.println("File " + fileName + " sent");


            						//Close all streams and socket			
            						read.close();
            				        	dataOutToClient.close();
            						dataSocket.close();
            						
            					}catch(FileNotFoundException e)
            					{
            						System.out.println("File not found: " + fileName);
            					}
                      			}
            	  									   						if(clientCommand.equals("close"))
            				{
            					//Close all streams and control socket
            					System.out.println("User" + connectionSocket.getInetAddress() + " disconnected");
            					inFromClient.close();
            					outToClient.close();
            					connectionSocket.close();
            					done = true;
            				}else{
            					System.out.println("Command not found");
            				}

            		}while(!done);
            		}catch(IOException iox){
            			
            			System.out.println("Error");	
            		}

            	}

            }