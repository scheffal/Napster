
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
			System.out.println(serverName);
			//Get Port #
			String portNumber = gui.getPort().getText();
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

	}
}