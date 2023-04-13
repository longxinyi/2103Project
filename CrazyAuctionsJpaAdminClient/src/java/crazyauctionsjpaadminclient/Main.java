/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionsjpaadminclient;

import ejb.session.stateless.AuctionListingSessionBeanRemote;
import ejb.session.stateless.CreditPackageSessionBeanRemote;
import ejb.session.stateless.EmployeeSessionBeanRemote;
import java.text.ParseException;
import javax.ejb.EJB;
import util.exception.CreditPackageNotFoundException;
import util.exception.CreditTransactionHistoryNotFoundException;
import util.exception.DeleteCreditPackageException;
import util.exception.EmployeeNotFoundException;
import util.exception.ListingNotFoundException;
import util.exception.NoBidException;
import util.exception.UpdateCreditPackageException;
import util.exception.WrongDateException;

/**
 *
 * @author xinyi
 */
public class Main {

    @EJB(name = "AuctionListingSessionBeanRemote")
    private static AuctionListingSessionBeanRemote auctionListingSessionBeanRemote;

    @EJB(name = "CreditPackageSessionBeanRemote")
    private static CreditPackageSessionBeanRemote creditPackageSessionBeanRemote;

    @EJB
    private static EmployeeSessionBeanRemote employeeSessionBeanRemote;
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws EmployeeNotFoundException, ListingNotFoundException, CreditTransactionHistoryNotFoundException, CreditPackageNotFoundException, UpdateCreditPackageException, DeleteCreditPackageException, ParseException, WrongDateException, NoBidException {
        // TODO code application logic here
        MainApp mainApp = new MainApp(employeeSessionBeanRemote, creditPackageSessionBeanRemote, auctionListingSessionBeanRemote);

        mainApp.runApp();
    }
    
}
