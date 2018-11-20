/*******************************************
* Connect.java
*
* This class is used to call the host 
* functions when an action occurs in the
* GUI. 
*******************************************/
import java.awt.event.*;
import java.io.IOException;
import java.net.UnknownHostException;

public class Connect implements ActionListener {
	private NapsterFrame gui;
	private NapsterHost host;
	
	public Connect (NapsterFrame gui, NapsterHost host)
	{	
		this.gui = gui;
		this.host = host;
	}
	
	/*Handle action events from NapsterFrame*/
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand() == "Connect") {
		

			try {
				//Try to connect to server
				boolean success = host.ConnectToServer();

				//Searching is enabled upon successful connection
				if(success)
				{
					gui.setSearch(true);
				}
			} catch (NumberFormatException e1) {
				
				e1.printStackTrace();
			} catch (UnknownHostException e1) {
				
				e1.printStackTrace();
			} catch (IOException e1) {
				
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				
				e1.printStackTrace();
			}
			
			
		}
		if(e.getActionCommand() == "Search")
		{
			String key = gui.getKeyword().getText();
			
			//Call function as long as there is text in the text field
			if(!key.equals(""))
			{
				//Call host search function
				host.search(key);
			}
			

		}
		if(e.getActionCommand() == "Go"){
			String command = gui.getCommand().getText();
			
			try{
				//Call host command as long as there is text in text field
				if(!command.equals(""))
				{
					host.command(command);
				}
			}catch(UnknownHostException h){
				System.out.println("Error");
			}catch(IOException io){
				System.out.println("Error");
			}	
		
			//Clear textfield		
			gui.getCommand().setText("");
			
		}

	}
}

