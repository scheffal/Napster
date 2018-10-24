import javax.swing.*;
import java.awt.*;

public class NapsterFrame extends JFrame{

	private TextField serverHostname;
	private TextField portText;
	private TextField usernameText;
	private TextField hostNameText;
	private JButton connectButton;
	
	public NapsterFrame() {
		setTitle("Naptster Host");
		setBackground(Color.LIGHT_GRAY);
		setSize(750,750);
		setResizable(false);
		JPanel connectPanel = new JPanel(new GridBagLayout());
		//setContentPane(connectPanel);
		GridBagConstraints c = new GridBagConstraints();
		connectPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		connectButton = new JButton("Connect");
		//connectButton.addActionListener(this);
		serverHostname = new TextField(20);
		Label serverHostnameText = new Label("Server Hostname:");
		Label port = new Label("Port:");
		portText = new TextField(5);
		Label username = new Label("Username:");
		usernameText = new TextField(10);
		Label hostName = new Label("Hostname:");
		hostNameText = new TextField(15);
		Label speedLabel = new Label("Speed:");
		String[] speeds = {"Ethernet"};
		JComboBox speed = new JComboBox(speeds);
		add(connectPanel);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(2,4,2,2);
		
		c.weightx = 0;
		c.weighty = 0;
		
		c.weightx = 1;
		c.gridx = 1;
		c.gridy = 0;
		connectPanel.add(serverHostname,c);
		
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		connectPanel.add(serverHostnameText,c);
		
		c.weightx = 1;
		c.gridx = 2;
		c.gridy = 0;
		connectPanel.add(port,c);
		
		c.weightx = 0.5;
		c.gridx = 3;
		c.gridy = 0;
		connectPanel.add(portText, c);
		
		c.weightx = 1;
		c.gridwidth = 2;
		c.weightx = 0;
		c.gridx = 4;
		c.gridy = 0;
		connectPanel.add(connectButton,c);
		
		c.gridwidth = 1;
		c.weightx = 0.1;
		c.gridx = 0;
		c.gridy = 1;
		connectPanel.add(username,c);
		
		c.weightx = 0.1;
		c.gridwidth = 1;
		
		c.gridx = 1;
		c.gridy = 1;
		connectPanel.add(usernameText,c);
		
		c.gridx = 2;
		c.gridy = 1;
		connectPanel.add(hostName,c);
		
		c.weightx = 1;
		c.gridx = 3;
		c.gridy = 1;
		connectPanel.add(hostNameText,c);
		
		
		c.gridx = 4;
		c.gridy = 1;
		connectPanel.add(speedLabel,c);
		
		c.gridx = 5;
		c.gridy = 1;
		connectPanel.add(speed,c);
		
		setVisible(true);
	}
	
	public TextField getServerHostname()
	{
		return this.serverHostname;
	}
	public TextField getPort()
	{
		return this.portText;
	}
	public TextField getUsername()
	{
		return this.usernameText;
	}
	public TextField getHostName()
	{
		return this.hostNameText;
	}
	public JButton getConnect()
	{
		return this.connectButton;
	}
	
	public static void main(String args[])
	{
		NapsterFrame frame = new NapsterFrame();
	}
}
