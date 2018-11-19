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
		

			try {
				boolean success = host.ConnectToServer();
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
			
			if(!key.equals(""))
			{
				//Call host search function
				host.search(key);
			}
			

		}
		if(e.getActionCommand() == "Go"){
			String command = gui.getCommand().getText();
			
			try {
				if(!command.equals(""))
				{
					host.command(command);
				}				
				gui.getCommand().setText("");
			} catch (NumberFormatException | IOException e1) {
				e1.printStackTrace();
			}
		}

	}
}

