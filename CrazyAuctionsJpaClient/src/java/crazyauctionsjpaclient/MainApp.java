/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionsjpaclient;

import ejb.session.stateless.AddressSessionBeanRemote;
import ejb.session.stateless.AuctionListingSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import ejb.session.stateless.auctionListingBidSessionBeanRemote;
import entity.AuctionListing;
import entity.Customer;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Scanner;
import util.exception.AddressNotFoundException;
import util.exception.CustomerNotFoundException;
import util.exception.CustomerUsernameExistException;
import util.exception.InvalidLoginCredentialException;
import util.exception.ListingNotFoundException;
import util.exception.UpdateCustomerException;

/**
 *
 * @author chiaangyong
 */
public class MainApp {

    private CustomerSessionBeanRemote customerSessionBeanRemote;
    private AddressSessionBeanRemote addressSessionBeanRemote;
    private AuctionListingSessionBeanRemote auctionListingSessionBeanRemote;
    private Customer currentCustomer;

    public MainApp() {

    }

    public MainApp(CustomerSessionBeanRemote customerSessionBeanRemote, AddressSessionBeanRemote addressSessionBeanRemote, AuctionListingSessionBeanRemote auctionListingSessionBeanRemote) {
        this.customerSessionBeanRemote = customerSessionBeanRemote;
        this.addressSessionBeanRemote = addressSessionBeanRemote;
        this.auctionListingSessionBeanRemote = auctionListingSessionBeanRemote;
    }

