/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import util.enumeration.AccessRightEnum;

/**
 *
 * @author chiaangyong
 */
@Entity
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(min = 1, max = 32)
    private String firstName;
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(min = 1, max = 32)
    private String lastName;
    @Column(nullable = false, unique = true, length = 32)
    @NotNull
    @Size(min = 5, max = 32)
    private String username;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private AccessRightEnum accessRightEnum;
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(min = 8, max = 32)
    private String password;


    public Employee() {
    }


    public Employee(String firstName, String lastName, String username, String password, AccessRightEnum accessRightEnum) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.accessRightEnum = accessRightEnum;

    }

    
    
    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (employeeId != null ? employeeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the employeeId fields are not set
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.employeeId == null && other.employeeId != null) || (this.employeeId != null && !this.employeeId.equals(other.employeeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Employee[ id=" + employeeId + " ]";
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the accessRightEnum
     */
    public AccessRightEnum getAccessRightEnum() {
        return accessRightEnum;
    }

    /**
     * @param accessRightEnum the accessRightEnum to set
     */
    public void setAccessRightEnum(AccessRightEnum accessRightEnum) {
        this.accessRightEnum = accessRightEnum;
    }
    
    
    
}
