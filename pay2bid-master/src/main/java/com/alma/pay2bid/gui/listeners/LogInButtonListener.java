package com.alma.pay2bid.gui.listeners;

import java.awt.event.ActionEvent;
import com.alma.pay2bid.gui.*;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.alma.pay2bid.client.Client;
import com.alma.pay2bid.gui.ClientGui;
import com.alma.pay2bid.server.IServer;

public class LogInButtonListener implements ActionListener {
	private IServer server;
	private ClientGui clientgui;
	private LoginFrame loginframe;
	
	public LogInButtonListener(ClientGui clientgui, IServer server, LoginFrame loginframe) {
		this.server = server;
		this.clientgui = clientgui;
		this.loginframe = loginframe;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
			try {
				String username = this.loginframe.getText1().getText();
				String password = this.loginframe.getText2().getText();
				System.out.println("\n"+username+ " "+password+" " + "\n");
				if(server.verifyCredentials(username,password) == true) {
					Client client = new Client(server, username, password,username);
					server.register(client);
					clientgui.setClient(client);
					//destroy current frame and generate an appFrame
					clientgui.showApp();
				}else {
					System.out.println("ERROR: le mot de passe ou l'username n'est pas correct");
					this.loginframe.getErrorLabel().setText("username or password incorrect. retry! ");
					this.loginframe.updateGui();
				}
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

	}

}