    public void runApp() throws AddressNotFoundException, CustomerNotFoundException, UpdateCustomerException, ListingNotFoundException {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Welcome to Crazy Auction ***\n");
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
            currentCustomer = customerSessionBeanRemote.customerLogin(username, password);
        } else {
            throw new InvalidLoginCredentialException("Missing login credential!");
        }
    }

    private void doRegister() {
        Scanner scanner = new Scanner(System.in);
        currentCustomer = new Customer();

        System.out.println("*** CrazyAuctions: Registration ***\n");
        System.out.print("Enter First Name> ");
        currentCustomer.setFirstName(scanner.nextLine().trim());
        System.out.print("Enter Last Name> ");
        currentCustomer.setLastName(scanner.nextLine().trim());
        System.out.print("Enter Postal Code> ");
        currentCustomer.setPostalCode(Integer.valueOf(scanner.nextLine().trim()));
        System.out.print("Enter Contact Number> ");
        currentCustomer.setContactNumber(Integer.valueOf(scanner.nextLine().trim()));
        System.out.print("Enter Email Address> ");
        currentCustomer.setEmailAddress(scanner.nextLine().trim());
        System.out.print("Enter Username> ");
        currentCustomer.setUsername(scanner.nextLine().trim());
        System.out.print("Enter Password> ");
        currentCustomer.setPassword(scanner.nextLine().trim());
        currentCustomer.setCreditBalance(new BigDecimal(0));

        try {
            Long customerId = customerSessionBeanRemote.createNewCustomer(currentCustomer);
            
        } catch (CustomerUsernameExistException ex) {
            System.out.println("An error has occurred while creating the new staff!: The user name already exist\n");
        } 

    }

    private void menuMain() throws InvalidLoginCredentialException, AddressNotFoundException, CustomerNotFoundException, UpdateCustomerException, ListingNotFoundException {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Crazy Auction Customer ***\n");
            System.out.println("You are login as " + currentCustomer.getFirstName() + " " + currentCustomer.getLastName());
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
                    
                    purchaseCreditPackage();
                    
                } else if (response == 2){
                    
                    viewCreditTransactionHistory();
                    
                } else if (response == 3){
                    
                    browseAllAuctionListing();
                    
                } else if (response == 4){
                    
                    viewAuctionListingDetails();
                
                } else if (response == 5){
                    
                    placeNewBid();
                
                } else if (response == 6){
                    
                    browseNewAuctionListing();
                
                } else if (response == 7){
                    
                    selectDeliveryAddress();
                
                } else if (response == 8){
                    
                    deleteAddress();
                
                } else if (response == 9){
                    
                    updateCustomerProfile();
                
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
    
    public void purchaseCreditPackage(){};
    
    public void viewCreditTransactionHistory(){};
    
    public void browseAllAuctionListing(){};
    
    public void viewAuctionListingDetails(){};
    
    public void placeNewBid() throws ListingNotFoundException{
    //enter bid amount -> check if higher than current highest
    //if no bid, check if more than 0.05
        Scanner scanner = new Scanner(System.in);
        System.out.print("Which listing would you like to bid for? Enter name > ");
        String listingName = scanner.nextLine().trim();
        AuctionListing auctionListing = auctionListingSessionBeanRemote.findListingByName(listingName);
        auctionListingBidSessionBeanRemote.placeNewBid();
    
    };
    
    public void browseNewAuctionListing(){};
    
    public void selectDeliveryAddress(){
        
    };
    
    public void deleteAddress() throws AddressNotFoundException{
    //check if it is associated with a winning bid
    //if yes, marked as disabled -> cannot be enabled
    //if no, deleted
        Scanner scanner = new Scanner(System.in);
        System.out.print("Which address you would like to delete? ");
        String addressName = scanner.nextLine().trim();
        try {
        addressSessionBeanRemote.deleteAddress(addressName);
        } catch (AddressNotFoundException e) {
            System.out.println("An error occurred while deleting address!");
        }
        
        
    };
    
    public void updateCustomerProfile() throws CustomerNotFoundException, UpdateCustomerException{
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while(true){
            System.out.println("*** Which part of your profile would you like to update? ***\n");
            System.out.println("1: First Name");
            System.out.println("2: Last Name");
            System.out.println("3: Postal Code");
            System.out.println("4: Contact Number");
            System.out.println("5: Email Address");
            System.out.println("6: Password");
            System.out.println("7: Exit\n");

            
            response = 0;
            while (response < 1 || response > 7) {
                System.out.print("> ");

                response = scanner.nextInt();
                if (response == 1) {
                    
                    try {
                        System.out.print("Enter New First Name> ");
                        String firstName = scanner.nextLine().trim();
                        currentCustomer.setFirstName(firstName);
                        System.out.println("testing");
                        customerSessionBeanRemote.updateCustomerProfile(currentCustomer);
                        System.out.print("First Name has been successfully changed! ");
                    } catch (UpdateCustomerException ex) {
                        System.out.print("There was an error, please try again ");
                    }
                    
                    
       
                } else if (response == 2) {
                    
                    System.out.print("Enter New Last Name> ");
                    currentCustomer.setLastName(scanner.nextLine().trim());
                    try {
                        customerSessionBeanRemote.updateCustomerProfile(currentCustomer);
                    } catch (UpdateCustomerException ex) {
                        System.out.print("Last Name has been successfully changed! ");
                    }

                    
                } else if (response == 3) {
                    
                    System.out.print("Enter Postal Code> ");
                    currentCustomer.setPostalCode(Integer.valueOf(scanner.nextLine().trim()));
                    try {
                        customerSessionBeanRemote.updateCustomerProfile(currentCustomer);
                    } catch (UpdateCustomerException ex) {
                        System.out.print("Last Name has been successfully changed! ");
                    }
                
                } else if (response == 4) {
                    
                    System.out.print("Enter Contact Number> ");
                    currentCustomer.setContactNumber(Integer.valueOf(scanner.nextLine().trim()));
                    customerSessionBeanRemote.updateCustomerProfile(currentCustomer);
                    System.out.print("Contact Number has been successfully changed! ");
                
                } else if (response == 5) {
                    
                    System.out.print("Enter New Email Address> ");
                    currentCustomer.setEmailAddress(scanner.nextLine().trim());
                    try {
                        customerSessionBeanRemote.updateCustomerProfile(currentCustomer);
                    } catch (UpdateCustomerException ex) {
                        System.out.print("Last Name has been successfully changed! ");
                    }
                
                } else if (response == 6) {

                    System.out.print("Enter New Password> ");
                    currentCustomer.setPassword(scanner.nextLine().trim());
                    try {
                        customerSessionBeanRemote.updateCustomerProfile(currentCustomer);
                    } catch (UpdateCustomerException ex) {
                        System.out.print("Last Name has been successfully changed! ");
                    }
                } else if (response == 7) {
                
                    break;
                    
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
                    
            }


        }    
       
    
    };

}
