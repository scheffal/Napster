import java.awt.event.*;

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
			
			
			
		}

	}
}
