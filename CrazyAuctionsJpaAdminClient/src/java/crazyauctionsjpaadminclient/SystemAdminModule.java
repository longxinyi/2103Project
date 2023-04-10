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
import util.exception.EmployeeUsernameExistException;
import util.exception.InvalidAccessRightException;

/**
 *
 * @author xinyi
 */
public class SystemAdminModule {
    
    
    
    private Employee currentEmployee;
    private EmployeeSessionBeanRemote employeeSessionBeanRemote;

    public SystemAdminModule() {
    }

    public SystemAdminModule(EmployeeSessionBeanRemote employeeSessionBeanRemote) {
        this.employeeSessionBeanRemote = employeeSessionBeanRemote;
    }
    
    
    
    public void systemAdminOperation() throws InvalidAccessRightException
    {
        if(currentEmployee.getAccessRightEnum() != AccessRightEnum.SYSTEMADMIN)
        {
            throw new InvalidAccessRightException("You don't have SYSTEMADMIN rights to access the system administration module.");
        }
        
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
            System.out.println("*** Crazy Auctions :: System Admin Operation ***\n");
            System.out.println("1: Create New Employee");
            System.out.println("2: View Employee Details");
            System.out.println("3: Update Employee");
            System.out.println("4: View All Employees");
            System.out.println("5: Back\n");
            response = 0;
            
            while(response < 1 || response > 5)
            {
                System.out.print("> ");

                response = scanner.nextInt();

                if(response == 1)
                {
                    createNewEmployee();
                }
                else if(response == 2)
                {
                    viewEmployeeDetails();
                }
                else if(response == 3)
                {
                    updateEmployee();
                } else if(response == 4){
                    viewAllEmployees();
                } else if (response == 5){
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
            
            if(response == 5)
            {
                break;
            }
        }
    }
    
    
    
    public void createNewEmployee(){
     
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

    public void viewEmployeeDetails(){}
    
    public void updateEmployee(){}
    
    public void viewAllEmployees(){}
    
}
