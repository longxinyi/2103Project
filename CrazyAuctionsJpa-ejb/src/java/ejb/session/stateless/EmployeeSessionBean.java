/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import entity.Employee;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.enumeration.AccessRightEnum;
import util.exception.CustomerUsernameExistException;
import util.exception.EmployeeNotFoundException;
import util.exception.EmployeeUsernameExistException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UpdateEmployeeException;

/**
 *
 * @author xinyi
 */
@Stateless
public class EmployeeSessionBean implements EmployeeSessionBeanRemote, EmployeeSessionBeanLocal {

    @PersistenceContext(unitName = "CrazyAuctionsJpa-ejbPU")
    private EntityManager em;
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

   
    @Override 
    public Long createNewEmployee(Employee employee) throws EmployeeUsernameExistException
    {
        
        em.persist(employee);
        
        return employee.getEmployeeId();
        
    }
    
    @Override
    public Employee createNewEmployee(String firstName, String lastName, String username, String password, AccessRightEnum accessRightEnum){
        Employee employee = new Employee(firstName, lastName, username, password, accessRightEnum);
        em.persist(employee);
        return employee;

    }
    
    @Override
    public Employee retrieveEmployeeByUsername(String username) throws EmployeeNotFoundException
    {
        Query query = em.createQuery("SELECT e FROM Employee e WHERE e.username = :inUsername");
        query.setParameter("inUsername", username);
        
        try
        {
            return (Employee)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new EmployeeNotFoundException("Employee Username " + username + " does not exist!");
        }
    }
    
    
    @Override
    public Employee employeeLogin(String username, String password) throws InvalidLoginCredentialException
    {
        try
        {
            Employee employee = retrieveEmployeeByUsername(username);
            
            if(employee.getPassword().equals(password))
            {                
                return employee;
            }
            else
            {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        }
        catch(EmployeeNotFoundException ex)
        {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }
    
    public void updateEmployeeProfile(String username, String newDetail, int type) throws EmployeeNotFoundException, UpdateEmployeeException{
       if(type == 1){
           
       }
    }
    
    public List<Employee> retrieveAllEmployees() {

        Query query = em.createQuery("SELECT s FROM StaffEntity s");

        return query.getResultList();
        
    }

   
}
