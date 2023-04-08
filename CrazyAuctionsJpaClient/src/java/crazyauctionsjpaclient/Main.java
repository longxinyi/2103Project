/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionsjpaclient;

import ejb.session.stateless.AddressSessionBeanRemote;
import ejb.session.stateless.AuctionListingSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import javax.ejb.EJB;
import util.exception.AddressNotFoundException;
import util.exception.CustomerNotFoundException;
import util.exception.ListingNotFoundException;
import util.exception.UpdateCustomerException;

/**
 *
 * @author xinyi
 */
public class Main {

    @EJB
    private static AuctionListingSessionBeanRemote auctionListingSessionBeanRemote;

    @EJB(name = "CustomerSessionBeanRemote")
    private static CustomerSessionBeanRemote customerSessionBeanRemote;
    
    @EJB(name = "AddressSessionBeanRemote")
    private static AddressSessionBeanRemote addressSessionBeanRemote;

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws AddressNotFoundException, CustomerNotFoundException, UpdateCustomerException, ListingNotFoundException {
        // TODO code application logic here
        MainApp mainApp = new MainApp(customerSessionBeanRemote, addressSessionBeanRemote, auctionListingSessionBeanRemote);
        mainApp.runApp();
        
    }
    
}
