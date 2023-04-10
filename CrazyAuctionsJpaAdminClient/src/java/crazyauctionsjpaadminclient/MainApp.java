/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionsjpaadminclient;

import ejb.session.stateless.EmployeeSessionBeanRemote;
import entity.Employee;
import java.util.Scanner;
import util.enumeration.AccessRightEnum;
import util.exception.EmployeeNotFoundException;
import util.exception.EmployeeUsernameExistException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UpdateEmployeeException;

/**
 *
 * @author xinyi
 */
public class MainApp {
    
    private Employee currentEmployee;
    
    private EmployeeSessionBeanRemote employeeSessionBeanRemote;

    public MainApp() {
        
    }
    
    public MainApp(EmployeeSessionBeanRemote employeeSessionBeanRemote){
        this.employeeSessionBeanRemote = employeeSessionBeanRemote;
    }
    
    public void runApp() throws EmployeeNotFoundException{
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Welcome to Crazy Auction: Employee ***\n");
            System.out.println("1: Login");
            System.out.println("2: Exit\n");
            response = 0;

            while (response < 1 || response > 2) {
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
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 2) {
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
    
    private void menuMain() throws EmployeeNotFoundException  {


        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Crazy Auction Employee ***\n");
            System.out.println("You are login as " + currentEmployee.getFirstName() + " " + currentEmployee.getLastName());
            System.out.println("1: Change Password");
           
            System.out.println("3: Delete Credit Package");
            System.out.println("4: Create Auction Listing");
            System.out.println("5: Delete Auction Listing");
            System.out.println("6: Close Expired Auction Listings and Assign Winning Bid");
            System.out.println("7: View All Auction Listings with Bids but Below Reserve Price");
            System.out.println("8: Assign Winning Bid for Listing with Bids but Below Reserve Price");
            System.out.println("9: Logout\n");
            response = 0;

            while (response < 1 || response > 9) {
                System.out.print("> ");

                response = scanner.nextInt();
                if (response == 1){
                    
                    changePassword();
                    
                } else if (response == 2){
                    
                    createNewEmployee();
                    
                } else if (response == 3){
                    
                    deleteCreditPackage();
                    
                } else if (response == 4){
                    
                    createAuctionListing();
                
                } else if (response == 5){
                    
                    deleteAuctionListing();
                
                } else if (response == 6){
                    
                    closeExpiredListingsAssignWinningBid();
                
                } else if (response == 7){
                    
                    viewAllListingsWBidsBelowReservePrice();
                
                } else if (response == 8){
                    
                    assignWinningBidforListingsWBids();
                
                } else if (response == 9){
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 9) {
                break;

            }
        }
        
        
    }
    
    public void changePassword() throws EmployeeNotFoundException{
        Scanner newScanner = new Scanner(System.in);
        System.out.print("Enter New Password> ");
        currentEmployee.setPassword(newScanner.nextLine().trim());
        try {
            employeeSessionBeanRemote.updateEmployeeProfile(currentEmployee);
        } catch (UpdateEmployeeException ex) {
            System.out.print("Password has been successfully changed! ");
        }
    }
        
    
    
    public void deleteCreditPackage(){}
    
    public void createAuctionListing(){}
    
    public void deleteAuctionListing(){}
    
    public void closeExpiredListingsAssignWinningBid(){}
    
    public void viewAllListingsWBidsBelowReservePrice(){}
    
    public void assignWinningBidforListingsWBids(){}
    
    
}
