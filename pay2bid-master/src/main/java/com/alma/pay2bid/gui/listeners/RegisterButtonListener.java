package com.alma.pay2bid.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.alma.pay2bid.client.Client;
import com.alma.pay2bid.gui.ClientGui;
import com.alma.pay2bid.gui.LoginFrame;
import com.alma.pay2bid.server.IServer;

public class RegisterButtonListener implements ActionListener {
	private IServer server;
	private LoginFrame loginframe;
	private ClientGui clientgui;
	
	public RegisterButtonListener(ClientGui clientgui, IServer server,LoginFrame loginframe) {
		this.server = server;
		this.clientgui = clientgui;
		this.loginframe = loginframe;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//write the given password and username to database on the server
		
		try {
			String username = this.loginframe.getText1().getText();
			String password = this.loginframe.getText2().getText();
			server.registerCredentials(username,password);
			Client client = new Client(server, username, password,username);
			server.register(client);
			clientgui.setClient(client);
			//destroy current frame and generate an appFrame
			clientgui.showApp();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
