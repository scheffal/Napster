import java.awt.Frame;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.net.*;
import java.io.*;

public class NapsterHost{

	public NapsterFrame gui;
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	
	public NapsterHost(String name, String server, String port) {
		//TODO
		gui = new NapsterFrame();
		gui.getConnect().addActionListener(new Connect(gui, this));
	}
	
	public static void main(String args[])
	{
		NapsterHost host = new NapsterHost("7", "9", "9");
	}
}
