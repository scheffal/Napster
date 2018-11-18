import java.awt.event.*;
import java.io.IOException;
import java.net.UnknownHostException;

public class Connect implements ActionListener {
	NapsterFrame gui;
	NapsterHost host;
	
	public Connect (NapsterFrame gui, NapsterHost host)
	{
		
		this.gui = gui;
		this.host = host;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand() == "Connect") {
		
			//Get ServerHostname
			String serverName = gui.getServerHostname().getText();
			
			//Get Port #
			String portNumber = gui.getPort().getText();
			System.out.println(portNumber);
			//Get Username
			String username = gui.getUsername().getText();
			
			//Get Hostname
			String hostname = gui.getHostName().getText();
			
			//Get Speed
			String speed = gui.getSpeed().getSelectedItem().toString();

			
			try {
				host.ConnectToServer(serverName, portNumber);
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		}
		if(e.getActionCommand() == "Search")
		{
			String key = gui.getKeyword().getText();
			System.out.println(key);
			
			//TODO change to return required information and updated GUI
			host.search(key);

		}
		if(e.getActionCommand() == "Go"){
			String command = gui.getCommand().getText();
			
			try {
				host.command(command);
				gui.getCommand().setText("");
			} catch (NumberFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}
}