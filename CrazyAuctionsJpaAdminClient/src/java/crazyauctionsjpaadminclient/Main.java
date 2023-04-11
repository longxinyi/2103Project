/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionsjpaadminclient;

import ejb.session.stateless.CreditPackageSessionBeanRemote;
import ejb.session.stateless.EmployeeSessionBeanRemote;
import javax.ejb.EJB;
import util.exception.EmployeeNotFoundException;
import util.exception.ListingNotFoundException;

/**
 *
 * @author xinyi
 */
public class Main {

    @EJB(name = "CreditPackageSessionBeanRemote")
    private static CreditPackageSessionBeanRemote creditPackageSessionBeanRemote;

    @EJB
    private static EmployeeSessionBeanRemote employeeSessionBeanRemote;
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws EmployeeNotFoundException, ListingNotFoundException {
        // TODO code application logic here
        MainApp mainApp = new MainApp(employeeSessionBeanRemote, creditPackageSessionBeanRemote);

        mainApp.runApp();
    }
    
}
