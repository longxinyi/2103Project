/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionsjpaclient;


import ejb.session.stateless.AuctionListingSessionBeanRemote;
import ejb.session.stateless.CreditPackageSessionBeanRemote;
import ejb.session.stateless.AddressSessionBeanRemote;
import ejb.session.stateless.AuctionListingBidSessionBeanRemote;
import ejb.session.stateless.AuctionListingSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import javax.ejb.EJB;
import util.exception.AddressNotFoundException;
import util.exception.ListingNotFoundException;
import util.exception.BidIncrementException;
import util.exception.CreditPackageNotFoundException;
import util.exception.CreditTransactionHistoryNotFoundException;
import util.exception.CustomerNotFoundException;
import util.exception.ImposterWinnerException;
import util.exception.InvalidBidIncrementException;
import util.exception.ListingNotActiveException;
import util.exception.ListingNotFoundException;
import util.exception.MinimumBidException;
import util.exception.NoBidException;
import util.exception.UpdateCreditPackageException;
import util.exception.UpdateCustomerException;

/**
 *
 * @author xinyi
 */
public class Main {

    @EJB(name ="AuctionListingBidSessionBeanRemote")
    private static AuctionListingBidSessionBeanRemote auctionListingBidSessionBeanRemote;

    @EJB(name = "CreditPackageSessionBeanRemote")
    private static CreditPackageSessionBeanRemote creditPackageSessionBeanRemote;

    @EJB(name = "CustomerSessionBeanRemote")
    private static CustomerSessionBeanRemote customerSessionBeanRemote;
    
    @EJB(name = "AddressSessionBeanRemote")
    private static AddressSessionBeanRemote addressSessionBeanRemote;

    @EJB(name = "AuctionListingSessionBeanRemote")
    private static AuctionListingSessionBeanRemote auctionListingSessionBeanRemote;
    
    
    
     /**
     * @param args the command line arguments
     */

    public static void main(String[] args) throws AddressNotFoundException, CustomerNotFoundException, UpdateCustomerException, ListingNotFoundException, MinimumBidException, BidIncrementException, InvalidBidIncrementException, ListingNotFoundException, ImposterWinnerException, CreditTransactionHistoryNotFoundException, CreditPackageNotFoundException, UpdateCreditPackageException, ListingNotActiveException, NoBidException {


        // TODO code application logic here

        MainApp mainApp = new MainApp(customerSessionBeanRemote, creditPackageSessionBeanRemote, auctionListingSessionBeanRemote, addressSessionBeanRemote, auctionListingBidSessionBeanRemote);

        mainApp.runApp();
        
    }
    
}
