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
import entity.AuctionListing;
import entity.Customer;
import entity.Employee;
import java.math.BigDecimal;
import java.util.Scanner;
import util.exception.AddressNotFoundException;
import util.exception.BidIncrementException;
import util.exception.CustomerNotFoundException;
import util.exception.CustomerUsernameExistException;
import util.exception.EmployeeNotFoundException;
import util.exception.EmployeeUsernameExistException;
import util.exception.InvalidBidIncrementException;
import util.exception.InvalidLoginCredentialException;
import util.exception.ListingNotFoundException;
import util.exception.MinimumBidException;
import util.exception.UpdateCustomerException;

/**
 *
 * @author chiaangyong
 */
public class MainApp {
    private EmployeeSessionBeanRemote employeeSessionBeanRemote;
    private CreditPackageSessionBeanRemote creditPackageSessionBeanRemote;
    private AuctionListingSessionBeanRemote auctionListingSessionBeanRemote;
    private AddressSessionBeanRemote addressSessionBeanRemote;
    private AuctionListingBidSessionBeanRemote auctionListingBidSessionBeanRemote;
    
    private Employee currentEmployee;
    private AuctionListing currentListing;
    
     public MainApp() {

    }




    public MainApp(EmployeeSessionBeanRemote employeeSessionBeanRemote, CreditPackageSessionBeanRemote creditPackageSessionBeanRemote, AuctionListingSessionBeanRemote auctionListingSessionBeanRemote, AddressSessionBeanRemote addressSessionBeanRemote, AuctionListingBidSessionBeanRemote auctionListingBidSessionBeanRemote)
    {

        this.employeeSessionBeanRemote = employeeSessionBeanRemote;
        this.creditPackageSessionBeanRemote = creditPackageSessionBeanRemote;
        this.auctionListingSessionBeanRemote = auctionListingSessionBeanRemote;
        this.addressSessionBeanRemote = addressSessionBeanRemote;
        this.auctionListingBidSessionBeanRemote = auctionListingBidSessionBeanRemote;
        
        
    }
    
    public void runApp() throws AddressNotFoundException, EmployeeNotFoundException, ListingNotFoundException, MinimumBidException, BidIncrementException, InvalidBidIncrementException {



        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Welcome to Crazy Auction Employee Edition ***\n");
            System.out.println("1: Login");
            System.out.println("2: Register");
            System.out.println("3: Exit\n");
            response = 0;

            while (response < 1 || response > 3) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    try {
                        doLogin();
                        System.out.println("Login successful!\n");

                        menuMain();
                    } catch (InvalidLoginCredentialException ex) {
                        System.out.println("Invalid login credential: " + ex.getMessage() + "\n");
                    }
                } else if (response == 2) {
                    try {
                        doRegister();
                        System.out.println("Login successful!\n");

                        menuMain();
                    } catch (InvalidLoginCredentialException ex ) {
                        System.out.println("Invalid login credential: " + ex.getMessage() + "\n");
                    }
                } else if (response == 3) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 3) {
                break;
            }
        }
    }
    
        private void doLogin() throws InvalidLoginCredentialException {
        Scanner scanner = new Scanner(System.in);
        String username = "";
        String password = "";

        System.out.println("*** Crazy Auction :: Login ***\n");
        System.out.print("Enter username> ");
        username = scanner.nextLine().trim();
        System.out.print("Enter password> ");
        password = scanner.nextLine().trim();

        if (username.length() > 0 && password.length() > 0) {
            currentEmployee = employeeSessionBeanRemote.employeeLogin(username, password);
        } else {
            throw new InvalidLoginCredentialException("Missing login credential!");
        }
    }

    private void doRegister() {
        Scanner scanner = new Scanner(System.in);
        currentEmployee = new Employee();

        System.out.println("*** CrazyAuctions: Registration ***\n");
        System.out.print("Enter First Name> ");
        currentEmployee.setFirstName(scanner.nextLine().trim());
        System.out.print("Enter Last Name> ");
        currentEmployee.setLastName(scanner.nextLine().trim());
        System.out.print("Enter Username> ");
        currentEmployee.setUsername(scanner.nextLine().trim());
        System.out.print("Enter Password> ");
        currentEmployee.setPassword(scanner.nextLine().trim());

        try {
            Long employeeId = employeeSessionBeanRemote.createNewEmployee(currentEmployee);
            
        } catch (EmployeeUsernameExistException ex) {
            System.out.println("An error has occurred while creating the new staff!: The user name already exist\n");
        } 

    }
        private void menuMain() throws InvalidLoginCredentialException, AddressNotFoundException, EmployeeNotFoundException, ListingNotFoundException, MinimumBidException, BidIncrementException, InvalidBidIncrementException {


        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Crazy Auction Employee ***\n");
            System.out.println("You are login as " + currentEmployee.getFirstName() + " " + currentEmployee.getLastName());
            System.out.println("1: Purchase Credit Package");
            System.out.println("2: View Credit Transaction History");
            System.out.println("3: Browse All Auction Listing");
            System.out.println("4: View Auction Listing Details");
            System.out.println("5: Place New Bid");
            System.out.println("6: Browse Won Auction Listing");
            System.out.println("7: Select Delivery Address For Won Auction Listing");
            System.out.println("8: Delete Address");
            System.out.println("9: Update Customer Profile");
            System.out.println("10: Logout\n");
            response = 0;

            while (response < 1 || response > 10) {
                System.out.print("> ");

                response = scanner.nextInt();
                if (response == 1){
                    
                    
                    
                } else if (response == 2){
                    
                    
                    
                } else if (response == 3){
                    
                    
                    
                } else if (response == 4){
                    
                    
                
                } else if (response == 5){
                    
                    
                
                } else if (response == 6){
                    
                    
                
                } else if (response == 7){
                    
                    
                
                } else if (response == 8){
                    
                   
                
                } else if (response == 9){
                    
                    
                
                } else if (response == 10){
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 10) {
                break;

            }
        }
    }



}
