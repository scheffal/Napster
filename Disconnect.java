import java.awt.event.*;
import java.io.IOException;
import java.net.UnknownHostException;

public class Disconnect extends WindowAdapter
{
	NapsterFrame gui;
	NapsterHost host;
	

	public Disconnect(NapsterFrame gui, NapsterHost host)
	{
		this.gui = gui;
		this.host = host;
	}

	
	public void windowClosing(WindowEvent e){
		host.disconnect();
	}
	

}
