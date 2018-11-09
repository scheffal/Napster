
import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class NapsterFrame extends JFrame{

	private TextField serverHostname;
	private TextField portText;
	private TextField usernameText;
	private TextField hostNameText;
	private JButton connectButton;
	private TextField keyword;
	private TextField command;
	private JButton search;
	private JButton go;
	private JTextArea ftpDisplay;
	@SuppressWarnings("rawtypes")
	private JComboBox speed;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public NapsterFrame() {
		//Set up frame
		setTitle("Naptster Host");
		setBackground(Color.LIGHT_GRAY);
		setPreferredSize(new Dimension(800,600));
		setLayout(new BorderLayout());
		
		
		//Create panels
		JPanel connectPanel = new JPanel(new GridBagLayout());
		connectPanel.setPreferredSize(new Dimension(100,100));
		
		JPanel searchPanel = new JPanel(new GridBagLayout());
		
		JPanel ftpPanel = new JPanel(new GridBagLayout());
		ftpPanel.setPreferredSize(new Dimension(250,250));
		
		//Set borders around panels
		connectPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Connection"),
			    BorderFactory.createEmptyBorder(5, 5, 5, 5)));
			
		searchPanel.setBorder(BorderFactory.createCompoundBorder(
			    BorderFactory.createTitledBorder("Search"),
			    BorderFactory.createEmptyBorder(5, 5, 5, 5)));
				
		ftpPanel.setBorder(BorderFactory.createCompoundBorder(
			     BorderFactory.createTitledBorder("FTP"),
			     BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		//Constraints for connect panel
		GridBagConstraints c = new GridBagConstraints();
		
		//Connect panel 
		connectButton = new JButton("Connect");
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
		speed = new JComboBox(speeds);
		
		//Search panel
		keyword = new TextField(20);
		Label keywordLabel = new Label("Keyword:");
		search = new JButton("Search");
		
		
		//FTP pane
		command = new TextField(50);
		Label commandLabel = new Label("Command:");
		go = new JButton("Go");
		ftpDisplay = new JTextArea(5,5);
		
		//Constraints for search panel
		GridBagConstraints s = new GridBagConstraints();
		
		//Constraints for FTP panel
		GridBagConstraints f = new GridBagConstraints();
		
		//Add panels to frame
		add(connectPanel,BorderLayout.NORTH);
		add(searchPanel,BorderLayout.CENTER);
		add(ftpPanel, BorderLayout.SOUTH);
		
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
		
		s.gridy = 0;
		s.gridx = 0;
		s.fill = GridBagConstraints.HORIZONTAL;
		s.insets = new Insets(2,4,2,2);
		searchPanel.add(keywordLabel,s);
		
		s.gridx = 1;
		searchPanel.add(keyword,s);
		
		s.gridx = 2;
		searchPanel.add(search, s);
		
		f.gridy = 0;
		f.gridx = 0;
		f.fill = GridBagConstraints.HORIZONTAL;
		f.insets = new Insets(2,4,2,2);
		ftpPanel.add(commandLabel, f);
		
		f.gridx = 1;
		ftpPanel.add(command, f);
		
		f.gridx = 2;
		ftpPanel.add(go, f);
		
		f.gridx = 0;
		f.gridy = 1;
		f.gridwidth = 3;
		f.gridheight = 10;
		JScrollPane scroll = new JScrollPane(ftpDisplay);
		ftpPanel.add(scroll,f);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
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
	public JComboBox getSpeed(){
		return this.speed;
	}
	
	public static void main(String args[])
	{
		NapsterFrame frame = new NapsterFrame();
	}
}