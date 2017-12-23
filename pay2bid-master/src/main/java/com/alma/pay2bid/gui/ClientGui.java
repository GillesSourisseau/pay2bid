package com.alma.pay2bid.gui;

import com.alma.pay2bid.bean.AuctionBean;
import com.alma.pay2bid.client.Client;
import com.alma.pay2bid.client.IClient;
import com.alma.pay2bid.client.observer.IBidSoldObserver;
import com.alma.pay2bid.client.observer.INewAuctionObserver;
import com.alma.pay2bid.client.observer.INewPriceObserver;
import com.alma.pay2bid.client.observer.ITimerObserver;
import com.alma.pay2bid.gui.listeners.AuctionInputListener;
import com.alma.pay2bid.gui.listeners.RaiseBidButtonListener;
import com.alma.pay2bid.server.IServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

import static java.lang.System.exit;

/**
 * The main GUI of the application client-side
 * @author Alexis Giraudet
 * @author Arnaud Grall
 * @author Thomas Minier
 */
public class ClientGui {

    private static final Logger LOGGER = Logger.getLogger(ClientGui.class.getCanonicalName());
    private Client client;
    private IServer server;
    private HashMap<UUID, AuctionView> auctionList;
    Dimension dimension = new Dimension(500, 500);
    /**
     * Main frame & elements
     */
    private JFrame loginFrame;
    private AppFrame appFrame;
    
    //private JPanel mainPanel;
    //private JPanel auctionPanel;

    /**
     * Constructor
     * @param client
     */
    public ClientGui(IServer server) throws RemoteException, InterruptedException {
    	//this.client = client;
        this.server = server;
        auctionList = new HashMap<UUID, AuctionView>();
        
        //display the login frame and if correctly logged in, call createGui
        loginFrame = new LoginFrame(this, server);
        loginFrame.setSize(500,200);
        
        loginFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent){
                exit(0);
            }
        });    
        
    }
    
    public void setClient(Client client) {
    	this.client = client;
    }


    /**
     * Initialize the GUI & populate it with the base elements
     */
    private void createAppGui() {
        // Create the Main JFrame
    	
        appFrame = new AppFrame(this, "Pay2Bid", dimension);
        
        client.addNewAuctionObserver(new INewAuctionObserver() {
            @Override
            public void updateNewAuction(AuctionBean auction) {
                LOGGER.info("A new auction needs to be added to the GUI");
                addAuctionPanel(auction);
            }
        });
        
        appFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent){
                try {
                    server.disconnect(client);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                exit(0);
            }
        });          
    }

    /**
     * Show the client GUI
     */
    public void show(){
        loginFrame.setVisible(true);
    }
    
    public void showApp() {
    	loginFrame.dispose();
    	createAppGui();
    	appFrame.setVisible(true);
    }

    /**
     * Add a new panel to the main panel which display a new Auction
     * @param auctionBean
     */
    private void addAuctionPanel(AuctionBean auctionBean){
        if(!auctionList.containsKey(auctionBean.getUUID())) {
            LOGGER.info("Add new auction to auctionPanel");

            final AuctionView auction = new AuctionView(auctionBean);

            JButton raiseBidButton = new JButton("Raise the bid");
            raiseBidButton.setActionCommand("raiseBid");
            raiseBidButton.addActionListener(new RaiseBidButtonListener(client, client.getServer(), auction, appFrame.getStatusLabel()));
            auction.setRaiseButton(raiseBidButton);

            //Now add the observer to receive all price updates
            client.addNewPriceObserver(new INewPriceObserver() {
                @Override
                public void updateNewPrice(UUID auctionID, Integer price) {
                    setAuctionPrice(auctionID, price);
                    auction.enable();
                }
            });

            // Add a observer to receive the notification when the bid is sold
            client.addBidSoldObserver(new IBidSoldObserver() {
                @Override
                public void updateBidSold(IClient client) {
                    try {
                        auction.setWinner(client.getName());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });

            client.addTimerObserver(new ITimerObserver() {
                @Override
                public void updateTimer(String time) {
                    auction.setAuctionTimer(time);
                }
            });

            appFrame.addAuction(auction.getAuctionPanel());
            auctionList.put(auctionBean.getUUID(), auction);
            
            appFrame.updateGUI();
            
        } else {
            LOGGER.warning("Trying to add a duplicated auction to the list - Auction : " + auctionBean.toString());
        }
    }

    /**
     * Set an new price for a given AuctionBean
     */
    private void setAuctionPrice(UUID auctionID, int newPrice){
        LOGGER.info("auctionPrice set !");
        AuctionView auction = auctionList.get(auctionID);
        //UPDATE AUCTION IN OUR LIST
        auction.setPrice(newPrice);

        //RELOAD THE MAIN PANEL
        auction.getAuctionPanel().revalidate();
        auction.getAuctionPanel().repaint();

        appFrame.updateGUI();
    }

    /**
     * Create the menu to add a new Auction
     */
    public void newAuctionView() {
        AuctionInput input = new AuctionInput(client);
        input.showFrame();
    }
}
