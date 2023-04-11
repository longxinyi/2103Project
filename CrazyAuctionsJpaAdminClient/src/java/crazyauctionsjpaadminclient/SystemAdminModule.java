/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazyauctionsjpaadminclient;

import ejb.session.stateless.EmployeeSessionBeanRemote;
import entity.Employee;
import java.util.List;
import java.util.Scanner;
import util.enumeration.AccessRightEnum;
import util.exception.EmployeeNotFoundException;
import util.exception.EmployeeUsernameExistException;
import util.exception.InvalidAccessRightException;
import util.exception.UpdateEmployeeException;

/**
 *
 * @author xinyi
 */
public class SystemAdminModule {

    private Employee currentEmployee;
    private EmployeeSessionBeanRemote employeeSessionBeanRemote;

    public SystemAdminModule() {
    }

    public SystemAdminModule(EmployeeSessionBeanRemote employeeSessionBeanRemote, Employee currentEmployee) {
        this.employeeSessionBeanRemote = employeeSessionBeanRemote;
        this.currentEmployee = currentEmployee;
    }

    public void systemAdminOperation() throws InvalidAccessRightException, EmployeeNotFoundException {
        if (currentEmployee.getAccessRightEnum() != AccessRightEnum.SYSTEMADMIN) {
            throw new InvalidAccessRightException("You don't have SYSTEM ADMIN rights to access the system administration module.");
        }

        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Crazy Auctions :: System Admin Operation ***\n");
            System.out.println("1: Create New Employee");
            System.out.println("2: View Employee Details");
            System.out.println("3: Update Employee");
            System.out.println("4: View All Employees");
            System.out.println("5: Back\n");
            response = 0;

            while (response < 1 || response > 5) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    createNewEmployee();
                } else if (response == 2) {
                    viewEmployeeDetails();
                } else if (response == 3) {
                    updateEmployee();
                } else if (response == 4) {
                    viewAllEmployees();
                } else if (response == 5) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 5) {
                break;
            }
        }
    }

    public void createNewEmployee() {

        Scanner scanner = new Scanner(System.in);
        Employee newEmployee = new Employee();

        System.out.println("*** CrazyAuctions: Create new employee ***\n");
        System.out.print("Enter First Name> ");
        newEmployee.setFirstName(scanner.nextLine().trim());
        System.out.print("Enter Last Name> ");
        newEmployee.setLastName(scanner.nextLine().trim());
        System.out.print("Enter Username> ");
        newEmployee.setUsername(scanner.nextLine().trim());
        System.out.print("Enter Password> ");
        newEmployee.setPassword(scanner.nextLine().trim());

        while (true) {
            System.out.print("Select Access Right (1: SystemAdmin, 2: Finance, 3: Sales)> ");
            Integer accessRightInt = scanner.nextInt();
            if (accessRightInt >= 1 && accessRightInt <= 3) {
                newEmployee.setAccessRightEnum(AccessRightEnum.values()[accessRightInt - 1]);
                break;
            } else {
                System.out.println("Invalid option, please try again!\n");
            }
        }

        try {
            Long employeeId = employeeSessionBeanRemote.createNewEmployee(currentEmployee);

        } catch (EmployeeUsernameExistException ex) {
            System.out.println("An error has occurred while creating the new staff!: The user name already exist\n");
        }

    }

    public void viewEmployeeDetails() {
        Scanner scanner = new Scanner(System.in);
        

        System.out.println("*** CrazyAuctions: View Employee Details ***\n");
        System.out.print("Enter Username> ");
        String username = scanner.nextLine().trim();
        
        try
        {
            Employee newEmployee = employeeSessionBeanRemote.retrieveEmployeeByUsername(username);
            System.out.println("Employee retrieved is : " + newEmployee.getFirstName() + " " + newEmployee.getLastName() + " with username of : " + newEmployee.getUsername() + "\n");
        }
        catch(EmployeeNotFoundException ex)
        {
            System.out.println("An error has occurred while retrieving staff: " + ex.getMessage() + "\n");
        }

    }

    public void updateEmployee() throws EmployeeNotFoundException {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        System.out.println("*** CrazyAuctions: View Employee Details ***\n");
        System.out.print("Enter username of employee to be updated> ");
        String username = scanner.nextLine().trim();

       

        while (true) {
            System.out.println("*** Which part of your profile would you like to update? ***\n");
            System.out.println("1: First Name");
            System.out.println("2: Last Name");
            System.out.println("3: Username");
            System.out.println("4: Password");
            System.out.println("5: Exit\n");

            response = 0;
            while (response < 1 || response > 5) {
                Scanner newScanner = new Scanner(System.in);
                System.out.print("> ");

                response = scanner.nextInt();
                if (response == 1) {

                    try {
                        System.out.print("Enter New First Name> ");
                        String newFirstName = newScanner.nextLine().trim();
                        employeeSessionBeanRemote.updateEmployeeProfileByAdmin(username, newFirstName, 1);

                    } catch (UpdateEmployeeException ex) {
                        System.out.print("There was an error, please try again ");
                    }

                    System.out.print("First Name has been successfully changed! ");

                } else if (response == 2) {
                    try {
                        System.out.print("Enter New Last Name> ");
                        String newLastName = newScanner.nextLine().trim();
                        employeeSessionBeanRemote.updateEmployeeProfileByAdmin(username, newLastName, 2);

                    } catch (UpdateEmployeeException ex) {
                        System.out.print("There was an error, please try again ");
                    }

                } else if (response == 3) {
                    try {
                        System.out.print("Enter New username> ");
                        String newUsername = newScanner.nextLine().trim();
                        employeeSessionBeanRemote.updateEmployeeProfileByAdmin(username, newUsername, 3);

                    } catch (UpdateEmployeeException ex) {
                        System.out.print("There was an error, please try again ");
                    }

                } else if (response == 4) {
                     try {
                        System.out.print("Enter New Password> ");
                        String newPassword = newScanner.nextLine().trim();
                        employeeSessionBeanRemote.updateEmployeeProfileByAdmin(username, newPassword, 4);

                    } catch (UpdateEmployeeException ex) {
                        System.out.print("There was an error, please try again ");
                    }

                } else if (response == 5) {

                    break;
                    
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }

            }

            if (response == 5) {
                break;
            }

        }
    }

   
        
    

    public void viewAllEmployees() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("*** Crazy Auctions :: System Administration :: View All Staffs ***\n");
        
        List<Employee> employees = employeeSessionBeanRemote.retrieveAllEmployees();
        System.out.printf("%20s%20s%15s%20s\n", "First Name", "Last Name", "Access Right", "Username");

        for(Employee employee: employees)
        {
            System.out.printf("%20s%20s%15s%\n", employee.getFirstName(), employee.getLastName(), employee.getAccessRightEnum().toString(), employee.getUsername());
        }
        
        System.out.print("Press any key to continue...> ");
        scanner.nextLine();
    }

}
