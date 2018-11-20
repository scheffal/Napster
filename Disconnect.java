/*******************************************
* Disconnect.java
*
* This class is used to call the host 
* disconnect function when the GUI is closed
* out of.
*******************************************/

import java.awt.event.*;
import java.io.IOException;
import java.net.UnknownHostException;

public class Disconnect extends WindowAdapter
{
	private NapsterFrame gui;
	private NapsterHost host;

	public Disconnect(NapsterFrame gui, NapsterHost host)
	{
		this.gui = gui;
		this.host = host;
	}

	/*Call disconnect function on window closing event*/
	public void windowClosing(WindowEvent e){
		host.disconnect();
	}
	

}

