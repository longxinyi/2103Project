/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionsjpaclient;

import ejb.session.stateless.AddressSessionBeanRemote;
import ejb.session.stateless.AuctionListingBidSessionBeanRemote;
import ejb.session.stateless.AuctionListingSessionBeanRemote;
import ejb.session.stateless.CreditPackageSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import entity.AuctionListing;
import entity.AuctionListingBid;
import entity.CreditPackage;
import entity.Customer;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import util.exception.ListingNotFoundException;
import util.exception.CreditTransactionHistoryNotFoundException;
import util.exception.AddressNotFoundException;
import util.exception.BidIncrementException;
import util.exception.CreditPackageNotFoundException;
import util.exception.CustomerNotFoundException;
import util.exception.CustomerUsernameExistException;
import util.exception.ImposterWinnerException;
import util.exception.InvalidBidIncrementException;
import util.exception.InvalidLoginCredentialException;
import util.exception.ListingNotActiveException;
import util.exception.ListingNotFoundException;
import util.exception.MinimumBidException;
import util.exception.NoBidException;
import util.exception.UpdateCreditPackageException;
import util.exception.UpdateCustomerException;

/**
 *
 * @author chiaangyong
 */
public class MainApp {

    private CustomerSessionBeanRemote customerSessionBeanRemote;
    private CreditPackageSessionBeanRemote creditPackageSessionBeanRemote;
    private AuctionListingSessionBeanRemote auctionListingSessionBeanRemote;
    private AddressSessionBeanRemote addressSessionBeanRemote;
    private AuctionListingBidSessionBeanRemote auctionListingBidSessionBeanRemote;

    private Customer currentCustomer;
    private AuctionListing currentListing;

    public MainApp() {

    }

    public MainApp(CustomerSessionBeanRemote customerSessionBeanRemote, CreditPackageSessionBeanRemote creditPackageSessionBeanRemote, AuctionListingSessionBeanRemote auctionListingSessionBeanRemote, AddressSessionBeanRemote addressSessionBeanRemote, AuctionListingBidSessionBeanRemote auctionListingBidSessionBeanRemote) {

        this.customerSessionBeanRemote = customerSessionBeanRemote;
        this.creditPackageSessionBeanRemote = creditPackageSessionBeanRemote;
        this.auctionListingSessionBeanRemote = auctionListingSessionBeanRemote;
        this.addressSessionBeanRemote = addressSessionBeanRemote;
        this.auctionListingBidSessionBeanRemote = auctionListingBidSessionBeanRemote;

    }

