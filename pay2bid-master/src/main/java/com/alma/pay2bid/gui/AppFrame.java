package com.alma.pay2bid.gui;

import static java.lang.System.exit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.alma.pay2bid.client.Client;
import com.alma.pay2bid.client.IClient;
import com.alma.pay2bid.gui.listeners.AuctionInputListener;
import com.alma.pay2bid.server.IServer;

public class AppFrame extends JFrame{
	private JPanel auctionPanel;
	private JPanel mainPanel;
	private JLabel statusLabel;
	private ClientGui clientgui;
	
	public AppFrame(ClientGui clientgui, String title, Dimension dimension) {
		this.clientgui = clientgui;
		this.setTitle(title);
        this.setSize(500, 500);
        this.setMaximumSize(dimension);
        this.setLayout(new BorderLayout());
        
     // Create the Menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Options");
        
        JMenuItem newAuction = new JMenuItem("New Auction");
        newAuction.setActionCommand("newAuction");
        newAuction.addActionListener(new AuctionInputListener(clientgui));
        menu.add(newAuction);
        
        menuBar.add(menu);
        this.setJMenuBar(menuBar);

        // Create the Frame Header
        JLabel headerLabel = new JLabel("", JLabel.CENTER);
        headerLabel.setText("Auction Lobby");

        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setBackground(Color.red);
        statusLabel.setSize(400,0);

        this.add(headerLabel, BorderLayout.PAGE_START);
        
		mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        auctionPanel = new JPanel();
        auctionPanel.setLayout(new BoxLayout(auctionPanel, BoxLayout.Y_AXIS));
        mainPanel.add(auctionPanel);
        mainPanel.setMaximumSize(dimension);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        this.add(scrollPane, BorderLayout.CENTER);

        this.add(statusLabel, BorderLayout.PAGE_END);
	}

	public JLabel getStatusLabel() {
		return this.statusLabel;
	}
	
	public void addAuction(JPanel auction) {
		this.auctionPanel.add(auction);
	}
	
	public void updateGUI() {
		mainPanel.revalidate();
        mainPanel.repaint();
	}
}
