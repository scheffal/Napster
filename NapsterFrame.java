/*******************************************
* NapsterFrame.java
*
* This class is used to implement the GUI 
* for Napster Host. Creates a GUI with 
* options to connect to centralized server, 
* search for a file, and connect to remote 
* server.
*******************************************/

import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;

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
	private JTable filesTable;
	private DefaultTableModel model;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public NapsterFrame() {
		//Set up frame
		setTitle("Naptster Host");
		setBackground(Color.LIGHT_GRAY);
		setPreferredSize(new Dimension(800,600));
		setLayout(new BorderLayout());
		
		
		//Create connect panel
		JPanel connectPanel = new JPanel(new GridBagLayout());
		connectPanel.setPreferredSize(new Dimension(100,100));
		
		//Create search panel
		JPanel searchPanel = new JPanel(new GridBagLayout());
		
		//Create FTP panel
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
		String[] speeds = {"Ethernet", "T1"};
		speed = new JComboBox(speeds);
		
		//Search panel
		keyword = new TextField(20);
		Label keywordLabel = new Label("Keyword:");
		search = new JButton("Search");
		search.setEnabled(false);
			
		//Create default table model to later add rows of data to
		model = new DefaultTableModel();
		filesTable = new JTable(model);

		model.addColumn("Speed");
		model.addColumn("Hostname");
		model.addColumn("Filename");
		
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
		
		//Add components to panel 
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

		s.gridy = 2;
		s.gridx = 1;
		s.weightx = 3;
		s.gridheight = 10;

		//Set the file table to scroll and add to panel
		filesTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
		filesTable.setFillsViewportHeight(true); 
		JScrollPane table = new JScrollPane(filesTable);
		table.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		searchPanel.add(table,s);
		
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

		//Set FTP text area to scroll and add to panel
		JScrollPane scroll = new JScrollPane(ftpDisplay);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		ftpPanel.add(scroll,f);
		pack();

		//Make frame visible
		setVisible(true);
	}

	/*Enable or disable search button*/
	public void setSearch(boolean val)
	{
		search.setEnabled(val);
	}	

	/*Add a row to the files table*/
	public void addRow(String fileName, String host, String speed){
		model.addRow(new Object[] {fileName, host, speed});
	
	}

	/*Remove all rows from files table*/
	public void removeAllRows()
	{
		model.setRowCount(0);
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
	public JButton getSearch()
	{
		return this.search;
	}
	public JComboBox getSpeed(){
		return this.speed;
	}

	public TextField getKeyword()
	{
		return this.keyword;
	}
	public TextField getCommand(){
		return this.command;
	}
	public JButton getGo(){
		return this.go;
	}
	public JTextArea getFTPText(){
		return this.ftpDisplay;
	}
	
	public static void main(String args[])
	{
		NapsterFrame frame = new NapsterFrame();
	}
}


