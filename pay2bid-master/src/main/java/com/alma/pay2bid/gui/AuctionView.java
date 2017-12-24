package com.alma.pay2bid.gui;

import com.alma.pay2bid.bean.AuctionBean;

import javax.swing.*;
import java.awt.*;

/**
 * The widget used to display an auction
 * @author Alexis Giraudet
 * @author Arnaud Grall
 * @author Thomas Minier
 */
public class AuctionView {

    /**
     * Properties for the main panel
     */
    private JPanel auctionPanel;
    private JPanel auctionPricePanel;
    private JPanel auctionQuickPricePanel;
    private JLabel auctionPriceValue;
    private JLabel auctionTimer;
    private JTextField auctionBid;
    private JLabel auctionBidLabel;
    private JButton raiseButton;
    // quick bid function
    private JLabel quickBidLabel;
    private JButton quickRaiseButton_10;
    private JButton quickRaiseButton_25;

    public AuctionView(AuctionBean auction){
        auctionPanel = new JPanel();
        auctionPanel.setMaximumSize(new Dimension(500, 150));
        auctionPanel.setLayout(new GridLayout(4, 2, 4, 4));
        
        auctionPricePanel = new JPanel();
        // auctionPricePanel.setMaximumSize(new Dimension(450, 120));
        auctionPricePanel.setLayout(new GridLayout(1, 2));
        
        auctionQuickPricePanel = new JPanel();
        // auctionQuickPricePanel.setMaximumSize(new Dimension(450, 120));
        auctionQuickPricePanel.setLayout(new GridLayout(1, 2));

        // Create the price label
        JLabel auctionPriceLabel = new JLabel(" Price : ");
        auctionPriceValue = new JLabel("");
        auctionPriceValue.setText(Integer.toString(auction.getPrice()));
        auctionPriceValue.setLabelFor(auctionPriceLabel);
        auctionPanel.add(auctionPriceLabel);
        auctionPanel.add(auctionPriceValue);

        // Create the bid field
        auctionBid = new JTextField("", JLabel.TRAILING);
        auctionBid.setColumns(1);
        auctionBidLabel = new JLabel("New Price : ");
        auctionBidLabel.setLabelFor(auctionBid);        
        auctionPanel.setBorder(BorderFactory.createTitledBorder(auction.getName()));
        
        quickBidLabel = new JLabel("Quick bid : ");

        //Create the timer label
        auctionTimer = new JLabel("0");
        JLabel auctionTimerLabel = new JLabel("Remaining time : ");
        auctionTimer.setLabelFor(auctionTimerLabel);
        auctionPanel.add(auctionTimerLabel);
        auctionPanel.add(auctionTimer);
        
        auctionPricePanel.add(auctionBidLabel);
        auctionPricePanel.add(auctionBid);
        auctionPanel.add(auctionPricePanel);        
    }

    public void enable() {
        auctionBid.setVisible(true);
        auctionBidLabel.setVisible(true);
        raiseButton.setVisible(true);
        quickRaiseButton_10.setVisible(true);
        quickRaiseButton_25.setVisible(true);
        
    }

    public void disable() {
        // remove the input elements
        auctionBid.setVisible(false);
        auctionBidLabel.setVisible(false);
        raiseButton.setVisible(false);
        quickRaiseButton_10.setVisible(false);
        quickRaiseButton_25.setVisible(false);
    }

    public void setWinner(String name) {
        auctionBidLabel.setText("Winner : " + name);
        auctionBid.setVisible(false);
        raiseButton.setVisible(false);
        quickRaiseButton_10.setVisible(false);
        quickRaiseButton_25.setVisible(false);
        
    }

    public void setRaiseButton(JButton raiseButton) {
        this.raiseButton = raiseButton;        
        auctionPanel.add(raiseButton);
        auctionPanel.add(quickBidLabel);
        auctionPanel.add(auctionQuickPricePanel);
    }
        

    public void setQuickRaiseButton_10(JButton quickRaiseButton_10) {
		this.quickRaiseButton_10 = quickRaiseButton_10;
		auctionQuickPricePanel.add(quickRaiseButton_10);
	}

	public void setQuickRaiseButton_25(JButton quickRaiseButton_25) {
		this.quickRaiseButton_25 = quickRaiseButton_25;
		auctionQuickPricePanel.add(quickRaiseButton_25);
	}

	public void setPrice(int newPrice){
        auctionPriceValue.setText(String.valueOf(newPrice));
    }
	
    public JLabel getAuctionPriceValue() {
		return auctionPriceValue;
	}

	public JTextField getAuctionBid() {
        return auctionBid;
    }

    public JPanel getAuctionPanel() {
        return auctionPanel;
    }

    public void setAuctionTimer(String time) {
        this.auctionTimer.setText(time);
    }
}