    public void runApp() throws AddressNotFoundException, CustomerNotFoundException, UpdateCustomerException, ListingNotFoundException, MinimumBidException, BidIncrementException, InvalidBidIncrementException, ListingNotFoundException, ImposterWinnerException, CreditTransactionHistoryNotFoundException, CreditPackageNotFoundException, UpdateCreditPackageException, ListingNotActiveException, NoBidException {

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
                    } catch (InvalidLoginCredentialException ex) {
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

    private void menuMain() throws InvalidLoginCredentialException, AddressNotFoundException, CustomerNotFoundException, UpdateCustomerException, ListingNotFoundException, MinimumBidException, BidIncrementException, InvalidBidIncrementException, ListingNotFoundException, ImposterWinnerException, CreditTransactionHistoryNotFoundException, CreditPackageNotFoundException, UpdateCreditPackageException, ListingNotActiveException, NoBidException {

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
                if (response == 1) {

                    purchaseCreditPackage();

                } else if (response == 2) {

                    viewCreditTransactionHistory();

                } else if (response == 3) {

                    browseAllAuctionListing();

                } else if (response == 4) {

                    viewAuctionListingDetails();

                } else if (response == 5) {

                    placeNewBid();

                } else if (response == 6) {

                    browseWonAuctionListing();

                } else if (response == 7) {

                    selectDeliveryAddress();

                } else if (response == 8) {

                    deleteAddress();

                } else if (response == 9) {

                    updateCustomerProfile();

                } else if (response == 10) {
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

    public void purchaseCreditPackage() throws CustomerNotFoundException, CreditTransactionHistoryNotFoundException, CreditPackageNotFoundException, UpdateCreditPackageException {
        Scanner scanner = new Scanner(System.in);
        int response = 0;
        BigDecimal quantity = new BigDecimal(0);

        while (true) {
            int i=1;
            System.out.println("*** We have numerous credit package for you, please select the one you need. ***\n");
            List<CreditPackage> listOfCreditPackage = creditPackageSessionBeanRemote.retrieveCreditPackage();
            if(listOfCreditPackage.size()==0){
                System.out.println("No Credit Package available");
                break;
            }
            for (CreditPackage creditPackage : listOfCreditPackage) {
                System.out.println(creditPackage.getCreditPackageId()+": Credit Package "+creditPackage.getCreditPackageId()+"($"+creditPackage.getCreditPrice()+")");
                i=i+1;
            
            }
            System.out.println(i+": Exit");
            response = 0;
            quantity = new BigDecimal(0);

            while (response < 1 || response > i) {

                System.out.print("> ");
                    
                response = scanner.nextInt();
                
                System.out.println("How many quantity?");
                System.out.print("> ");
                if (response == i) {
                    break;
                } else if (response > i){
                    System.out.println("Invalid Response, please try again");
                } else {
                    quantity = scanner.nextBigDecimal();
                    CreditPackage currentCreditPackage = creditPackageSessionBeanRemote.retrieveCreditPackageById(Long.valueOf(response));
                    //setactive
                    currentCreditPackage.setIsActive(Boolean.TRUE);
                    creditPackageSessionBeanRemote.updateCreditPackage(currentCreditPackage);
                    Long customerId = customerSessionBeanRemote.updateCreditBalance(currentCustomer.getUsername(), quantity.multiply(new BigDecimal(10)));
                    System.out.println("Successful Transaction: You have purchased " + quantity + " credit package "+currentCreditPackage.getCreditPackageId());

                
                   
                }
                
            }
            
            if (response == i){
                break;
            }
        

            


        }
    }

    ;
    
    public void viewCreditTransactionHistory() {
        try {

            List<CreditPackage> creditPackage = creditPackageSessionBeanRemote.retrieveCreditTransactionHistory(currentCustomer);

            for (CreditPackage credPackage : creditPackage) {
                System.out.println(credPackage.getCreditPackageType());//+ " " + credPackage.getCreditPackageQuantity() + "\n");
            }
        } catch (CreditTransactionHistoryNotFoundException ex) {
            System.out.println("An error has occurred while creating the new staff!: The user name already exist\n");
        }

    }

    ;
    
    public void browseAllAuctionListing() {
        try {
            List<AuctionListing> auctionListings = auctionListingSessionBeanRemote.retrieveAuctionListing();

            for (AuctionListing auctionListing : auctionListings) {
                if (auctionListing.isActive() == true) {
                    System.out.println("Auction listing : " + auctionListing.getAuctionName() + " with reserve price of: " + auctionListing.getReservePrice() + "\n");
                }
            }

        } catch (ListingNotFoundException ex) {
            System.out.println("An error has occurred while creating the new staff!: The user name already exist\n");
        }
    }

    public void viewAuctionListingDetails() throws ListingNotFoundException {
        List<AuctionListing> activeAuctions = auctionListingSessionBeanRemote.viewActiveListings();
        for (AuctionListing auctionListing : activeAuctions) {
            System.out.println("Name of listing : " + auctionListing.getAuctionName() + " with the current highest bid of: " + auctionListing.getAuctionListingBids().get(auctionListing.getAuctionListingBids().size()) + " \n");
        }
    }

    ;
    
    public void placeNewBid() throws ListingNotFoundException, MinimumBidException, BidIncrementException, InvalidBidIncrementException, ListingNotActiveException, NoBidException {
        //enter bid amount -> check if higher than current highest
        //if no bid, check if more than 0.05
        Scanner scanner = new Scanner(System.in);
        System.out.print("Which listing would you like to bid for? Enter name > ");
        String auctionName = scanner.nextLine().trim();
        System.out.println("How much would you like to bid for? Enter amount  ");
        System.out.print("> ");
        BigDecimal price = new BigDecimal(0);
        price = scanner.nextBigDecimal();
        

        auctionListingBidSessionBeanRemote.placeNewBid(auctionName, price);

    }

    ;
    
    public void browseWonAuctionListing() {
        List<AuctionListing> wonListings = customerSessionBeanRemote.browseWonAuctionListings(currentCustomer);

        if (wonListings.size() == 0) {
            System.out.println("You have won 0 listings.");
        } else {
            for (AuctionListing auctionListing : wonListings) {
                System.out.println("Name of listing won: " + auctionListing.getAuctionName() + " with the bid of: " + auctionListing.getAuctionListingBids().get(auctionListing.getAuctionListingBids().size()) + " \n");
            }

        }
    }

    ;
    
    public void selectDeliveryAddress() throws AddressNotFoundException, ListingNotFoundException, ImposterWinnerException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter address name > ");
        String addressName = scanner.nextLine().trim();
        System.out.print("Enter listing name > ");
        String listingName = scanner.nextLine().trim();
        
        addressSessionBeanRemote.selectAddressForWinningBid(addressName, currentCustomer, listingName);
        System.out.println("Address successfully selected");
    }

    ;
    
    public void deleteAddress() throws AddressNotFoundException {
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

    }

    ;
    
    public void updateCustomerProfile() throws CustomerNotFoundException, UpdateCustomerException {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
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
                Scanner newScanner = new Scanner(System.in);
                System.out.print("> ");

                response = scanner.nextInt();
                if (response == 1) {

                    try {
                        System.out.print("Enter New First Name> ");

                        currentCustomer.setFirstName(newScanner.nextLine().trim());
                        customerSessionBeanRemote.updateCustomerProfile(currentCustomer);

                    } catch (UpdateCustomerException ex) {
                        System.out.print("There was an error, please try again ");
                    }

                    System.out.print("First Name has been successfully changed! ");

                } else if (response == 2) {

                    System.out.print("Enter New Last Name> ");
                    currentCustomer.setLastName(newScanner.nextLine().trim());
                    try {
                        customerSessionBeanRemote.updateCustomerProfile(currentCustomer);
                    } catch (UpdateCustomerException ex) {
                        System.out.print("Last Name has been successfully changed! ");
                    }

                } else if (response == 3) {

                    System.out.print("Enter Postal Code> ");
                    currentCustomer.setPostalCode(Integer.valueOf(newScanner.nextLine().trim()));
                    try {
                        customerSessionBeanRemote.updateCustomerProfile(currentCustomer);
                    } catch (UpdateCustomerException ex) {
                        System.out.print("Last Name has been successfully changed! ");
                    }

                } else if (response == 4) {

                    System.out.print("Enter Contact Number> ");
                    currentCustomer.setContactNumber(Integer.valueOf(newScanner.nextLine().trim()));
                    customerSessionBeanRemote.updateCustomerProfile(currentCustomer);
                    System.out.print("Contact Number has been successfully changed! ");

                } else if (response == 5) {

                    System.out.print("Enter New Email Address> ");
                    currentCustomer.setEmailAddress(newScanner.nextLine().trim());
                    try {
                        customerSessionBeanRemote.updateCustomerProfile(currentCustomer);
                    } catch (UpdateCustomerException ex) {
                        System.out.print("Email Address has been successfully changed! ");
                    }

                } else if (response == 6) {

                    System.out.print("Enter New Password> ");
                    currentCustomer.setPassword(newScanner.nextLine().trim());
                    try {
                        customerSessionBeanRemote.updateCustomerProfile(currentCustomer);
                    } catch (UpdateCustomerException ex) {
                        System.out.print("Password has been successfully changed! ");
                    }
                } else if (response == 7) {

                    break;

                } else {
                    System.out.println("Invalid option, please try again!\n");
                }

            }

            if (response == 7) {
                break;
            }

        }

    }
;

}
