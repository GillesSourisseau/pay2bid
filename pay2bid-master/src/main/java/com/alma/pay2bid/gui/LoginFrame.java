package com.alma.pay2bid.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.alma.pay2bid.client.Client;
import com.alma.pay2bid.gui.listeners.LogInButtonListener;
import com.alma.pay2bid.gui.listeners.RegisterButtonListener;
import com.alma.pay2bid.server.IServer;

public class LoginFrame extends JFrame
{
	IServer server;
	ClientGui clientgui;
	//Client client;
	JButton submit;
	JButton register;
	JPanel panel;
	JLabel label1,label2, errorLabel;
	final JTextField  text1,text2;
	
	LoginFrame(ClientGui clientgui, IServer server)
	{
		this.server = server;
		this.clientgui = clientgui;
		label1 = new JLabel();
		label1.setText("Username:");
		text1 = new JTextField(15);

		label2 = new JLabel();
		label2.setText("Password:");
		text2 = new JPasswordField(15);

		submit=new JButton("LogIn");
		register= new JButton("Register");
		
		errorLabel = new JLabel();

		panel=new JPanel(new GridLayout(4,1));
		panel.add(label1);
		panel.add(text1);
		panel.add(label2);
		panel.add(text2);
		panel.add(submit);
		panel.add(register);
		panel.add(errorLabel);
		add(panel,BorderLayout.CENTER);
		submit.addActionListener(new LogInButtonListener(clientgui,server,this));
		register.addActionListener(new RegisterButtonListener(clientgui,server,this));
		setTitle("Pay2Bid Login");
	}

	public JLabel getErrorLabel() {
		return errorLabel;
	}

	public void setErrorLabel(JLabel errorLabel) {
		this.errorLabel = errorLabel;
	}

	public JTextField getText1() {
		return text1;
	}

	public JTextField getText2() {
		return text2;
	}
	
	public void updateGui() {
		panel.revalidate();
		panel.repaint();
	}
}
