/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Employee;
import java.util.List;
import javax.ejb.Remote;
import util.enumeration.AccessRightEnum;
import util.exception.EmployeeNotFoundException;
import util.exception.EmployeeUsernameExistException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UpdateEmployeeException;

/**
 *
 * @author xinyi
 */
@Remote
public interface EmployeeSessionBeanRemote {
    public Long createNewEmployee(Employee employee) throws EmployeeUsernameExistException;
    public Employee createNewEmployee(String firstName, String lastName, String username, String password, AccessRightEnum accessRightEnum);
    public Employee retrieveEmployeeByUsername(String username) throws EmployeeNotFoundException;
    public Employee employeeLogin(String username, String password) throws InvalidLoginCredentialException;
    public void updateEmployeeProfileByAdmin(String username, String newDetail, int type) throws EmployeeNotFoundException, UpdateEmployeeException;
    public List<Employee> retrieveAllEmployees();
    public void updateEmployeeProfile(Employee employee) throws EmployeeNotFoundException, UpdateEmployeeException;
}