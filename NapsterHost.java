import java.awt.Frame;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class NapsterHost extends JFrame{

	private Label hostName;
	private TextField hostText;
	private Button connect;
	
	public NapsterHost() {
		setTitle("Naptster Host");
		setBackground(Color.LIGHT_GRAY);
		setSize(700,700);
		setResizable(false);
		JPanel connectPanel = new JPanel(new GridBagLayout());
		//setContentPane(connectPanel);
		GridBagConstraints c = new GridBagConstraints();
		
		JButton connectButton = new JButton("Connect");
		TextField serverHostname = new TextField(20);
		Label serverHostnameText = new Label("Server Hostname:");
		Label port = new Label("Port:");
		TextField portText = new TextField(5);
		Label username = new Label("Username:");
		TextField usernameText = new TextField(10);
		Label hostName = new Label("Hostname:");
		TextField hostNameText = new TextField(15);
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
	
	public static void main(String args[])
	{
		NapsterHost host = new NapsterHost();
	}
}
