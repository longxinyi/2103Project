/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionsjpajavaseclient;

import ejb.session.stateless.AuctionListingSessionBeanRemote;
import ejb.session.stateless.CreditPackageSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import ejb.session.stateless.EmployeeSessionBeanRemote;
import javax.ejb.EJB;
import util.exception.ListingNotFoundException;

/**
 *
 * @author xinyi
 */
public class CrazyAuctionsJpaJavaSeClient {

    /**
     * @param args the command line arguments
     */
    @EJB(name = "AuctionListingSessionBeanRemote")
    private static AuctionListingSessionBeanRemote auctionListingSessionBeanRemote;

    @EJB(name = "CreditPackageSessionBeanRemote")
    private static CreditPackageSessionBeanRemote creditPackageSessionBeanRemote;

    @EJB
    private static CustomerSessionBeanRemote customerSessionBeanRemote;
    
    public static void main(String[] args) throws ListingNotFoundException {
        // TODO code application logic here
        MainApp mainApp = new MainApp(auctionListingSessionBeanRemote, creditPackageSessionBeanRemote, customerSessionBeanRemote);
        mainApp.runApp();
    }
    
}
