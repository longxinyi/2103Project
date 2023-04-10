/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionsjpaemployeeclient;

import ejb.session.stateless.AddressSessionBeanRemote;
import ejb.session.stateless.AuctionListingBidSessionBeanRemote;
import ejb.session.stateless.AuctionListingSessionBeanRemote;
import ejb.session.stateless.CreditPackageSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import ejb.session.stateless.EmployeeSessionBeanRemote;
import javax.ejb.EJB;
import util.exception.AddressNotFoundException;
import util.exception.BidIncrementException;
import util.exception.EmployeeNotFoundException;
import util.exception.InvalidBidIncrementException;
import util.exception.ListingNotFoundException;
import util.exception.MinimumBidException;

/**
 *
 * @author chiaangyong
 */
public class Main {

    @EJB(name = "EmployeeSessionBeanRemote")
    private static EmployeeSessionBeanRemote employeeSessionBeanRemote;

    @EJB(name ="AuctionListingBidSessionBeanRemote")
    private static AuctionListingBidSessionBeanRemote auctionListingBidSessionBeanRemote;

    @EJB(name = "CreditPackageSessionBeanRemote")
    private static CreditPackageSessionBeanRemote creditPackageSessionBeanRemote;
    
    @EJB(name = "AddressSessionBeanRemote")
    private static AddressSessionBeanRemote addressSessionBeanRemote;

    @EJB(name = "AuctionListingSessionBeanRemote")
    private static AuctionListingSessionBeanRemote auctionListingSessionBeanRemote;
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws AddressNotFoundException, EmployeeNotFoundException, ListingNotFoundException, MinimumBidException, BidIncrementException, InvalidBidIncrementException {
        // TODO code application logic here
        
        MainApp mainApp = new MainApp(employeeSessionBeanRemote, creditPackageSessionBeanRemote, auctionListingSessionBeanRemote, addressSessionBeanRemote, auctionListingBidSessionBeanRemote);

        mainApp.runApp();
    }
    
}
